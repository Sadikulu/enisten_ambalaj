package com.kss.exception;

import com.kss.exception.message.ApiResponseError;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class KSSAmbalajExceptionHandler extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(KSSAmbalajExceptionHandler.class);

    private ResponseEntity<Object> buildResponseEntity(ApiResponseError error) {
        logger.error(error.getMessage()); //  exception fırlarsa mesajını loggladık
        return new ResponseEntity<>(error, error.getStatus());

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException ( ResourceNotFoundException ex, WebRequest request  )   {
        ApiResponseError error =  new ApiResponseError( HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false));

        /*
         *  Map<String,String> map= new HashMap<>();
         *  map.put("time", LocalDateTime.now().toString());
         *  map.put("message", ex.getMessage());
         *  return new ResponseEntity<>(map,HttpStatus.CREATED);
         */


        return buildResponseEntity(error);
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                           @NotNull HttpHeaders headers, @NotNull HttpStatus status, WebRequest request) {

        List<String> errors = ex.getBindingResult().getFieldErrors(). // bütün field errorlarını get ile aldım
                stream().
                map(DefaultMessageSourceResolvable::getDefaultMessage).//bütün errorların getMessage() metodunu alıyorum
                        toList();

        ApiResponseError error = new  ApiResponseError(HttpStatus.BAD_REQUEST,
                errors.get(0),
                request.getDescription(false));

        return buildResponseEntity(error);


    }

    @Override
    protected @NotNull ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, @NotNull HttpHeaders headers,
                                                                 @NotNull HttpStatus status, WebRequest request) {
        ApiResponseError error = new  ApiResponseError(HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false));
        return buildResponseEntity(error);
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
                                                                           @NotNull HttpHeaders headers, @NotNull HttpStatus status, WebRequest request) {
        ApiResponseError error = new  ApiResponseError(HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getDescription(false));
        return buildResponseEntity(error);
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                           @NotNull HttpHeaders headers, @NotNull HttpStatus status, WebRequest request) {
        ApiResponseError error = new  ApiResponseError(HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false));
        return buildResponseEntity(error);
    }

    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<Object> handleConflictException(ConflictException ex, WebRequest request) {
        ApiResponseError error = new ApiResponseError(HttpStatus.CONFLICT,
                ex.getMessage(),
                request.getDescription(false));
        return buildResponseEntity(error);
    }


    // Security ile ilgili Exceptionlar handle adiliyor

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ApiResponseError error = new ApiResponseError(HttpStatus.FORBIDDEN,
                ex.getMessage(),
                request.getDescription(false));
        return buildResponseEntity(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        ApiResponseError error = new ApiResponseError(HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false));
        return buildResponseEntity(error);
    }


    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        ApiResponseError error = new ApiResponseError(HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false));
        return buildResponseEntity(error);
    }

    @ExceptionHandler(ImageFileException.class)
    protected ResponseEntity<Object> handleImageFileException(ImageFileException ex, WebRequest request) {
        ApiResponseError error = new ApiResponseError(HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false));
        return buildResponseEntity(error);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException ( RuntimeException ex, WebRequest  request  )   {

        ApiResponseError error =  new ApiResponseError( HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getDescription(false)  );

        return buildResponseEntity(error);

    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGeneralException ( Exception ex, WebRequest  request  )   {

        ApiResponseError error =  new ApiResponseError( HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getDescription(false)  );

        return buildResponseEntity(error);

    }

}
