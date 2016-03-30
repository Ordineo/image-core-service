package be.ordina.ordineo.controller;

import be.ordina.ordineo.config.AWSClient;
import be.ordina.ordineo.model.Image;
import be.ordina.ordineo.repository.ImageRepository;
import be.ordina.ordineo.service.ImageService;
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
    ImageService service;

    @RequestMapping(method = RequestMethod.POST, value = "/{username}")
    public void uploadImageByUrl(@PathVariable String username, @RequestBody String url) throws IOException,NullPointerException {
        service.uploadToAWS(username.toLowerCase(),url);
    }

    @RequestMapping(method = RequestMethod.GET, value = ("/{username}"), produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable String username) throws AmazonS3Exception, IOException,NullPointerException{
       return service.getImage(username);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = ("/{username}"))
    public void deleteImage(@PathVariable String username) {
        service.deleteImage(username);
    }

}