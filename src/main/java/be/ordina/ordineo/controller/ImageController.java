package be.ordina.ordineo.controller;

import be.ordina.ordineo.service.ImageService;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping(value = "/api/images")
public class ImageController {

    @Autowired
    ImageService service;

    @RequestMapping(method = RequestMethod.POST, value = "/url/{username}")
    public void uploadImageByUrl(@PathVariable String username, @RequestBody String url) throws IOException {
        service.uploadToAWS(username.toLowerCase(), url);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{username}")
    public void uploadImage(@PathVariable String username, @RequestParam("file") MultipartFile file) throws IOException {
        service.uploadToAWS(username.toLowerCase(), file.getInputStream());
    }

    @RequestMapping(method = RequestMethod.GET, value = ("/{username}"), produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable String username) throws AmazonS3Exception, IOException, NullPointerException{
       return service.getImage(username);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = ("/{username}"))
    public void deleteImage(@PathVariable String username) {
        service.deleteImage(username);
    }

}