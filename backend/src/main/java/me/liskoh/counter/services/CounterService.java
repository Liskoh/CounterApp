package me.liskoh.counter.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.liskoh.counter.dto.response.impl.CounterResponseDTO;
import me.liskoh.counter.entities.CounterEntity;
import me.liskoh.counter.entities.UserEntity;
import me.liskoh.counter.exceptions.impl.CounterNotFoundException;
import me.liskoh.counter.exceptions.impl.UserNotFoundException;
import me.liskoh.counter.repositories.CounterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CounterService {

    private final UserService userService;
    private final CounterRepository counterRepository;

    public ResponseEntity<CounterResponseDTO> findPersonalCounter(long id) {
        return userService.getFromAuth().getCounters().stream()
                .filter(counter -> counter.getId() == id)
                .findFirst()
                .map(CounterResponseDTO::of)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CounterNotFoundException(id));
    }

    public CounterEntity findCounter(UserEntity user, long id) {
        return user.getCounters().stream()
                .filter(counter -> counter.getId() == id)
                .findFirst()
                .orElseThrow(() -> new CounterNotFoundException(id));
    }

    public ResponseEntity<CounterResponseDTO> findCounter(long id) {
        return counterRepository.findById(id)
                .map(CounterResponseDTO::of)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CounterNotFoundException(id));
    }

    public ResponseEntity<Set<CounterResponseDTO>> findPersonalCounters() {
        Set<CounterResponseDTO> counters = userService.getFromAuth().getCounters().stream()
                .map(CounterResponseDTO::of)
                .collect(Collectors.toSet());
        return ResponseEntity.ok().body(counters);
    }

    public ResponseEntity<Set<CounterResponseDTO>> findCounters(UserEntity user) {
        return ResponseEntity.ok().body(mapCounters(user.getCounters()));
    }

    public ResponseEntity<Set<CounterResponseDTO>> findCounters(@NonNull String username) {
        final UserEntity user = userService.findByUsername(username);

        return ResponseEntity.ok().body(mapCounters(user.getCounters()));
    }

    public Set<CounterResponseDTO> mapCounters(Collection<CounterEntity> entities) {
        return entities.stream()
                .map(CounterResponseDTO::of)
                .collect(Collectors.toSet());
    }
}