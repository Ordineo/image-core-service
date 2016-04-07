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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ImageCoreApplication.class)
@WebAppConfiguration
public class ImageCoreServiceIntegrationTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
    }

    @Test
    public void saveGetAndDeleteImage() throws Exception {
        // Create
        mockMvc.perform(
                post("/api/images/url/a")
                        .content("https://media.licdn.com/mpr/mprx/0_PhhQv7mNs0uKASzlbdjhkYacsOhpkDnBTAp8XAf-Z0Dyz3zZlEjhkL7tIxTrA7zjcCj8FHat4s8yQpLq6wldCNmN7s8pQpXU-wlkc7tqV8AzQwv--fT5lSF82L"))
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
    }

    @Test
    public void getUserFail() throws Exception {
        mockMvc.perform(
                get("/api/images/dfetrygvngvfhtyupqaz"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void getImageFail() throws Exception {
        mockMvc.perform(
                get("/api/images/Unknown"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

}
