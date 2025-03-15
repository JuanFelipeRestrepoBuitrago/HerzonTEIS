package com.eafit.herzon.teis.exceptions;

/**
 * The InvalidOfferException class is an exception thrown when an offer is invalid.
 */
public class InvalidOfferException extends RuntimeException {
  /**
   * Constructor of the InvalidOfferException class.
   *
   * @param message the message of the exception.
   */
  public InvalidOfferException(String message) {
    super(message);
  }
}
