package be.ordina.ordineo.controller;

import be.ordina.ordineo.model.Image;
import be.ordina.ordineo.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by DeDu on 17/03/2016.
 */
@RestController
@RequestMapping(value = "/api/images")
public class ImageController {

    @Autowired
    private ImageRepository imageRepository;


    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public void uploadImage(@RequestParam("file") MultipartFile image) {
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("image-core-service/src/main/resources/images/" + image.getOriginalFilename())));
            FileCopyUtils.copy(image.getInputStream(), stream);
            stream.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = ("/{username}"), produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getImage(@PathVariable String username) throws IllegalArgumentException, NullPointerException, IOException{
        Image image = imageRepository.findByUsernameIgnoreCase(username);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();

        InputStream is = this.getClass().getResourceAsStream(image.getImage());
        BufferedImage img = ImageIO.read(is);

        if(image.getImage().contains(".jpg")) {
            ImageIO.write(img, "jpg", bao);
        }
        else {
            ImageIO.write(img, "png", bao);
        }

        return bao.toByteArray();

    }

}