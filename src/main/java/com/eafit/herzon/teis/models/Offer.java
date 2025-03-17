package com.eafit.herzon.teis.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Represents an offer made by a user for an auction.
 * This class stores details such as the creation date, auction name, offer
 * price, and state.
 */
@Entity
@Table(name = "offers")
public class Offer {
  /**
   * The ID of the offer.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The date and time when the offer was created.
   */
  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  /**
   * The date and time when the offer was last updated. It is automatically
   * updated by Hibernate when the entity is modified.
   */
  @UpdateTimestamp
  @Column(name = "modified_at", nullable = false)
  private LocalDateTime modifiedAt;

  /**
   * The auction for which the offer was made.
   */
  @ManyToOne(optional = false)
  @JoinColumn(name = "auction_id", nullable = false)
  private Auction auction;

  // /**
  //  * The user who made the offer.
  //  */
  // @ManyToOne(fetch = FetchType.EAGER, optional = false)
  // @JoinColumn(name = "user_id", nullable = false)
  // private User user;

  /**
   * The price offered by the user.
   */
  @Column(name = "offer_price", nullable = false)
  private Double offerPrice;

  /**
   * The state of the offer. True if the offer is active, false otherwise.
   */
  @Column(name = "state", nullable = false, columnDefinition = "boolean default true")
  private boolean state;

  /**
   * Default constructor required by JPA.
   */
  public Offer() {}

  /**
   * Creates a new offer with the specified offer price, state, and
   * auction.
   *
   * @param offerPrice the price offered by the user.
   * @param state the state of the offer.
   * @param auction the auction for which the offer was made.
   */
  public Offer(double offerPrice, boolean state, Auction auction) {
    this.auction = auction;
    this.offerPrice = offerPrice;
    this.state = state;
  }

  /**
   * Creates a new offer with the specified offer price, and
   * auction. The state of the offer is set to true by default.
   *
   * @param offerPrice the price offered by the user.
   * @param auction the auction for which the offer was made.
   */
  public Offer(double offerPrice, Auction auction) {
    this.auction = auction;
    this.offerPrice = offerPrice;
    this.state = true;
  }

  /**
   * Returns the ID of the offer.
   *
   * @return the ID of the offer.
   */
  public Long getId() {
    return id;
  }

  /**
   * Returns the date and time when the offer was created.
   *
   * @return the date and time when the offer was created.
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Returns the date and time when the offer was last updated.
   *
   * @return the date and time when the offer was last updated.
   */
  public LocalDateTime getModifiedAt() {
    return modifiedAt;
  }

  /**
   * Returns the auction for which the offer was made.
   *
   * @return the auction for which the offer was made.
   */
  public Auction getAuction() {
    return auction;
  }

  // /**
  //  * Returns the user who made the offer.

  //  * @return the user who made the offer.
  //  */
  // public User getUser() {
  //   return user;
  // }

  /**
   * Returns the price offered by the user.
   *
   * @return the price offered by the user.
   */
  public Double getOfferPrice() {
    return offerPrice;
  }

  /**
   * Returns the state of the offer.
   *
   * @return the state of the offer.
   */
  public boolean getState() {
    return state;
  }

  /**
   * Sets the state of the offer.
   *
   * @param state the state of the offer.
   */
  public void setState(boolean state) {
    this.state = state;
  }

  /**
   * Sets the price offered by the user.
   *
   * @param offerPrice the price offered by the user.
   */
  public void setOfferPrice(Double offerPrice) {
    this.offerPrice = offerPrice;
  }

  // /**
  //  * Sets the user who made the offer.

  //  * @param user the user who made the offer.
  //  */
  // public void setUser(User user) {
  //   this.user = user;
  // }

  /**
   * Sets the auction for which the offer was made.
   *
   * @param auction the auction for which the offer was made.
   */
  public void setAuction(Auction auction) {
    this.auction = auction;
  }
}
