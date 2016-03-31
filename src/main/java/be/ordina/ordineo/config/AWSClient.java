package be.ordina.ordineo.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by DeDu on 29/03/2016.
 */
@Component
public class AWSClient extends AmazonS3Client {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Autowired
    BasicAWSCredentials basicAWSCredentials(){
        return new BasicAWSCredentials(accessKey,secretKey);
    }

    public AWSClient() {
         setEndpoint("s3.eu-central-1.amazonaws.com");
    }

}
