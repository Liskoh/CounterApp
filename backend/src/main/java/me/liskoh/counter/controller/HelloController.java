package me.liskoh.counter.controller;

import me.liskoh.counter.entities.UserEntity;
import me.liskoh.counter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/hello")
public class HelloController {

    private final UserRepository userRepository;

    @Autowired
    public HelloController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/create")
    public ResponseEntity<UserEntity> create() {
        String name = UUID.randomUUID().toString();
        String email = name + "@example.com";
        String password = UUID.randomUUID().toString();

        UserEntity user = new UserEntity(name, email, password);
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }
}
