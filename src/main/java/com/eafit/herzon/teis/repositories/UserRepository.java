package com.eafit.herzon.teis.repositories;

import com.eafit.herzon.teis.models.CustomUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {

  /**
   * Finds a user by their email.
   *
   * @param email the user's email
   * @return an Optional containing the user if found, empty otherwise
   */
  Optional<CustomUser> findByEmail(String email);

  /**
   * Finds a user by their username.
   *
   * @param username the user's username
   * @return an Optional containing the user if found, empty otherwise
   */
  Optional<CustomUser> findByUsername(String username);

  /**
   * Checks if a user exists with the given email.
   *
   * @param email the email to check
   * @return true if a user exists with the email, false otherwise
   */
  boolean existsByEmail(String email);

  /**
   * Checks if a user exists with the given username.
   *
   * @param username the username to check
   * @return true if a user exists with the username, false otherwise
   */
  boolean existsByUsername(String username);

  /**
   * Finds a user by their role.
   *
   * @param role the user's role
   * @return the user with the specified role
   */
  List<CustomUser> findAllByRole(CustomUser.Role role);
}