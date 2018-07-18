package com.ericgibson.website.code;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Authorities")
class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String authority;

    void setId(Integer id) {
        this.id = id;
    }

    Integer getId() {
        return id;
    }

    void setUsername(String username) {
        this.username = username;
    }

    String getUsername() {
        return username;
    }

    void setAuthority(String authority) {
        this.authority = authority;
    }

    String getAuthority() {
        return authority;
    }
}