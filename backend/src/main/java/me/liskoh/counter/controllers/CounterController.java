package me.liskoh.counter.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.liskoh.counter.dto.input.UsernameInputDTO;
import me.liskoh.counter.dto.response.impl.CounterResponseDTO;
import me.liskoh.counter.entities.CounterEntity;
import me.liskoh.counter.entities.UserEntity;
import me.liskoh.counter.repositories.CounterRepository;
import me.liskoh.counter.services.CounterService;
import me.liskoh.counter.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/counter")
public class CounterController {

    private final UserService userService;
    private final CounterService counterService;
    private final CounterRepository counterRepository;

    @GetMapping("/find-personal-counter/{id}")
    public ResponseEntity<CounterResponseDTO> findPersonalCounter(@PathVariable @Min(1) long id) {
        return counterService.findPersonalCounter(id);
    }

    @GetMapping("/find-counter/{id}")
    public ResponseEntity<CounterResponseDTO> findCounter(@PathVariable @Min(1) long id) {
        return counterService.findCounter(id);
    }

    @GetMapping("/find-counters")
    public ResponseEntity<Set<CounterResponseDTO>> findPersonalCounters() {
        return counterService.findPersonalCounters();
    }

    @GetMapping("/find-counters/{username}")
    public ResponseEntity<Set<CounterResponseDTO>> findAllByUsername(@Valid UsernameInputDTO username) {
        return counterService.findCounters(username.username());
    }
}
