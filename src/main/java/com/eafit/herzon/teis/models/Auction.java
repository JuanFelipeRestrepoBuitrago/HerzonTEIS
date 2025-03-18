package com.eafit.herzon.teis.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * The Auction class represents an auction of a jewel in Herzon.
 */
@Entity
@Table(name = "auctions")
public class Auction {
  /**
   * The ID of the auction.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The date and time when the auction was created.
   */
  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  /**
   * The date and time when the auction was last updated. It is automatically
   * updated by Hibernate when the entity is modified.
   */
  @UpdateTimestamp
  @Column(name = "modified_at", nullable = false)
  private LocalDateTime modifiedAt;

  /**
   * The date and time when the auction starts.
   */
  @Column(name = "start_date", nullable = false)
  private LocalDateTime startDate;

  /**
   * The date and time when the auction ends.
   */
  @Column(name = "end_date", nullable = false)
  private LocalDateTime endDate;

  /**
   * The minimum or start price of the auction.
   */
  @Column(name = "start_price", nullable = false)
  private Double startPrice;

  /**
   * The current price of the auction.
   */
  @Column(name = "current_price", nullable = false)
  private Double currentPrice;

  /**
   * The Jewel associated with the auction.
   */
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "jewel_id", nullable = false)
  private Jewel jewel;

  /**
   * The list of offers which have been made to the auction.
   */
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "auction", 
      cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  @Fetch(FetchMode.SUBSELECT)
  private List<Offer> offers;

  /**
   * The state of the Auction. True if the auction is active, false otherwise.
   */
  @Column(name = "status", nullable = false, columnDefinition = "boolean default true")
  private boolean status;

  /**
   * Constructor for the Auction class.
   */
  public Auction() {
    this.offers = new ArrayList<>();
  }

  /**
   * Constructor for the Auction class.
   *
   * @param startDate The date and time when the auction starts.
   * @param endDate The date and time when the auction ends.
   * @param startPrice The minimum or start price of the auction.
   * @param currentPrice The current price of the auction.
   * @param jewel The Jewel associated with the auction.
   */
  public Auction(
      LocalDateTime startDate, LocalDateTime endDate, 
      Double startPrice, Double currentPrice, Jewel jewel
  ) { 
    this.startDate = startDate;
    this.endDate = endDate;
    this.startPrice = startPrice;
    this.currentPrice = currentPrice;
    this.jewel = jewel;
    this.offers = new ArrayList<>();
    this.status = true;
  }

  /**
   * Returns the ID of the auction.
   *
   * @return The ID of the auction.
   */
  public Long getId() {
    return id;
  }

  /**
   * Returns the date and time when the auction was created.
   *
   * @return The date and time when the auction was created.
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Returns the date and time when the auction was last updated.
   *
   * @return The date and time when the auction was last updated.
   */
  public LocalDateTime getModifiedAt() {
    return modifiedAt;
  }

  /**
   * Returns the date and time when the auction starts.
   *
   * @return The date and time when the auction starts.
   */
  public LocalDateTime getStartDate() {
    return startDate;
  }

  /**
   * Returns the date and time when the auction ends.
   *
   * @return The date and time when the auction ends.
   */
  public LocalDateTime getEndDate() {
    return endDate;
  }

  /**
   * Returns the minimum or start price of the auction.
   *
   * @return The minimum or start price of the auction.
   */
  public Double getStartPrice() {
    return startPrice;
  }

  /**
   * Returns the current price of the auction.
   *
   * @return The current price of the auction.
   */
  public Double getCurrentPrice() {
    return currentPrice;
  }

  /**
   * Returns the Jewel associated with the auction.
   *
   * @return The Jewel associated with the auction.
   */
  public Jewel getJewel() {
    return jewel;
  }

  /**
   * Returns the list of offers which have been made to the auction.
   *
   * @return The list of offers which have been made to the auction.
   */
  public List<Offer> getOffers() {
    return offers;
  }

  /**
   * Returns the status of the Auction. True if the auction is active, false otherwise.
   *
   * @return The status of the Auction. True if the auction is active, false otherwise.
   */
  public boolean getStatus() {
    return status;
  }

  /**
   * Sets the status of the Auction. True if the auction is active, false otherwise.
   *
   * @param status The status of the Auction. True if the auction is active, false otherwise.
   */
  public void setStatus(boolean status) {
    this.status = status;
  }

  /**
   * Sets the date and time when the auction starts.
   *
   * @param startDate The date and time when the auction starts.
   */
  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  /**
   * Sets the date and time when the auction ends.
   *
   * @param endDate The date and time when the auction ends.
   */
  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  /**
   * Sets the minimum or start price of the auction.
   *
   * @param startPrice The minimum or start price of the auction.
   */
  public void setStartPrice(Double startPrice) {
    this.startPrice = startPrice;
  }

  /**
   * Sets the current price of the auction.
   *
   * @param currentPrice The current price of the auction.
   */
  public void setCurrentPrice(Double currentPrice) {
    this.currentPrice = currentPrice;
  }

  /**
   * Sets the Jewel associated with the auction.
   *
   * @param jewel The Jewel associated with the auction.
   */
  public void setJewel(Jewel jewel) {
    this.jewel = jewel;
  }

  /**
   * Sets the list of offers which have been made to the auction.
   *
   * @param offers The list of offers which have been made to the auction.
   */
  public void setOffers(List<Offer> offers) {
    this.offers = offers;
  }
}
