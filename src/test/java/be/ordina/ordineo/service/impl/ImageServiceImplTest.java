package be.ordina.ordineo.service.impl;

import be.ordina.ordineo.model.Image;
import be.ordina.ordineo.repository.ImageRepository;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyNoMoreInteractions;

/**
 * Created by gide on 04/04/16.
 */
public class ImageServiceImplTest {

    @Mock
    ImageRepository imageRepository;

    @Mock
    AmazonS3Client amazonS3Client;

    @InjectMocks
    ImageServiceImpl imageService;

    private String username = "test";

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void uploadToAWSAndSave() throws IOException {
        String url = "https://test.com";

        imageService.uploadToAWS(username, url);

        verify( amazonS3Client ).putObject( any(PutObjectRequest.class) );
        verify( imageRepository ).findByUsernameIgnoreCase(username);
        verify( imageRepository ).save( any(Image.class) );
        verifyNoMoreInteractions( amazonS3Client );
        verifyNoMoreInteractions( imageRepository );
    }

    @Test(expected = IOException.class)
    public void uploadToAWSFail() throws IOException {
        String url = "https://test.com";

        when( amazonS3Client.putObject( any(PutObjectRequest.class)) ).thenThrow( Exception.class );

        imageService.uploadToAWS(username, url);

        verify( amazonS3Client ).putObject(any(PutObjectRequest.class));
        verifyNoMoreInteractions( amazonS3Client );
        verifyNoMoreInteractions( imageRepository );
    }

    @Test(expected = NullPointerException.class)
    public void getImageNull() throws IOException {
        imageService.getImage( username );

        verify( imageRepository ).findByUsernameIgnoreCase(username);
        verifyNoMoreInteractions( amazonS3Client );
        verifyNoMoreInteractions( imageRepository );
    }

    @Test(expected = IOException.class)
    public void getImageFail() throws IOException {
        Image image = new Image();
        image.setImage( "testing" );

        when( imageRepository.findByUsernameIgnoreCase(username) ).thenReturn( image );

        imageService.getImage( username );

        verify( imageRepository ).findByUsernameIgnoreCase(username);
        verify( amazonS3Client ).getObject( ImageServiceImpl.BUCKET, image.getImage());
        verifyNoMoreInteractions( amazonS3Client );
        verifyNoMoreInteractions( imageRepository );
    }

    @Test
    public void getImage() throws IOException {
        Image image = new Image();
        image.setImage( "testing" );

        S3Object object = mock(S3Object.class);
        S3ObjectInputStream inputStream = mock(S3ObjectInputStream.class);

        mockStatic( IOUtils.class );

        when( imageRepository.findByUsernameIgnoreCase(username) ).thenReturn( image );
        when( amazonS3Client.getObject( ImageServiceImpl.BUCKET, image.getImage() )).thenReturn( object );
        when( object.getObjectContent() ).thenReturn( inputStream );

        imageService.getImage( username );

        verify( imageRepository ).findByUsernameIgnoreCase(username);
        verify( amazonS3Client ).getObject( ImageServiceImpl.BUCKET, image.getImage());
        verifyNoMoreInteractions( amazonS3Client );
        verifyNoMoreInteractions( imageRepository );
    }

    @Test(expected = NullPointerException.class)
    public void deleteImageNull() {
        imageService.deleteImage( username );

        verify(imageRepository).findByUsernameIgnoreCase( username );
        verifyNoMoreInteractions( amazonS3Client );
        verifyNoMoreInteractions( imageRepository );
    }

    @Test
    public void deleteImage() throws Exception {
        Image image = new Image();
        image.setImage("test");

        when( imageRepository.findByUsernameIgnoreCase( username ) ).thenReturn( image );

        imageService.deleteImage( username );

        verify(imageRepository).findByUsernameIgnoreCase( username );
        verify(amazonS3Client).deleteObject( ImageServiceImpl.BUCKET, image.getImage() );
        verify(imageRepository).delete( image );
        verifyNoMoreInteractions( amazonS3Client );
        verifyNoMoreInteractions( imageRepository );
    }
}