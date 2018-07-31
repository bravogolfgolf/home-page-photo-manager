package com.ericgibson.website.security;

import org.junit.Before;
import org.junit.Test;

import static com.ericgibson.website.TestingConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    private final User user = new User();

    @Before
    public void setup() {
        user.setId(ID_INTEGER);
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setEnabled(true);
        user.setEmail(EMAIL);
    }

    @Test
    public void shouldCreateUser() {
        assertThat(user.getId()).isEqualTo(ID_INTEGER);
        assertThat(user.getUsername()).isEqualTo(USERNAME);
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
        assertThat(user.isEnabled()).isTrue();
        assertThat(user.getEmail()).isEqualTo(EMAIL);
    }
}