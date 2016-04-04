package be.ordina.ordineo.service;

import java.io.IOException;

public interface ImageService {

    void uploadToAWS(String image,String url)throws IOException;
    byte[] getImage(String username) throws IOException;
    void deleteImage(String username);
}
