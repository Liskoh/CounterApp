package me.liskoh.counter.services;

import lombok.RequiredArgsConstructor;
import me.liskoh.counter.entities.UserEntity;
import me.liskoh.counter.exceptions.impl.UserNotFoundException;
import me.liskoh.counter.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity create(String username, String password) {
        return create(new UserEntity(username, password));
    }

    public UserEntity create(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return save(user);
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public UserEntity getFromAuth() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserEntity user) {
            return user;
        }

        throw new UserNotFoundException();
    }
}
