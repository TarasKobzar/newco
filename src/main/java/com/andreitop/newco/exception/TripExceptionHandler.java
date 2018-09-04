package com.andreitop.newco.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class TripExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
                                                   final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        List<String> errors = new ArrayList();
        exception.getBindingResult().getFieldErrors().stream()
                .forEach((p) -> errors.add(p.getField() + ": " + p.getDefaultMessage()));
        exception.getBindingResult().getGlobalErrors().stream()
                .forEach((p) -> errors.add(p.getObjectName() + ": " + p.getDefaultMessage()));

        final DtoApiError dtoApiError = new DtoApiError(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), errors);
        return handleExceptionInternal(exception, dtoApiError, headers, dtoApiError.getStatus(), request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleValidation(final ConstraintViolationException exception) {

        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }

        final DtoApiError dtoApiError = new DtoApiError(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), errors);
        return new ResponseEntity<>(dtoApiError, new HttpHeaders(), dtoApiError.getStatus());
    }

    @Override
    public ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException exception,
                                                   final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        String error = exception.getParameterName() + " parameter is missing";
        final DtoApiError dtoApiError = new DtoApiError(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), error);
        return new ResponseEntity<>(dtoApiError, new HttpHeaders(), dtoApiError.getStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        String error = exception.getName() + " should be of type " + exception.getRequiredType().getName();

        final DtoApiError apiError = new DtoApiError(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleEverything(final AccessDeniedException exception, final HttpHeaders headers) {

        String error = "Access to a file is denied";
        final DtoApiError dtoApiError = new DtoApiError(HttpStatus.FORBIDDEN, exception.getLocalizedMessage(), error);
        return new ResponseEntity<>(dtoApiError, headers, dtoApiError.getStatus());
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException exception,
                                                   final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        String error = "Method is fail";
        final DtoApiError dtoApiError = new DtoApiError(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(), error);
        return new ResponseEntity<>(dtoApiError, headers, dtoApiError.getStatus());
    }

    @Override
    public ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException exception,
                                                   final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        final String error = "No handler found for " + exception.getHttpMethod() + " " + exception.getRequestURL();
        final DtoApiError dtoApiError = new DtoApiError(HttpStatus.NOT_FOUND, exception.getLocalizedMessage(), error);
        return new ResponseEntity<>(dtoApiError, new HttpHeaders(), dtoApiError.getStatus());
    }

    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException exception,
                                                   final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        StringBuilder error = new StringBuilder();
        error.append(exception.getMethod());
        error.append(" method is not supported for this request. Supported methods are ");
        exception.getSupportedHttpMethods().forEach(t -> error.append(t + " "));

        final DtoApiError dtoApiError = new DtoApiError(HttpStatus.METHOD_NOT_ALLOWED, exception.getLocalizedMessage(), error.toString());
        return new ResponseEntity<>(dtoApiError, new HttpHeaders(), dtoApiError.getStatus());
    }

    @Override
    public ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException exception,
                                                   final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        StringBuilder error = new StringBuilder();
        error.append(exception.getContentType());
        error.append(" media type is not supported. Supported media types are ");
        exception.getSupportedMediaTypes().forEach(t -> error.append(t + ", "));

        final DtoApiError dtoApiError = new DtoApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception.getLocalizedMessage(), error.substring(0, error.length() - 2));
        return new ResponseEntity<>(dtoApiError, new HttpHeaders(), dtoApiError.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(final Exception exception) {

        String error = "error occurred";
        final DtoApiError dtoApiError = new DtoApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage(), error);
        return new ResponseEntity<>(dtoApiError, new HttpHeaders(), dtoApiError.getStatus());
    }

}
