package be.ordina.ordineo.service;


public interface ImageService {

    String uploadToAWS(String username);

    String saveImage(String username, String url);
}
