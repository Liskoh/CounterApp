package me.liskoh.counter.dto.response.impl;

import lombok.Getter;
import me.liskoh.counter.dto.response.AResponseDTO;

@Getter
public class RegisterResponseDTO extends AResponseDTO {

    private final String username;
    private final String email;
    private final String token;

    public RegisterResponseDTO(String message, String username, String email, String token) {
        super(message);
        this.username = username;
        this.email = email;
        this.token = token;
    }
}
