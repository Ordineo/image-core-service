package be.ordina.ordineo.config;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.stereotype.Component;

/**
 * Created by DeDu on 29/03/2016.
 */
@Component
public class AWSClient extends AmazonS3Client {

    public AWSClient() {
        setEndpoint("s3.eu-central-1.amazonaws.com");
    }

}
