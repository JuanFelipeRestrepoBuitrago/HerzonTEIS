package com.eafit.herzon.teis.dto;

import java.util.List;

/**
 * Represents a standardized API response.
 */
public class ApiResponseBase {
  private final boolean status;
  private final List<String> messages;

  /**
   * Constructor for the ApiResponse class.
   *
   * @param status Whether the operation was successful or not.
   * @param messages The list of messages
   */
  public ApiResponseBase(boolean status, List<String> messages) {
    this.status = status;
    this.messages = messages;
  }

  /**
   * Get the status of the response.
   *
   * @return Whether the response was successful or not
   */
  public boolean getStatus() {
    return this.status;
  }

  /**
   * Get the list of messages in the response.
   *
   * @return The list of messages
   */
  public List<String> getMessages() {
    return messages;
  }
}
