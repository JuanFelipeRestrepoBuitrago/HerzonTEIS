package com.eafit.herzon.teis.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  /**
   * The date and time when the auction was last updated. It is automatically
   * updated by Hibernate when the entity is modified.
   */
  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

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

  // /**
  //  * The Jewel associated with the auction.
  //  */
  // @ManyToOne(fetch = FetchType.EAGER, optional = false)
  // @JoinColumn(name = "jewel_id", nullable = false)
  // private Jewel jewel;

  /**
   * The list of offers which have been made to the auction.
   */
  @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private ArrayList<Offer> offers;

  /**
   * Constructor for the Auction class.
   */
  public Auction() {
  }

  /**
   * Constructor for the Auction class.

   * @param startDate The date and time when the auction starts.
   * @param endDate The date and time when the auction ends.
   * @param startPrice The minimum or start price of the auction.
   * @param currentPrice The current price of the auction.
   */
  public Auction(
      LocalDateTime startDate, LocalDateTime endDate, 
      Double startPrice, Double currentPrice //, Jewel
  ) { 
    this.startDate = startDate;
    this.endDate = endDate;
    this.startPrice = startPrice;
    this.currentPrice = currentPrice;
    // this.jewel = jewel;
    // this.offers = new ArrayList<>();
  }

  /**
   * Returns the ID of the auction.

   * @return The ID of the auction.
   */
  public Long getId() {
    return id;
  }

  /**
   * Returns the date and time when the auction was created.

   * @return The date and time when the auction was created.
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Returns the date and time when the auction was last updated.

   * @return The date and time when the auction was last updated.
   */
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Returns the date and time when the auction starts.

   * @return The date and time when the auction starts.
   */
  public LocalDateTime getStartDate() {
    return startDate;
  }

  /**
   * Returns the date and time when the auction ends.

   * @return The date and time when the auction ends.
   */
  public LocalDateTime getEndDate() {
    return endDate;
  }

  /**
   * Returns the minimum or start price of the auction.

   * @return The minimum or start price of the auction.
   */
  public Double getStartPrice() {
    return startPrice;
  }

  /**
   * Returns the current price of the auction.

   * @return The current price of the auction.
   */
  public Double getCurrentPrice() {
    return currentPrice;
  }

  // /**
  //  * Returns the Jewel associated with the auction.
  
  //  * @return The Jewel associated with the auction.
  //  */
  // public Jewel getJewel() {
  //   return jewel;
  // }

  /**
   * Returns the list of offers which have been made to the auction.

   * @return The list of offers which have been made to the auction.
   */
  public ArrayList<Offer> getOffers() {
    return offers;
  }

  /**
   * Sets the date and time when the auction starts.

   * @param startDate The date and time when the auction starts.
   */
  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  /**
   * Sets the date and time when the auction ends.

   * @param endDate The date and time when the auction ends.
   */
  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  /**
   * Sets the minimum or start price of the auction.

   * @param startPrice The minimum or start price of the auction.
   */
  public void setStartPrice(Double startPrice) {
    this.startPrice = startPrice;
  }

  /**
   * Sets the current price of the auction.

   * @param currentPrice The current price of the auction.
   */
  public void setCurrentPrice(Double currentPrice) {
    this.currentPrice = currentPrice;
  }

  // /**
  //  * Sets the Jewel associated with the auction.

  //  * @param jewel The Jewel associated with the auction.
  //  */
  // public void setJewel(Jewel jewel) {
  //   this.jewel = jewel;
  // }

  /**
   * Sets the list of offers which have been made to the auction.

   * @param offers The list of offers which have been made to the auction.
   */
  public void setOffers(ArrayList<Offer> offers) {
    this.offers = offers;
  }
}
