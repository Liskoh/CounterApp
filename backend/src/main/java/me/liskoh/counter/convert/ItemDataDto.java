package me.liskoh.counter.convert;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDataDto {
    private long id;
    private String name;
    private String server;
    private long lot_1;
    private long lot_10;
    private long lot_100;
    private long craft_cost;
    private long gain;
    private String created_at;
}
