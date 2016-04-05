package be.ordina.ordineo.service;

import java.io.IOException;
import java.io.InputStream;

public interface ImageService {

    void uploadToAWS(String image,String url) throws IOException;

    void uploadToAWS(String username, InputStream inputStream) throws IOException;

    byte[] getImage(String username) throws IOException;

    void deleteImage(String username);
}
