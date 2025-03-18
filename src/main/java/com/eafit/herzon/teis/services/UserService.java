package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.dto.UserForm;
import com.eafit.herzon.teis.exceptions.InvalidUserException;
import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.repositories.UserRepository;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class to manage User operations.
 */
@Service
public class UserService {

  private UserRepository userRepository;
  private BCryptPasswordEncoder passwordEncoder;

  /**
   * Constructor for the UserService class.
   *
   * @param userRepository    the UserRepository object
   * @param passwordEncoder  the BCryptPasswordEncoder object
   */
  public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Registers a new user in the system.
   *
   * @param user the user to register
   * @return the registered user
   * @throws IllegalArgumentException if the user is null, or if email/username is
   *                                  already taken
   */
  @Transactional
  public CustomUser register(UserForm user) {
    if (user == null) {
      throw new InvalidUserException("El usuario no puede ser nulo");
    }
    // Check for existing email or username
    if (userRepository.existsByEmail(user.getEmail())
        || userRepository.existsByUsername(user.getUsername())) {
      throw new InvalidUserException(
          "Ya existe un usuario registrado con estos datos");
    }
    // Ensure role is not null, assign USER by default if not provided
    if (!user.getPassword().equals(user.getPasswordConfirmation())) {
      throw new InvalidUserException("Las contraseñas no coinciden");
    }

    CustomUser newUser = new CustomUser(
        user.getUsername(),
        user.getEmail(),
        passwordEncoder.encode(user.getPassword()));
    newUser.setName(user.getName());
    newUser.setAddress(user.getAddress());
    newUser.setRole(CustomUser.Role.USER);
    userRepository.save(newUser);
    return newUser;
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
   * Retrieves a user by their username.
   *
   * @param username the username of the user to retrieve
   * @return the user with the specified username
   */
  @Transactional(readOnly = true)
  public CustomUser getUserByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new InvalidUserException("Usuario no encontrado"));
  }
}