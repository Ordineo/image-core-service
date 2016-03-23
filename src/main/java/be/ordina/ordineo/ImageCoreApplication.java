package be.ordina.ordineo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;

/**
 * Created by DeDu on 17/03/2016.
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableRetry
public class ImageCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageCoreApplication.class, args);
    }

}