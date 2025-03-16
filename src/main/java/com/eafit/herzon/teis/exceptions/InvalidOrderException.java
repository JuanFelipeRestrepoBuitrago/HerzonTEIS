package com.eafit.herzon.teis.exceptions;

/**
 * The InvalidOrderException class is an exception thrown when an order is invalid.
 */
public class InvalidOrderException extends RuntimeException {
  /**
   * Constructor of the InvalidOrderException class.
   *
   * @param message the message of the exception.
   */
  public InvalidOrderException(String message) {
    super(message);
  }
}
