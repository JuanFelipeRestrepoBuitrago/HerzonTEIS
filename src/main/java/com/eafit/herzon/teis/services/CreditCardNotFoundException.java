package com.eafit.herzon.teis.services;

/**
 * Exception thrown when a credit card is not found in the system.
 */
public class CreditCardNotFoundException extends RuntimeException {

  /**
   * Constructs a new CreditCardNotFoundException with the specified message.
   *
   * @param message the detail message
   */
  public CreditCardNotFoundException(String message) {
    super(message);
  }
}