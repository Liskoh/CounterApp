package me.liskoh.counter.dto.response.impl;

import lombok.Getter;
import me.liskoh.counter.dto.response.AResponseDTO;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class RegisterResponseDTO extends AResponseDTO {

    private static final String SUCCESS = "User registered successfully";

    private final String username;
    private final String token;

    public RegisterResponseDTO(String username, String token) {
        super(SUCCESS);

        this.username = username;
        this.token = token;
    }

    public static RegisterResponseDTO of(UserDetails userDetails, String token) {
        return new RegisterResponseDTO(userDetails.getUsername(), token);
    }
}
