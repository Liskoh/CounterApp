package me.liskoh.counter.convert;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItemDto {
    // Getters et Setters
    private String name;
    private List<PriceUpdateDto> priceUpdates;
}

