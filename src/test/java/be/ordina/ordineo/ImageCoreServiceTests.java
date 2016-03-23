package be.ordina.ordineo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by DeDu on 17/03/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ImageCoreApplication.class)
@WebAppConfiguration
public class ImageCoreServiceTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();

    }

    @Test
    public void getImage() throws Exception {
        mockMvc.perform(
                get("/api/images/Turbots").accept(MediaType.IMAGE_JPEG))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getImageFail() throws Exception {
        mockMvc.perform(
                get("/api/images/Derya"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

}

