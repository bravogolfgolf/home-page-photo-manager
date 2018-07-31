package com.ericgibson.website.security;

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

import static com.ericgibson.website.TestingConstants.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorityRepositoryTest {

    @Qualifier("authorityRepository")
    @Autowired
    private AuthorityRepository repository;
    private final Authority authority = new Authority();

    @Before
    public void setup() {
        repository.deleteAll();
        authority.setUsername(USERNAME);
        authority.setAuthority(AUTHORITY_USER);
    }

    @After
    public void teardown() {
        repository.deleteAll();
    }

    @Test
    public void shouldCreateAuthority() {
        long startingCount = repository.count();
        long expectedCount = startingCount + 1;

        repository.save(authority);

        assertThat(repository.count()).isEqualTo(expectedCount);
    }

    @Test
    public void shouldRetrieveAuthority() {
        Authority authority = repository.save(this.authority);

        Optional<Authority> optionalAuthority = repository.findById(authority.getId());

        if (optionalAuthority.isPresent()) {
            Authority updatedAuthority = optionalAuthority.get();
            assertThat(updatedAuthority.getAuthority()).isEqualTo(authority.getAuthority());
        } else
            throw new AssertionError("expecting Optional to be not null");
    }

    @Test
    public void shouldThrowExceptionWhenAuthorityNotFound() {
        Integer id = 0;

        Optional<Authority> optionalAuthority = repository.findById(id);

        assertThatThrownBy(optionalAuthority::get)
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");
    }

    @Test
    public void shouldRetrieveAuthorities() {
        Authority anotherAuthority = new Authority();
        anotherAuthority.setUsername("another");
        anotherAuthority.setAuthority(AUTHORITY_ADMIN);

        long startingCount = repository.count();
        long expectedCount = startingCount + 2;
        repository.saveAll(Arrays.asList(authority, anotherAuthority));

        assertThat(repository.findAll()).hasSize((int) expectedCount);
    }

    @Test
    public void shouldUpdateAuthority() {
        String updated = AUTHORITY_ADMIN;
        Authority authority = repository.save(this.authority);
        this.authority.setAuthority(updated);
        long expectedCount = repository.count();
        repository.save(authority);

        assertThat(repository.count()).isEqualTo(expectedCount);
        Optional<Authority> optionalAuthority = repository.findById(authority.getId());

        if (optionalAuthority.isPresent()) {
            Authority updatedAuthority = optionalAuthority.get();
            assertThat(updatedAuthority.getAuthority()).isEqualTo(updated);
        } else
            throw new AssertionError("expecting Optional to be not null");
    }

    @Test
    public void shouldDeleteAuthority() {
        Authority authority = repository.save(this.authority);
        long startingCount = repository.count();
        long expectedCount = startingCount - 1;

        repository.deleteById(authority.getId());

        assertThat(repository.count()).isEqualTo(expectedCount);
    }

    @Test
    public void shouldThrowExceptionWhenDeleteFails() {
        Integer id = 0;

        assertThatThrownBy(() -> repository.deleteById(id))
                .isInstanceOf(EmptyResultDataAccessException.class)
                .hasMessageContaining("Authority entity with id 0 exists!");
    }
}