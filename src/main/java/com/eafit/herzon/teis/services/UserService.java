package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.exceptions.UserNotFoundException;
import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.repositories.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class to manage User operations.
 */
@Service
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  /**
   * Constructs a new UserService with the required dependencies.
   *
   * @param userRepository the repository for user data access
   * @param passwordEncoder the encoder for password encryption
   */
  @Autowired
  public UserService(UserRepository userRepository, 
                     BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Saves a new user after encrypting the password.
   *
   * @param customUser the user to save
   * @return the saved user
   * @throws IllegalArgumentException if the user is null
   */
  @Transactional
  public CustomUser save(CustomUser customUser) {
    if (customUser == null) {
      throw new IllegalArgumentException("User cannot be null");
    }
    customUser.setPassword(passwordEncoder.encode(customUser.getPassword()));
    return userRepository.save(customUser);
  }

  /**
   * Finds a user by username.
   *
   * @param username the username of the user to find
   * @return the user if found
   * @throws IllegalArgumentException if the username is null or empty
   * @throws UserNotFoundException if no user is found with the given username
   */
  @Transactional(readOnly = true)
  public CustomUser findByUsername(String username) {
    validateString(username, "Username");
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(
            "User not found with username: " + username));
  }

  /**
   * Finds a user by email.
   *
   * @param email the email of the user to find
   * @return the user if found
   * @throws IllegalArgumentException if the email is null or empty
   * @throws UserNotFoundException if no user is found with the given email
   */
  @Transactional(readOnly = true)
  public CustomUser findByEmail(String email) {
    validateString(email, "Email");
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException(
            "User not found with email: " + email));
  }

  /**
   * Authenticates a user by checking the password.
   *
   * @param username the username of the user
   * @param password the password to verify
   * @return true if credentials match, false otherwise
   * @throws IllegalArgumentException if username or password is null or empty
   */
  @Transactional(readOnly = true)
  public boolean login(String username, String password) {
    validateString(username, "Username");
    validateString(password, "Password");
    return userRepository.findByUsername(username)
        .map(user -> passwordEncoder.matches(password, user.getPassword()))
        .orElse(false);
  }

  /**
   * Registers a new user in the system.
   *
   * @param customUser the user to register
   * @return the registered user
   * @throws IllegalArgumentException if the user is null, or if email/username is already taken
   */
  @Transactional
  public CustomUser register(CustomUser customUser) {
    if (customUser == null) {
      throw new IllegalArgumentException("User cannot be null");
    }
    // Check for existing email or username
    if (userRepository.existsByEmail(customUser.getEmail())
        || userRepository.existsByUsername(customUser.getUsername())) {
      throw new IllegalArgumentException(
          "Ya existe un usuario registrado con estos datos");
    }
    // Ensure role is not null, assign USER by default if not provided
    if (customUser.getRole() == null) {
      customUser.setRole(CustomUser.Role.USER);
    }
    return save(customUser);
  }

  /**
   * Retrieves all users from the database.
   *
   * @return a list of all users
   */
  @Transactional(readOnly = true)
  public List<CustomUser> getAllUsers() {
    return userRepository.findAll();
  }

  /**
   * Validates that the provided string is not null or empty.
   *
   * @param value the string to validate
   * @param fieldName the name of the field being validated (for error messaging)
   * @throws IllegalArgumentException if the string is invalid
   */
  private void validateString(String value, String fieldName) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(fieldName + " cannot be null or " 
          + "empty");
    }
  }
}