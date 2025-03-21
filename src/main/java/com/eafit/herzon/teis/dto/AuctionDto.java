package com.eafit.herzon.teis.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for creating and updating Auctions.
 */
public class AuctionDto {
  private Long auctionId;

  
  private LocalDateTime startDate;

  
  private LocalDateTime endDate;

  @NotNull(message = "El precio inicial es requerido")
  @Positive(message = "El precio inicial debe ser positivo")
  private Double startPrice;

  @Positive(message = "El precio actual debe ser positivo")
  private Double currentPrice;

  @NotNull(message = "El id de la joya es requerido")
  private Long jewelId;

  @NotNull(message = "La fecha de inicio es requerida")
  private String startDateString;
  @NotNull(message = "La fecha de fin es requerida")
  private String endDateString;

  
  @NotNull(message = "El estado de la subasta es requerido")
  private boolean status;

  /**
   * Constructs a new AuctionDTO with the specified start date, end date, start
   * price, and jewel ID.
   */
  public AuctionDto() {
    status = true;
  }

  /**
   * Returns the ID of the auction.
   *
   * @return the ID of the auction
   */
  public Long getAuctionId() {
    return auctionId;
  }

  /**
   * Sets the ID of the auction.
   *
   * @param auctionId the ID of the auction
   */
  public void setAuctionId(Long auctionId) {
    this.auctionId = auctionId;
  }

  /**
   * Returns the start date of the auction.
   *
   * @return the start date of the auction
   */
  public LocalDateTime getStartDate() {
    return startDate;
  }

  /**
   * Sets the start date of the auction.
   *
   * @param startDate the start date of the auction
   */
  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  /**
   * Returns the end date of the auction.
   *
   * @return the end date of the auction
   */
  public LocalDateTime getEndDate() {
    return endDate;
  }

  /**
   * Sets the end date of the auction.
   *
   * @param endDate the end date of the auction
   */
  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  /**
   * Returns the start price of the auction.
   *
   * @return the start price of the auction
   */
  public Double getStartPrice() {
    return startPrice;
  }

  /**
   * Sets the start price of the auction.
   *
   * @param startPrice the start price of the auction
   */
  public void setStartPrice(Double startPrice) {
    this.startPrice = startPrice;
  }

  /**
   * Returns the current price of the auction.
   *
   * @return the current price of the auction
   */
  public Double getCurrentPrice() {
    return currentPrice;
  }

  /**
   * Sets the current price of the auction.
   *
   * @param currentPrice the current price of the auction
   */
  public void setCurrentPrice(Double currentPrice) {
    this.currentPrice = currentPrice;
  }

  /**
   * Returns the ID of the jewel associated with the auction.
   *
   * @return the ID of the jewel associated with the auction
   */
  public Long getJewelId() {
    return jewelId;
  }

  /**
   * Sets the ID of the jewel associated with the auction.
   *
   * @param jewelId the ID of the jewel associated with the auction
   */
  public void setJewelId(Long jewelId) {
    this.jewelId = jewelId;
  }

  /**
   * Returns the start date of the auction as a string.
   *
   * @return the start date of the auction as a string
   */
  public String getStartDateString() {
    return startDateString;
  }

  /**
   * Sets the start date of the auction as a string.
   *
   * @param startDateString the start date of the auction as a string
   */
  public void setStartDateString(String startDateString) {
    this.startDateString = startDateString;
  }

  /**
   * Returns the end date of the auction as a string.
   *
   * @return the end date of the auction as a string
   */
  public String getEndDateString() {
    return endDateString;
  }

  /**
   * Sets the end date of the auction as a string.
   *
   * @param endDateString the end date of the auction as a string
   */
  public void setEndDateString(String endDateString) {
    this.endDateString = endDateString;
  }

  /**
   * Returns the status of the auction.
   *
   * @return the status of the auction
   */
  public boolean getStatus() {
    return status;
  }

  /**
   * Sets the status of the auction.
   *
   * @param status the status of the auction
   */
  public void setStatus(boolean status) {
    this.status = status;
  }
}