package me.liskoh.counter.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.liskoh.counter.dto.response.impl.CounterResponseDTO;
import me.liskoh.counter.entities.CounterEntity;
import me.liskoh.counter.entities.UserEntity;
import me.liskoh.counter.repositories.CounterRepository;
import me.liskoh.counter.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/counter")
public class CounterController {

    private final UserService userService;
    private final CounterRepository counterRepository;

    @PostMapping("/add")
    public ResponseEntity<?> add() {
        userService.getFromAuth().ifPresent(user -> {
            log.info("User: {}", user);
            counterRepository.save(new CounterEntity(user, "message", "description"));
        });

        return ResponseEntity.ok().body("Add counter");
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll() {
        Optional<UserEntity> user = userService.getFromAuth();

        if (user.isPresent()) {
            Set<CounterResponseDTO> counters = user.get().getCounters().stream()
                    .map(CounterResponseDTO::of)
                    .collect(java.util.stream.Collectors.toSet());

            return ResponseEntity.ok().body(counters);
        }

        return ResponseEntity.ok().body("No counters");
    }

}
