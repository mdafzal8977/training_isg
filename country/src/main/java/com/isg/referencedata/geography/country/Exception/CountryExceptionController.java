package com.isg.referencedata.geography.country.Exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.isg.referencedata.geography.country.response.ResponseBean;

@ControllerAdvice
public class CountryExceptionController extends ResponseEntityExceptionHandler{

    @Autowired
    public ResponseBean response;


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
            ErrorModel error = new ErrorModel(HttpStatus.BAD_REQUEST, "validation error", ex.getMessage().split(";")[5]);

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    

       @Override
       protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
               HttpHeaders headers, HttpStatus status, WebRequest request) {
         
                ErrorModel error = new ErrorModel(HttpStatus.BAD_REQUEST, "Method not Supported, not exist",ex.getMessage());
              return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
           
       }
    


}


