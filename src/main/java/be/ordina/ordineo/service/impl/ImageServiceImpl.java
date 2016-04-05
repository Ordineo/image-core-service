package be.ordina.ordineo.service.impl;

import be.ordina.ordineo.model.Image;
import be.ordina.ordineo.repository.ImageRepository;
import be.ordina.ordineo.service.ImageService;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
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

    public void uploadToAWS(String username, String url) throws IOException {
        try {
            url = url.replace("https:", "http:");
            URL imageUrl = new URL(url);
            URLConnection connection = imageUrl.openConnection();
            InputStream inputStream = connection.getInputStream();
            uploadToAWSAndSave(username, inputStream);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    public void uploadToAWS(String username, InputStream inputStream) throws IOException {
        try {
            uploadToAWSAndSave(username, inputStream);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    private void uploadToAWSAndSave(String username, InputStream inputStream) {
        String image = "ProfilePictures/" + username + ".jpg";
        amazonS3Client.putObject(new PutObjectRequest(BUCKET, image, inputStream, new ObjectMetadata())
                .withCannedAcl(CannedAccessControlList.PublicRead));
        saveImage(username,image);
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
        S3Object awsImage = getAWSImage(username);
        try {
            InputStream is = new BufferedInputStream(awsImage.getObjectContent());
            return IOUtils.toByteArray(is);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    protected S3Object getAWSImage(String username) throws AmazonS3Exception {
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
