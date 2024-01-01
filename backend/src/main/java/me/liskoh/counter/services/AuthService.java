package me.liskoh.counter.services;

import lombok.RequiredArgsConstructor;
import me.liskoh.counter.dto.response.AResponseDTO;
import me.liskoh.counter.dto.response.impl.ErrorResponseDTO;
import me.liskoh.counter.dto.response.impl.LoginResponseDTO;
import me.liskoh.counter.entities.UserEntity;
import org.hibernate.cache.spi.support.EntityReadOnlyAccess;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<AResponseDTO> login(String username, String password) {
        final Optional<UserEntity> result = userService.findByUsername(username);

        if (result.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorResponseDTO.USER_NOT_FOUND);
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        final UserDetails userDetails = result.get();
        final String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok().body(LoginResponseDTO.of(userDetails, token));
    }
}
