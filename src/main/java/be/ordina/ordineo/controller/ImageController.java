package be.ordina.ordineo.controller;

import be.ordina.ordineo.config.AWSClient;
import be.ordina.ordineo.model.Image;
import be.ordina.ordineo.repository.ImageRepository;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by DeDu on 17/03/2016.
 */
@RestController
@RequestMapping(value = "/api/images")
public class ImageController {

    public static final String BUCKET = "ordineo";

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private AWSClient awsClient;

    @RequestMapping(method = RequestMethod.POST, value = "/{username}")
    public void uploadImageByUrl(@PathVariable String username, @RequestBody String url) {


        url = url.replace("https:", "http:");


        try {
            URL imageUrl = new URL(url);
            URLConnection connection = imageUrl.openConnection();
            InputStream inputStream = connection.getInputStream();

            String image = "ProfilePictures/" + username.toLowerCase() + ".jpg";

            awsClient.putObject(new PutObjectRequest(BUCKET, image, inputStream, new ObjectMetadata())
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            Image newImage = new Image();
            newImage.setImage(image);
            newImage.setUsername(username);

            imageRepository.save(newImage);

        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = ("/{username}"), produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable String username) throws AmazonS3Exception, NullPointerException, IOException{
        Image image = imageRepository.findByUsernameIgnoreCase(username);

        S3Object object = awsClient.getObject(BUCKET, image.getImage());
        InputStream is = new BufferedInputStream(object.getObjectContent());

        ByteArrayOutputStream bao = new ByteArrayOutputStream();

        BufferedImage img = ImageIO.read(is);
        ImageIO.write(img, "jpg", bao);

        return bao.toByteArray();

    }

    @RequestMapping(method = RequestMethod.DELETE, value = ("/{username}"))
    public void deleteImage(@PathVariable String username) {
        Image image = imageRepository.findByUsernameIgnoreCase(username);

        awsClient.deleteObject(BUCKET, image.getImage());

        imageRepository.delete(image);

    }

}