package be.ordina.ordineo.service.impl;

import be.ordina.ordineo.config.AWSClient;
import be.ordina.ordineo.repository.ImageRepository;
import be.ordina.ordineo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    AWSClient awsClient;

    @Override
    public String uploadToAWS(String username) {
        return null;
    }

    @Override
    public String saveImage(String username, String url) {
        return null;
    }
}
