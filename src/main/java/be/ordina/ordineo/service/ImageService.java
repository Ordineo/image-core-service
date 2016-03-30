package be.ordina.ordineo.service;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;

import java.io.IOException;

public interface ImageService {

    void uploadToAWS(String image,String url)throws IOException;

    void saveImage(String username, String image);

    byte[] getImage(String username) throws IOException;

    S3Object getAWSImage(String username)throws AmazonS3Exception;

    void deleteImage(String username);
}
