package be.ordina.ordineo.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by DeDu on 29/03/2016.
 */
@Configuration
public class AWSClientConfiguration {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Bean
    AWSCredentials basicAWSCredentials(){
        return new BasicAWSCredentials(accessKey,secretKey);
    }

    @Bean
    public AmazonS3Client amazonS3Client(){
        AmazonS3Client client = new AmazonS3Client(basicAWSCredentials(),new ClientConfiguration().withSocketTimeout(0).withProtocol(Protocol.HTTP));
        client.setS3ClientOptions(new S3ClientOptions().withPathStyleAccess(true));
        client.setEndpoint("s3.eu-central-1.amazonaws.com");
        return client;
    }
}
