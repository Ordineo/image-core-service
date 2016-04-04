package be.ordina.ordineo.config;

import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by gide on 04/04/16.
 */
@Configuration
public class AWSConfiguration {

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.endpoint}")
    private String endpoint;

    @Bean
    @Primary
    public AmazonS3Client amazonS3Client() {
        amazonS3Client.setEndpoint( endpoint );
        return amazonS3Client;
    }

}
