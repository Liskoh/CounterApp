package me.liskoh.counter.convert;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "price_updates")
public class PriceUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private long lot1;
    private long lot10;
    private long lot100;
    private long craftCost;
    private long gain;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
