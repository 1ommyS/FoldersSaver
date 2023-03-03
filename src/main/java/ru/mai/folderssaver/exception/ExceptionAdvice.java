package ru.mai.folderssaver.exception;

import jakarta.persistence.EntityNotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

/**
 * @author 1ommy
 * @version 28.02.2023
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            ConstraintViolationException.class,
            DataIntegrityViolationException.class,
            InvalidDataAccessApiUsageException.class
    })
    @ResponseBody
    public ApiError handleConstraintException(Exception exception) {
        log.error("exception caught by advice {} ", exception.getMessage());
        if (Objects.nonNull(exception.getCause())) {
            return wrapBusinessException(exception.getCause(), HttpStatus.BAD_REQUEST);
        }
        return wrapBusinessException(exception, HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class, FileNotFoundException.class, IOException.class, MalformedURLException.class})
    @ResponseBody
    public ApiError handleConstraintException(EntityNotFoundException exception) {
        log.error("exception caught by advice {} ", exception.getMessage());
        return wrapBusinessException(exception, HttpStatus.NOT_FOUND);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ApiError handleException(Exception exception) {
        log.error("exception caught by advice {} ", exception.getMessage());
        return wrapSystemException(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ApiError wrapBusinessException(Throwable throwable, HttpStatus status) {
        return ApiError.builder()
                .message(throwable.getMessage())
                .status(status)
                .type(ApiErrorType.BUSINESS)
                .build();
    }

    private ApiError wrapValidException(String message, HttpStatus status) {
        return ApiError.builder()
                .message(message)
                .status(status)
                .type(ApiErrorType.VALIDATION)
                .build();
    }

    private ApiError wrapSystemException(HttpStatus status) {
        return ApiError.builder()
                .status(status)
                .type(ApiErrorType.SYSTEM)
                .build();
    }
}
