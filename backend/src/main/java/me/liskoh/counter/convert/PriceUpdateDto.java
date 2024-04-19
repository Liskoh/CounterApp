package me.liskoh.counter.convert;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PriceUpdateDto {
    private long lot1;
    private long lot10;
    private long lot100;
    private long craftCost;
    private long gain;
    private String timestamp;
}
