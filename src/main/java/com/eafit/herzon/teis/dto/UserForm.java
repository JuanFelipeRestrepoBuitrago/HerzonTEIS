package com.eafit.herzon.teis.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for capturing form input when creating a user.
 */
public class UserForm {
  @NotBlank(message = "El nombre de usuario es requerido")
  private String username;

  @NotBlank(message = "El email es requerido")
  @Email(message = "Debe ser un email válido")
  private String email;

  @NotBlank(message = "La contraseña es requerida")
  @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
  private String password;

  @NotBlank(message = "La confirmación de la contraseña es requerida")
  @Size(min = 8, message = "La confirmación de la contraseña debe tener al menos 8 caracteres")
  private String passwordConfirmation;

  @NotBlank(message = "El nombre completo es requerido")
  private String name;

  @NotBlank(message = "Dirección es requerida")
  private String address;
  
  /**
   * Get the password.
   *
   * @return The password
   */
  public String getUsername() {
    return username;
  }

  /**
   * Set the password.
   *
   * @param username The password
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Get the password.
   *
   * @return The password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Set the password.
   *
   * @param password The password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Get the password confirmation.
   *
   * @return The password confirmation
   */
  public String getPasswordConfirmation() {
    return passwordConfirmation;
  }

  /**
   * Set the password confirmation.
   *
   * @param passwordConfirmation The password confirmation
   */
  public void setPasswordConfirmation(String passwordConfirmation) {
    this.passwordConfirmation = passwordConfirmation;
  }

  /**
   * Get the name.
   *
   * @return The name
   */
  public String getName() {
    return name;
  }

  /**
   * Set the name.
   *
   * @param name The name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get the address.
   *
   * @return The address
   */
  public String getAddress() {
    return address;
  }

  /**
   * Set the address.
   *
   * @param address The address
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Get the email.
   *
   * @return The email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Set the email.
   *
   * @param email The email
   */
  public void setEmail(String email) {
    this.email = email;
  }
}
