package me.liskoh.counter.repositories;

import me.liskoh.counter.entities.UserEntity;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {

    private static final String DEFAULT_USERNAME = "Default";
    private static final String DEFAULT_PASSWORD = "Password";

    @Autowired
    private UserRepository userRepository;

    @Test
    public void save_Returns_User_With_Id() {
        final UserEntity user = new UserEntity(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        final UserEntity savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0L);
    }

    @Test
    public void findByUsername_Returns_User() {
        final UserEntity user = new UserEntity(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        final UserEntity savedUser = userRepository.save(user);

        final Optional<UserEntity> result = userRepository.findByUsername(DEFAULT_USERNAME);
        assertThat(result).isPresent();

        final UserEntity foundUser = result.get();
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(savedUser.getId());
    }

    @Test
    public void findByUsername_Returns_Empty() {
        final Optional<UserEntity> result = userRepository.findByUsername(DEFAULT_USERNAME);
        assertThat(result).isEmpty();
    }

    @Test
    public void findAll_Returns_Users() {
        final UserEntity user = new UserEntity(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        userRepository.save(user);

        final List<UserEntity> result = userRepository.findAll();
        assertThat(result.size()).isGreaterThan(0);
    }

    @Test
    public void findAll_Returns_Empty() {
        final List<UserEntity> result = userRepository.findAll();
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void deleteById_Deletes_User() {
        final UserEntity user = new UserEntity(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        final UserEntity savedUser = userRepository.save(user);
        assertThat(savedUser).isNotNull();

        userRepository.deleteById(savedUser.getId());

        final Optional<UserEntity> result = userRepository.findById(savedUser.getId());
        assertThat(result).isEmpty();
    }
}
