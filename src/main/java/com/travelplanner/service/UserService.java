package com.travelplanner.service;

import com.travelplanner.model.User;
import com.travelplanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for managing User operations.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Saves a new user or updates an existing user.
     * Encrypts the user's password using BCrypt before saving.
     *
     * @param user The User object to save or update
     * @throws Exception If the username already exists
     */
    public void save(User user) throws Exception {
        user.setUsername(user.getUsername().trim());
        if (userRepository.findUserByUsername(user.getUsername()) != null) {
            throw new Exception("Username already exists!");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve
     * @return The User object if found, null otherwise
     */
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find
     * @return The User object if found, null otherwise
     */
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
