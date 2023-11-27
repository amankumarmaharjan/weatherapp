package com.weather.application.error;

import com.weather.application.error.model.APIErrorResponse;
import com.weather.application.error.model.Error;
import feign.FeignException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class, MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<Object> handleConstraintViolationException(RuntimeException ex, WebRequest request) {
        String message = "Invalid Query Parameters";
        Error error = new Error(HttpStatus.BAD_REQUEST, String.valueOf(HttpStatus.BAD_REQUEST.value()), message, ex);
        return buildResponseEntity(APIErrorResponse.builder().error(error).build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = "Invalid Query Parameters";
        Error error = new Error(HttpStatus.BAD_REQUEST, String.valueOf(HttpStatus.BAD_REQUEST.value()), message, ex);
        return buildResponseEntity(APIErrorResponse.builder().error(error).build());
    }

    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public ResponseEntity<Object> handleAuthenticationException(Exception ex) {
        String message = "User Name or Password incorrect";
        Error error = new Error(HttpStatus.UNAUTHORIZED, String.valueOf(HttpStatus.UNAUTHORIZED.value()), message, ex);
        return buildResponseEntity(APIErrorResponse.builder().error(error).build());
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(
            NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = "No Page Found";
        Error error = new Error(HttpStatus.NOT_FOUND, String.valueOf(HttpStatus.NOT_FOUND.value()), message, ex);
        return buildResponseEntity(APIErrorResponse.builder().error(error).build());
    }

    @ExceptionHandler({FeignException.FeignClientException.class})
    @ResponseBody
    public ResponseEntity<Object> feignClientException(FeignException.FeignClientException ex) {
        String message = "Downstream API Error";
        Error error = new Error(HttpStatus.valueOf(ex.status()), String.valueOf(ex.status()), message, ex);
        return buildResponseEntity(APIErrorResponse.builder().error(error).build());
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<Object> handleAllException(Exception ex) {
        String message = "Internal Server Error";
        Error error = new Error(HttpStatus.INTERNAL_SERVER_ERROR, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), message, ex);
        return buildResponseEntity(APIErrorResponse.builder().error(error).build());
    }

    private ResponseEntity<Object> buildResponseEntity(APIErrorResponse apiErrorResponse) {
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getError().getStatus());
    }

}