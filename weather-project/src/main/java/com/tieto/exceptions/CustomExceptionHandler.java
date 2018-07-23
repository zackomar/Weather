package com.tieto.exceptions;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tieto.models.dtos.ErrorDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ CityNotFoundException.class })
    public final ResponseEntity<ErrorDTO> notFound(Exception exception) {
        log.warn(createLog(exception));
        return createResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    public final ResponseEntity<ErrorDTO> badRequest(Exception exception) {
        log.warn(createLog(exception));
        return createResponse(exception, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Create response from given parameters.
     * 
     * @param exception, status
     * @return {@link ResponseEntity} {@link ErrorDTO} response entity
     */ 
    private ResponseEntity<ErrorDTO> createResponse(Exception exception, HttpStatus status) {
        return new ResponseEntity<>(
                new ErrorDTO(
                        status != null ? String.format("%s", status.value()) : "[unknown]",
                        exception != null ? exception.getMessage() : "[unknown]",
                        LocalDateTime.now()
                ),
                status
        );
    }

    /**
     * Formats given exception to readable String.
     * 
     * @param exception
     * @return {@link String} formatted exception
     */ 
    private String createLog(Exception exception) {
        return String.format(
                "%s%n%s",
                exception,
                Arrays.toString(exception.getStackTrace()).replaceAll(", ", "\n")
        );
    }
}
