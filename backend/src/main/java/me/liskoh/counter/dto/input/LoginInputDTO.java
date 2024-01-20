package me.liskoh.counter.dto.input;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginInputDTO(
        @NotBlank(message = "Username cannot be blank") @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long")
        String username,
        @NotBlank(message = "Password cannot be blank")
        String password) {

}
