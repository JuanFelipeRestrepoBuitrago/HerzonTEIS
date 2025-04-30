package com.eafit.herzon.teis.dto;

import java.util.List;

/**
 * Represents a standardized API response with a list of auctions and an success
 * status.
 */
public class ApiAuctionsResponse extends ApiResponseBase {
  private final List<AuctionDto> auctions;
  private final int page;
  private final int size;
  private final long totalElements;
  private final int totalPages;

  /**
   * Constructor for the ApiResponse class.
   *
   * @param status   Whether the operation was successful or not.
   * @param messages The list of messages
   * @param auctions The list of auctions
   * @param page     The current page number
   * @param size     The number of items per page
   * @param totalElements The total number of elements
   * @param totalPages The total number of pages
   */
  public ApiAuctionsResponse(
      boolean status,
      List<String> messages,
      List<AuctionDto> auctions,
      int page,
      int size,
      long totalElements,
      int totalPages
  ) {
    super(status, messages);
    this.auctions = auctions;
    this.page = page;
    this.size = size;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
  }

  /**
   * Get the list of auctions in the response.
   *
   * @return The list of auctions
   */
  public List<AuctionDto> getAuctions() {
    return this.auctions;
  }

  /**
   * Get the current page number.
   *
   * @return The current page number
   */
  public int getPage() {
    return this.page;
  }

  /**
   * Get the number of items per page.
   *
   * @return The number of items per page
   */
  public int getSize() {
    return this.size;
  }

  /**
   * Get the total number of elements.
   *
   * @return The total number of elements
   */
  public long getTotalElements() {
    return this.totalElements;
  }

  /**
   * Get the total number of pages.
   *
   * @return The total number of pages
   */
  public int getTotalPages() {
    return this.totalPages;
  }
}
