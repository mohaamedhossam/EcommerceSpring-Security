package com.springecommerce.error;

import com.springecommerce.entity.ErrorMessage;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    public RestResponseEntityExceptionHandler() {
    }

    @ExceptionHandler({CustomerNotFoundException.class})
    public ResponseEntity<ErrorMessage> CustomerNotFoundException(CustomerNotFoundException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<ErrorMessage> ProductNotFoundException(ProductNotFoundException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler({CustomerAlreadyOwnUnconfimedOrderException.class})
    public ResponseEntity<ErrorMessage> CustomerAlreadyOwnUnconfimedOrderException(CustomerAlreadyOwnUnconfimedOrderException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(message);
    }

    @ExceptionHandler({OrderProductNotFoundException.class})
    public ResponseEntity<ErrorMessage> OrderProductNotFoundException(OrderProductNotFoundException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler({EmpytCartException.class})
    public ResponseEntity<ErrorMessage> CustomerAlreadyOwnUnconfimedOrderException(EmpytCartException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(message);
    }

    @ExceptionHandler({AlreadyReviewedException.class})
    public ResponseEntity<ErrorMessage> CustomerAlreadyOwnUnconfimedOrderException(AlreadyReviewedException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(message);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler({DataAccessException.class})
    public ResponseEntity<Object> handleDataAccessException(DataAccessException exception, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(message);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        List<String> errors = (List)ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        body.put("errors", errors);
        return new ResponseEntity(body, headers, status);
    }
}
