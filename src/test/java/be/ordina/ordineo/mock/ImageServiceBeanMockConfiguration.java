package be.ordina.ordineo.mock;

import be.ordina.ordineo.service.ImageService;
import be.ordina.ordineo.service.impl.ImageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

/**
 * Created by gide on 05/04/16.
 */
@Configuration
@BeanMock
public class ImageServiceBeanMockConfiguration {

    @Bean
    @Primary
    public ImageService imageServiceMock() {
        return mock(ImageServiceImpl.class);
    }

}
