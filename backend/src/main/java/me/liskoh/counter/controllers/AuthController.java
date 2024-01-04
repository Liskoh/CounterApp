package me.liskoh.counter.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.liskoh.counter.dto.input.LoginInputDTO;
import me.liskoh.counter.dto.input.RegisterInputDTO;
import me.liskoh.counter.dto.response.AResponseDTO;
import me.liskoh.counter.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AResponseDTO> register(@Valid @RequestBody RegisterInputDTO input) {
        return authService.register(input.getUsername(), input.getPassword());
    }

    @PostMapping("/login")
    public ResponseEntity<AResponseDTO> login(@Valid @RequestBody LoginInputDTO input) {
        return authService.login(input.getUsername(), input.getPassword());
    }
}