package me.liskoh.counter.dto.input;

import jakarta.validation.constraints.NotBlank;

public record UsernameInputDTO (@NotBlank(message = "Username is required") String username) {
}
