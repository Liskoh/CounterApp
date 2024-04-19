package me.liskoh.counter.exceptions;

import lombok.Getter;
import me.liskoh.counter.dto.response.impl.ErrorResponseDTO;
import org.springframework.http.HttpStatus;

@Getter
public class CounterAppException extends RuntimeException {

    private final HttpStatus status;
    private final ErrorResponseDTO errorResponseDTO;

    public CounterAppException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.errorResponseDTO = ErrorResponseDTO.of(message);
    }
}
