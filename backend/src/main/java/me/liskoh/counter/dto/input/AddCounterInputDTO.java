package me.liskoh.counter.dto.input;

import jakarta.validation.constraints.NotBlank;

public record AddCounterInputDTO(@NotBlank(message = "Message cannot be blank") String message,
                                 @NotBlank(message = "Description cannot be blank") String description) {
}
