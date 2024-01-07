package me.liskoh.counter.controllers.advice;

import me.liskoh.counter.dto.response.impl.ErrorResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationExceptionAdvice {

    private static final String VALIDATION_FAILED = "Validation failed";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, Object> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage
                ));

        return ResponseEntity.badRequest().body(ErrorResponseDTO.of(VALIDATION_FAILED, errors));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponseDTO> handleHandlerMethodValidationException(HandlerMethodValidationException exception) {
        return ResponseEntity.badRequest().body(ErrorResponseDTO.of(VALIDATION_FAILED));
    }
}
