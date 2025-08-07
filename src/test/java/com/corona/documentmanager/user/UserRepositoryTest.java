package com.corona.documentmanager.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private static final String TEST_PASSWORD = "password123";
    private static final String TEST_EMAIL = "test@example.com";
    private static final String NONEXISTENT_USERNAME = "nonexistentUser";

    @Test
    public void testFindByUsername() {
        User user = new User();
        user.setUsername(TEST_EMAIL);
        user.setPassword(TEST_PASSWORD);

        entityManager.persist(user);
        entityManager.flush();

        Optional<User> found = userRepository.findByUsername(TEST_EMAIL);
        assertTrue(found.isPresent());
        assertEquals(TEST_EMAIL, found.get().getUsername());

        Optional<User> notFound = userRepository.findByUsername(NONEXISTENT_USERNAME);
        assertTrue(notFound.isEmpty());
    }
}