package me.liskoh.counter.dto.input;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

public record LoginInputDTO(@NotBlank(message = "Username cannot be blank") String username,
                            @NotBlank(message = "Password cannot be blank") String password) {
}
