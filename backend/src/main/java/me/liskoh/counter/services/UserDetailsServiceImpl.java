package me.liskoh.counter.services;

import lombok.extern.slf4j.Slf4j;
import me.liskoh.counter.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<UserEntity> result = userService.findByUsername(username);
            if (result.isPresent()) {
                return result.get();
            }
        } catch (Exception exception) {
            log.error("Error while loading user by username", exception);
        }

        throw new UsernameNotFoundException("User not found");
    }
}
