package me.liskoh.counter.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.liskoh.counter.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/counter")
public class CounterController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> add() {
        log.info("User: {}", userService.getFromAuth());
        return ResponseEntity.ok().body("Add counter");
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body("Find all counter");
    }
}
