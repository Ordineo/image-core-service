package be.ordina.ordineo.controller;

import be.ordina.ordineo.service.ImageService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.InputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by gide on 05/04/16.
 */
public class ImageControllerTest {

    @Mock
    ImageService imageService;

    @InjectMocks
    ImageController imageController;

    String username = "test";

    @Before
    public void setup() {
        initMocks( this );
    }

    @Test
    public void uploadImageByUrl() throws Exception {
        String url = "https://test.com";

        imageController.uploadImageByUrl(username, url);

        verify( imageService ).uploadToAWS(username, url);
        verifyNoMoreInteractions( imageService );
    }

    @Test
    public void uploadImage() throws Exception {
        MultipartFile file = mock( MultipartFile.class );
        InputStream inputStream = mock( InputStream.class );

        when( file.getInputStream() ).thenReturn( inputStream );

        imageController.uploadImage(username, file);

        verify( imageService ).uploadToAWS(username, inputStream);
        verifyNoMoreInteractions( imageService );
    }

    @Test
    public void getImage() throws Exception {
        byte[] file = new byte[]{};

        when( imageService.getImage(username) ).thenReturn( file );

        byte[] result = imageController.getImage(username);

        assertEquals(file, result);

        verify( imageService ).getImage(username);
        verifyNoMoreInteractions( imageService );
    }

    @Test
    public void deleteImage() throws Exception {
        imageController.deleteImage(username);

        verify( imageService ).deleteImage(username);
        verifyNoMoreInteractions( imageService );
    }
}