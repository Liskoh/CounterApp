package me.liskoh.counter.dto.response.impl;

import lombok.Getter;
import me.liskoh.counter.dto.response.AResponseDTO;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorResponseDTO extends AResponseDTO {

    public static final ErrorResponseDTO USER_NOT_FOUND = new ErrorResponseDTO("User not found");
    public static final AResponseDTO USER_ALREADY_EXISTS = new ErrorResponseDTO("User already exists");
    public static final AResponseDTO INVALID_CREDENTIALS = new ErrorResponseDTO("Invalid credentials");

    private final Map<String, Object> errors;

    public ErrorResponseDTO(String message) {
        this(message, new HashMap<>());
    }

    public ErrorResponseDTO(String message, Map<String, Object> errors) {
        super(message);

        this.errors = errors;
    }

    public static ErrorResponseDTO of(String message, Map<String, Object> errors) {
        return new ErrorResponseDTO(message, errors);
    }
}
