package com.eafit.herzon.teis.exceptions;

/**
 * The InvalidUserException class is an exception thrown when a user is invalid.
 */
public class InvalidUserException extends RuntimeException {
  /**
   * Constructor of the InvalidUserException class.
   *
   * @param message the message of the exception.
   */
  public InvalidUserException(String message) {
    super(message);
  }
}
