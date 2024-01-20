package me.liskoh.counter.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.liskoh.counter.dto.input.LoginInputDTO;
import me.liskoh.counter.dto.response.AResponseDTO;
import me.liskoh.counter.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @CrossOrigin("*")
    @PostMapping("/register")
    public ResponseEntity<AResponseDTO> register(@Valid @RequestBody LoginInputDTO input) {
        return authService.register(input.username(), input.password());
    }

    @CrossOrigin("*")
    @PostMapping("/login")
    public ResponseEntity<AResponseDTO> login(@Valid @RequestBody LoginInputDTO input) {
        return authService.login(input.username(), input.password());
    }
}
