package me.liskoh.counter.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.liskoh.counter.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final UsernameNotFoundException USERNAME_NOT_FOUND_EXCEPTION;

    static {
        USERNAME_NOT_FOUND_EXCEPTION = new UsernameNotFoundException("User not found");
    }

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<UserEntity> result = userService.findByUsername(username);

            return result.orElseThrow(() -> USERNAME_NOT_FOUND_EXCEPTION);
    }
}
