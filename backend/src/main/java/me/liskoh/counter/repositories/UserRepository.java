package me.liskoh.counter.repositories;

import me.liskoh.counter.entities.User;

import java.util.Optional;

//@Repository
//public interface UserRepository extends JpaRepository<User, Long> {
public interface UserRepository {
    Optional<User> findByEmail(String email);
}
