package com.ericgibson.website.security;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static com.ericgibson.website.TestingConstants.*;


public class AuthorityTest {

    private final Authority authority = new Authority();

    @Test
    public void shouldCreateAuthority() {
        authority.setId(ID_INTEGER);
        authority.setUsername(USERNAME);
        authority.setAuthority(AUTHORITY_USER);
        Assertions.assertThat(authority.getId()).isEqualTo(ID_INTEGER);
        Assertions.assertThat(authority.getUsername()).isEqualTo(USERNAME);
        Assertions.assertThat(authority.getAuthority()).isEqualTo(AUTHORITY_USER);
    }
}