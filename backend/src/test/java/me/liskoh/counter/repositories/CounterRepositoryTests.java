package me.liskoh.counter.repositories;

import me.liskoh.counter.entities.CounterEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CounterRepositoryTests {

    private static final String DEFAULT_NAME = "Default";
    private static final String DEFAULT_CONTENT = "Content of the counter";

    @Autowired
    private CounterRepository counterRepository;
}
