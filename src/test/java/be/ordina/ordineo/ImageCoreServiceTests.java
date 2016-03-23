package be.ordina.ordineo;

import be.ordina.ordineo.model.Image;
import be.ordina.ordineo.repository.ImageRepository;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by DeDu on 17/03/2016.
 */
public class ImageCoreServiceTests {

    private ImageRepository repositoryMock = Mockito.mock(ImageRepository.class);

    @Test
    public void getImage() throws Exception {

        //Temp test

        Image image = new Image();
        image.setImage("/images/Turbots.jpg");
        image.setUsername("Turbots");

        when(repositoryMock.findByUsernameIgnoreCase("Turbots")).thenReturn(image);

        assertEquals(repositoryMock.findByUsernameIgnoreCase("Turbots"), image);
    }

}

