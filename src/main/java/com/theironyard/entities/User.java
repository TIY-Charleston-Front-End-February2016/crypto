package com.theironyard.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by PiratePowWow on 3/17/16.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    int id;
    @Column(nullable = false, unique = true)
    String name;
    @Column(nullable = false)
    String passwordHash;

    public User(String name, String passwordHash) {
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
