package me.liskoh.counter.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Annotation;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CounterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long creationMillis;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public CounterEntity(UserEntity user, String title, String description) {
        this.user = user;
        this.creationMillis = System.currentTimeMillis();
        this.title = title;
        this.description = description;
    }
}
