package me.liskoh.counter.controllers;

import me.liskoh.counter.dto.response.impl.HelloResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {

    @GetMapping
    public ResponseEntity<HelloResponseDTO> getHello() {
        return ResponseEntity.ok().body(new HelloResponseDTO());
    }

}
