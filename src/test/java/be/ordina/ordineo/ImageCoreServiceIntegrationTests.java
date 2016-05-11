package be.ordina.ordineo;

import be.ordina.ordineo.mock.ImageServiceBeanMockConfiguration;
import be.ordina.ordineo.service.ImageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ImageCoreApplication.class, ImageServiceBeanMockConfiguration.class})
@WebAppConfiguration
public class ImageCoreServiceIntegrationTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ImageService imageService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
    }

    @Test
    public void saveGetAndDeleteImage() throws Exception {
        String url = "https://www.google.be/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwipgJy6k9LMAhVLkiwKHU2tDK8QjRwIBw&url=http%3A%2F%2Fwww.bbc.com%2Fnews%2Ftechnology-34119243&psig=AFQjCNEPecvWNADYM-K0GjU_xqzh1DfDWQ&ust=1463060559396580";
        // Create
        mockMvc.perform(
                post("/api/images/url/a")
                        .content(url))
                .andExpect(status().isOk());

        // Get
        mockMvc.perform(
                get("/api/images/a").accept(MediaType.IMAGE_JPEG))
                .andExpect(status().isOk())
                .andReturn();

        // Delete
        mockMvc.perform(
                delete("/api/images/a"))
                .andExpect(status().isOk());

        Mockito.verify( imageService ).uploadToAWS("a", url);
        Mockito.verify( imageService ).getImage("a");
        Mockito.verify( imageService ).deleteImage("a");
        verifyNoMoreInteractions( imageService );
    }

    @Test
    public void getUserFail() throws Exception {
        String wrongUser = "dfetrygvngvfhtyupqaz";

        doThrow(new IOException()).when(imageService).getImage(wrongUser);

        mockMvc.perform(
                get("/api/images/"+wrongUser))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

}
