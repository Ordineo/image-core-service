package be.ordina.ordineo.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

/**
 * Created by DeDu on 23/03/2016.
 */
@ControllerAdvice
public class ControllerConfiguration {

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND,reason = "User not found")
    public void userNotFound(){}

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason = "Image not found")
    public void imageNotFound(){}

    @ExceptionHandler(IOException.class)
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason = "Unable to read or write image data")
    public void ioException(){}

}
