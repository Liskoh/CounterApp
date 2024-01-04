package me.liskoh.counter.dto.input;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RegisterInputDTO {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long")
    private final String username;

    @NotBlank(message = "Password cannot be blank")
    private final String password;

    public RegisterInputDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
