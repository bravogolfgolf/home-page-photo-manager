package com.ericgibson.website.code;

import javax.persistence.*;

@Entity
@Table(schema = "eric_gibson", name = "Users")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private boolean enabled;
    @Column(unique = true, nullable = false)
    private String email;

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

    void setPassword(String password) {
        this.password = password;
    }

    String getPassword() {
        return password;
    }

    void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    boolean isEnabled() {
        return enabled;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getEmail() {
        return email;
    }
}