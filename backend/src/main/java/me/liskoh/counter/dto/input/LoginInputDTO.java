package me.liskoh.counter.dto.input;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginInputDTO {

    @NotBlank(message = "Username cannot be blank")
    private final String username;

    @NotBlank(message = "Password cannot be blank")
    private final String password;

    public LoginInputDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
