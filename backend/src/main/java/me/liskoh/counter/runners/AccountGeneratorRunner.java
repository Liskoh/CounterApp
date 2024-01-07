package me.liskoh.counter.runners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.liskoh.counter.constants.RoleEnum;
import me.liskoh.counter.entities.UserEntity;
import me.liskoh.counter.repositories.UserRepository;
import me.liskoh.counter.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountGeneratorRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public void run(String... args) {
        if (userRepository.count() != 0) {
            return;
        }

        log.info("User repository is empty, generating default accounts");

        List.of(RoleEnum.values()).forEach(role -> {
            final String name = role.name().toLowerCase();
            UserEntity user = new UserEntity(name, name);
            user.setRole(role);

            userService.create(user);
            log.info("Created default user: USERNAME: {}, PASSWORD: {}, AUTHORITIES: {}", user.getUsername(), name, user.getAuthorities());
        });
    }
}
