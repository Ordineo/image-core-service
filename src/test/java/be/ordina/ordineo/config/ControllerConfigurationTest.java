package be.ordina.ordineo.config;

import be.ordina.ordineo.ImageCoreApplication;
import be.ordina.ordineo.mock.ImageServiceBeanMockConfiguration;
import be.ordina.ordineo.service.ImageService;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by gide on 05/04/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ImageCoreApplication.class, ImageServiceBeanMockConfiguration.class})
@WebAppConfiguration
public class ControllerConfigurationTest {

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    ImageService imageService;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(wac).build();

        this.imageService = (ImageService) this.wac.getBean("imageServiceMock");

        //reset mocked behaviour
        reset( imageService );
    }

    @Test
    public void userNotFound() throws Exception {
        when(imageService.getImage(anyString())).thenThrow(NullPointerException.class);

        mockMvc.perform(
                get("/api/images/testFake"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void imageNotFound() throws Exception {
        when(imageService.getImage(anyString())).thenThrow(AmazonS3Exception.class);

        mockMvc.perform(
                get("/api/images/testFake"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void ioException() throws Exception {
        when(imageService.getImage(anyString())).thenThrow(IOException.class);

        mockMvc.perform(
                get("/api/images/testFake"))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }
}