package me.liskoh.counter.dto.response.impl;

import lombok.Getter;
import me.liskoh.counter.dto.response.AResponseDTO;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class LoginResponseDTO extends AResponseDTO {

    private static final String SUCCESS = "Login successful";

    private final String username;
    private final String email;
    private final String token;

    public LoginResponseDTO(String username, String email, String token) {
        super(SUCCESS);

        this.username = username;
        this.email = email;
        this.token = token;
    }

    public static LoginResponseDTO of(UserDetails userDetails, String token) {
        return new LoginResponseDTO(
                userDetails.getUsername(),
                userDetails.getUsername(),
                token
        );
    }
}
