package com.ericgibson.website.code;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.ericgibson.website.code.TestingConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersRepositoryTest {

    @Qualifier("usersRepository")
    @Autowired
    private UsersRepository repository;
    private final User user = new User();

    @Before
    public void setup() {
        repository.deleteAll();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setEnabled(true);
    }

    @After
    public void teardown() {
        repository.deleteAll();
    }

    @Test
    public void shouldCreateUser() {
        long startingCount = repository.count();
        long expectedCount = startingCount + 1;

        repository.save(user);

        assertThat(repository.count()).isEqualTo(expectedCount);
    }

    @Test
    public void shouldRetrieveUser() {
        User user = repository.save(this.user);

        Optional<User> optionalUser = repository.findById(user.getId());

        if (optionalUser.isPresent()) {
            User updatedUser = optionalUser.get();
            assertThat(updatedUser.getUsername()).isEqualTo(user.getUsername());
            assertThat(updatedUser.getPassword()).isEqualTo(user.getPassword());
            assertThat(updatedUser.getEmail()).isEqualTo(user.getEmail());
            assertThat(updatedUser.isEnabled()).isTrue();
        } else
            throw new AssertionError("expecting Optional to be not null");
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFound() {
        Integer id = 0;

        Optional<User> optionalUser = repository.findById(id);

        assertThatThrownBy(optionalUser::get)
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");
    }

    @Test
    public void shouldRetrieveUsers() {
        User anotherUser = new User();
        anotherUser.setUsername("another");
        anotherUser.setEmail("another@example.com");
        anotherUser.setPassword(PASSWORD);

        long startingCount = repository.count();
        long expectedCount = startingCount + 2;
        repository.saveAll(Arrays.asList(user, anotherUser));

        assertThat(repository.findAll()).hasSize((int) expectedCount);
    }

    @Test
    public void shouldUpdateUser() {
        String updatedName = "updated_username";
        User user = repository.save(this.user);
        this.user.setUsername(updatedName);
        long expectedCount = repository.count();
        repository.save(user);

        assertThat(repository.count()).isEqualTo(expectedCount);
        Optional<User> optionalUser = repository.findById(user.getId());

        if (optionalUser.isPresent()) {
            User updatedUser = optionalUser.get();
            assertThat(updatedUser.getUsername()).isEqualTo(updatedName);
            assertThat(updatedUser.getPassword()).isEqualTo(PASSWORD);
            assertThat(updatedUser.getEmail()).isEqualTo(EMAIL);
        } else
            throw new AssertionError("expecting Optional to be not null");
    }

    @Test
    public void shouldDeleteUser() {
        User user = repository.save(this.user);
        long startingCount = repository.count();
        long expectedCount = startingCount - 1;

        repository.deleteById(user.getId());

        assertThat(repository.count()).isEqualTo(expectedCount);
    }

    @Test
    public void shouldThrowExceptionWhenDeleteFails() {
        Integer id = 0;

        assertThatThrownBy(() -> repository.deleteById(id))
                .isInstanceOf(EmptyResultDataAccessException.class)
                .hasMessageContaining("No class com.ericgibson.website.code.User entity with id 0 exists!");
    }
}