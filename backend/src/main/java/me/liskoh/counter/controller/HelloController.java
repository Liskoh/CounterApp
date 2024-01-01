package me.liskoh.counter.controller;

import me.liskoh.counter.entities.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/hello")
public class HelloController {

    @GetMapping
    public String hello() {
        return "Hello World!";
    }
}
