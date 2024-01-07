package me.liskoh.counter.dto.response.impl;

import lombok.Getter;
import me.liskoh.counter.dto.response.AResponseDTO;

@Getter
public class HelloResponseDTO extends AResponseDTO {

    private static final String MESSAGE = "Hello World!";

    private final long timestamp;

    public HelloResponseDTO() {
        super(MESSAGE);

        this.timestamp = System.currentTimeMillis();
    }
}
