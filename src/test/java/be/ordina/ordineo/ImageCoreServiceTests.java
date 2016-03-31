package be.ordina.ordineo;

import be.ordina.ordineo.config.AWSClientConfiguration;
import be.ordina.ordineo.controller.ImageController;
import be.ordina.ordineo.model.Image;
import be.ordina.ordineo.repository.ImageRepository;
import com.amazonaws.services.s3.AmazonS3Client;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by DeDu on 17/03/2016.
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes=ImageCoreApplication.class)
public class ImageCoreServiceTests {

    public static final String BUCKET = "ordineo";

    @Mock
    private ImageRepository repositoryMock;

    @Mock
    private AmazonS3Client awsClientMock;

    @InjectMocks
    private ImageController controller;

    @Before
    public void setup(){
        initMocks(this);
    }

    @Test
    public void findByUsername() throws Exception {
        Image image = new Image();
        image.setImage("ProfilePictures/Turbots.jpg");
        image.setUsername("Turbots");
        when(repositoryMock.findByUsernameIgnoreCase("Turbots")).thenReturn(image);
        assertEquals(repositoryMock.findByUsernameIgnoreCase("Turbots"), image);

    }

}

