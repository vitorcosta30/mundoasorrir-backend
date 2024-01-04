package com.mundoasorrir.mundoasorrirbackend.Auth;

import com.mundoasorrir.mundoasorrirbackend.Controllers.FileUploadController;
import com.mundoasorrir.mundoasorrirbackend.Message.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import java.io.EOFException;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(EOFException.class)
    public ResponseEntity<?> handleEOFexception() {
        logger.error("Error handling file");
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ErrorMessage.FILE_ERROR);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<?> handleMultiPartException() {
        logger.error("Error handling file");
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ErrorMessage.FILE_ERROR);


        // Nothing to do
    }

}
