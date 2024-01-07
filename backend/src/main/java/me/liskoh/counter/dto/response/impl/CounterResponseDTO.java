package me.liskoh.counter.dto.response.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.liskoh.counter.dto.response.AResponseDTO;
import me.liskoh.counter.entities.CounterEntity;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CounterResponseDTO {

    private long id;
    private long creationMillis;
    private String title;
    private String description;

    public static CounterResponseDTO of(CounterEntity entity) {
        return new CounterResponseDTO(
                entity.getId(),
                entity.getCreationMillis(),
                entity.getTitle(),
                entity.getDescription()
        );
    }
}
