package me.liskoh.counter.services;

import lombok.RequiredArgsConstructor;
import me.liskoh.counter.entities.UserEntity;
import me.liskoh.counter.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity create(String username, String password) {
        return save(new UserEntity(username, passwordEncoder.encode(password)));
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public UserEntity getFromAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserEntity)) {
            return null;
        }

        return (UserEntity) authentication.getPrincipal();
    }
}
