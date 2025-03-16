package com.eafit.herzon.teis.exceptions;

/**
 * Exception thrown when a user is not found in the system.
 */
public class UserNotFoundException extends RuntimeException {

  /**
   * Constructs a new UserNotFoundException with the specified message.
   *
   * @param message the detail message
   */
  public UserNotFoundException(String message) {
    super(message);
  }
}