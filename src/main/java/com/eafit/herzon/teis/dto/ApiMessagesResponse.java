package com.eafit.herzon.teis.dto;

import java.util.List;

/**
 * Represents a standardized API response with a list of messages and an error status.
 */
public class ApiMessagesResponse {
  private final boolean error;
  private final List<String> messages;

  /**
   * Constructor for the ApiResponse class.
   *
   * @param error    Whether the response is an error
   * @param messages The list of messages
   */
  public ApiMessagesResponse(boolean error, List<String> messages) {
    this.error = error;
    this.messages = messages;
  }

  /**
   * Get the error status of the response.
   *
   * @return Whether the response is an error
   */
  public boolean isError() {
    return error;
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
