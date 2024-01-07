package me.liskoh.counter.repositories;

import me.liskoh.counter.entities.CounterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterRepository extends JpaRepository<CounterEntity, Long> {

}
