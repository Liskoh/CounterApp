package me.liskoh.counter.dto.response.impl;

import me.liskoh.counter.dto.response.AResponseDTO;

public class ErrorResponseDTO extends AResponseDTO {

    public static final ErrorResponseDTO USER_NOT_FOUND = new ErrorResponseDTO("User not found");
    public static final AResponseDTO USER_ALREADY_EXISTS = new ErrorResponseDTO("User already exists");

    public ErrorResponseDTO(String message) {
        super(message);
    }
}
