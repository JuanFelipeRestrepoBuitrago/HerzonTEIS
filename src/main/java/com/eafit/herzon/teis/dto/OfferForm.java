package com.eafit.herzon.teis.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO for capturing form input when creating an offer.
 */
public class OfferForm {

  /**
   * The id of the auction to place the offer.
   */
  @NotNull(message = "Auction Id is required")
  private Long auctionId;

  /**
   * The price of the offer.
   */
  @Positive(message = "Offer price must be a positive number")
  @NotNull(message = "Offer price is required")
  private Double offerPrice;

  /**
   * Get the auction id.

   * @return The auction id
   */
  public Long getAuctionId() {
    return auctionId;
  }

  /**
   * Set the auction id.

   * @param auction The auction id
   */
  public void setAuctionId(Long auction) {
    this.auctionId = auction;
  }

  /**
   * Get the offer price.

   * @return The offer price
   */
  public Double getOfferPrice() {
    return offerPrice;
  }

  /**
   * Set the offer price.

   * @param offerPrice The offer price
   */
  public void setOfferPrice(Double offerPrice) {
    this.offerPrice = offerPrice;
  }
}
