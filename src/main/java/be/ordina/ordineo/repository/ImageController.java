package be.ordina.ordineo.repository;

import be.ordina.ordineo.client.ImageClient;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by DeDu on 17/03/2016.
 */
@RestController
public class ImageController implements ImageClient {

    public ImageController() {}

    @Override
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

    @Override
    public byte[] getImage(@PathVariable String url) {
        try {
            System.out.println("URL: " + url);
            InputStream is = this.getClass().getResourceAsStream("/images/" + url);
            BufferedImage img = ImageIO.read(is);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", bao);

            return bao.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}