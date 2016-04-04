package be.ordina.ordineo;

import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableRetry
public class ImageCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageCoreApplication.class, args);
    }

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Bean
    @Primary
    public AmazonS3Client amazonS3Client() {
        amazonS3Client.setEndpoint("s3.eu-central-1.amazonaws.com");
        return amazonS3Client;
    }

}