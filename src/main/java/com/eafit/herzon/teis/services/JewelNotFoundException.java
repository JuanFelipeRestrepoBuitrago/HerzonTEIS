package com.eafit.herzon.teis.services;

/**
 * Exception thrown when a jewel is not found in the system.
 */
public class JewelNotFoundException extends RuntimeException {

  /**
   * Constructs a new JewelNotFoundException with the specified message.
   *
   * @param message the detail message
   */
  public JewelNotFoundException(String message) {
    super(message);
  }
}