package be.ordina.ordineo.config;

import com.amazonaws.services.s3.AmazonS3Client;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by gide on 04/04/16.
 */
public class AWSConfigurationTest {

    @Mock
    AmazonS3Client amazonS3Client;

    @InjectMocks
    AWSConfiguration awsConfiguration;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void amazonS3ClientEndpoint() {
        awsConfiguration.amazonS3Client();

        verify(amazonS3Client).setEndpoint(anyString());
    }

}