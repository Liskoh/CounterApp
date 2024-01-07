package me.liskoh.counter.services;

import lombok.RequiredArgsConstructor;
import me.liskoh.counter.configuration.PasswordConfiguration;
import me.liskoh.counter.dto.response.AResponseDTO;
import me.liskoh.counter.dto.response.impl.ErrorResponseDTO;
import me.liskoh.counter.dto.response.impl.LoginResponseDTO;
import me.liskoh.counter.dto.response.impl.RegisterResponseDTO;
import me.liskoh.counter.entities.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public ResponseEntity<AResponseDTO> login(String username, String password) {
//        final Optional<UserEntity> result = userService.findByUsername(username);
//
//        if (result.isEmpty()) {
//            return ResponseEntity.badRequest().body(ErrorResponseDTO.USER_NOT_FOUND);
//        }

//        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        } catch (AuthenticationException ignored) {
//            return ResponseEntity.badRequest().body(ErrorResponseDTO.INVALID_CREDENTIALS);
//        }

        final UserDetails userDetails = userService.findByUsername(username);
        final String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok().body(LoginResponseDTO.of(userDetails, token));
    }

    public ResponseEntity<AResponseDTO> register(String username, String password) {
        final boolean exists = userService.existsByUsername(username);

        if (exists) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorResponseDTO.USER_ALREADY_EXISTS);
        }

        final UserEntity user = userService.create(username, password);
        final String token = jwtService.generateToken(user);

        return ResponseEntity.ok().body(RegisterResponseDTO.of(user, token));
    }
}
