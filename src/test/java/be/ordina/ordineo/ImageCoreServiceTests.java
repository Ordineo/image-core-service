package be.ordina.ordineo;

import be.ordina.ordineo.controller.ImageController;
import be.ordina.ordineo.model.Image;
import be.ordina.ordineo.repository.ImageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by DeDu on 17/03/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ImageCoreApplication.class)
public class ImageCoreServiceTests {

    @Mock
    private ImageRepository repositoryMock;

    @InjectMocks
    private ImageController controller;

    @Before
    public void setup(){
        initMocks(this);
    }

    @Test
    public void findByUsername() throws Exception {

        Image image = new Image();
        image.setImage("/images/Turbots.jpg");
        image.setUsername("Turbots");

        when(repositoryMock.findByUsernameIgnoreCase("Turbots")).thenReturn(image);

        assertEquals(repositoryMock.findByUsernameIgnoreCase("Turbots"), image);

    }

    @Test
     public void getImage() throws Exception {

        Image image = new Image(1L, "Nivek", "/images/Nivek.png");

        when(repositoryMock.findByUsernameIgnoreCase("Nivek")).thenReturn(image);

        assertNotNull(controller.getImage("Nivek"));
        assertTrue(controller.getImage("Nivek").length > 1);

    }

}

