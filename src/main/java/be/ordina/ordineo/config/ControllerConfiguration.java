package be.ordina.ordineo.config;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@ControllerAdvice
public class ControllerConfiguration {

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "User not found")
    public void userNotFound(){}

    @ExceptionHandler(AmazonS3Exception.class)
    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason = "Image (Amazon S3 key) not found")
    public void imageNotFound(){}

    @ExceptionHandler(IOException.class)
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason = "Unable to read or write image data")
    public void ioException(){}

}
