package com.travelplanner.repository;

import com.travelplanner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing User data.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a User entity by its username.
     *
     * @param username the username to search for.
     * @return the User entity with the given username, or null if no such user exists.
     */
    User findUserByUsername(String username);
}
