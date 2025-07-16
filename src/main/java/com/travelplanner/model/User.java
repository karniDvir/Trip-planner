package com.travelplanner.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * Entity class representing a user in the system.
 */
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @NotEmpty(message = "Username is mandatory")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotEmpty(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    /**
     * Gets the user's ID.
     * @return the user's ID.
     */
    public Long getId() {
        return user_id;
    }

    /**
     * Sets the user's ID.
     * @param id the new ID for the user.
     */
    public void setId(Long id) {
        this.user_id = id;
    }

    /**
     * Gets the user's username.
     * @return the user's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     * @param username the new username for the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the user's password.
     * @return the user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     * @param password the new password for the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's email.
     * @return the user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     * @param email the new email for the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
