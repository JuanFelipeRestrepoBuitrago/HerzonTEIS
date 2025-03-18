package com.eafit.herzon.teis.exceptions;

/**
 * The InvalidAuctionException class is an exception thrown when an auction is invalid.
 */
public class InvalidAuctionException extends RuntimeException {
  /**
   * Constructor of the InvalidAuctionException class.
   *
   * @param message the message of the exception.
   */
  public InvalidAuctionException(String message) {
    super(message);
  }
}
