package me.liskoh.counter.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
//    @Id
//    private Long id;

    private String name;
    private String email;
    private String password;
}
