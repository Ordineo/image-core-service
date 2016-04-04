package be.ordina.ordineo.service.impl;

import be.ordina.ordineo.model.Image;
import be.ordina.ordineo.repository.ImageRepository;
import be.ordina.ordineo.service.ImageService;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    AmazonS3Client amazonS3Client;

    public static final String BUCKET = "ordineo";

    @Override
    public void uploadToAWS(String username,String url) throws IOException {
        url = url.replace("https:", "http:");
        String image = "ProfilePictures/" + username + ".jpg";
        try {
            URL imageUrl = new URL(url);
            URLConnection connection = imageUrl.openConnection();
            InputStream inputStream = connection.getInputStream();
            amazonS3Client.putObject(new PutObjectRequest(BUCKET, image, inputStream, new ObjectMetadata())
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            saveImage(username,image);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    protected void saveImage(String username, String image) {
        Image newImage = imageRepository.findByUsernameIgnoreCase(username);
        if (newImage==null) {
            newImage = new Image();
            newImage.setUsername(username);
        }
        newImage.setImage(image);
        imageRepository.save(newImage);
    }

    @Override
    public byte[] getImage(String username) throws IOException {
        InputStream is = new BufferedInputStream(getAWSImage(username).getObjectContent());
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        try {
            BufferedImage img = ImageIO.read(is);
            ImageIO.write(img, "jpg", bao);
        } catch (IOException e) {
            throw new IOException(e);
        }
        return bao.toByteArray();
    }

    protected S3Object getAWSImage(String username) throws AmazonS3Exception{
        Image image = imageRepository.findByUsernameIgnoreCase(username);
        if (image !=null) {
            return amazonS3Client.getObject(BUCKET, image.getImage());
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public void deleteImage(String username) {
        Image image = imageRepository.findByUsernameIgnoreCase(username);
        amazonS3Client.deleteObject(BUCKET, image.getImage());
        imageRepository.delete(image);
    }

}
