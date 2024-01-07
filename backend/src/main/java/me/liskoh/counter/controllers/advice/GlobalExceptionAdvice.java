package me.liskoh.counter.controllers.advice;

import me.liskoh.counter.dto.response.impl.ErrorResponseDTO;
import me.liskoh.counter.exceptions.CounterAppException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionAdvice {

    private static final String INVALID_CREDENTIALS = "Invalid credentials";

    @ExceptionHandler(CounterAppException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(CounterAppException exception) {
        int code = exception.getStatus().value();
        ErrorResponseDTO dto = exception.getErrorResponseDTO();

        return ResponseEntity.status(code).body(dto);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponseDTO> handleAuthenticationException(AuthenticationException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponseDTO.of(INVALID_CREDENTIALS));
    }
}
