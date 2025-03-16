package com.eafit.herzon.teis.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

/**
 * Represents a credit card entity in the system.
 */
@Entity
@Table(name = "credit_cards")
public class CreditCard {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "card_number", nullable = false, unique = true)
  private String cardNumber;

  @Column(nullable = false)
  private String name;

  @Column(name = "expiration_date", nullable = false)
  private String expirationDate;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at", nullable = false, updatable = false)
  private Date createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "modified_at")
  private Date modifiedAt;

  /**
   * Default constructor.
   */
  public CreditCard() {
  }

  /**
   * Constructor with required parameters.
   *
   * @param cardNumber the credit card number
   * @param name the cardholder's name
   * @param expirationDate the expiration date of the card
   * @param user the user who owns this credit card
   */
  public CreditCard(String cardNumber, String name, String expirationDate, User user) {
    this.cardNumber = cardNumber;
    this.name = name;
    this.expirationDate = expirationDate;
    this.user = user;
    this.createdAt = new Date();
    this.modifiedAt = new Date();
  }

  /**
   * Gets the ID of the credit card.
   *
   * @return the ID
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the credit card.
   *
   * @param id the ID to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the credit card number.
   *
   * @return the card number
   */
  public String getCardNumber() {
    return cardNumber;
  }

  /**
   * Sets the credit card number.
   *
   * @param cardNumber the card number to set
   */
  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  /**
   * Gets the cardholder's name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the cardholder's name.
   *
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the expiration date of the credit card.
   *
   * @return the expiration date
   */
  public String getExpirationDate() {
    return expirationDate;
  }

  /**
   * Sets the expiration date of the credit card.
   *
   * @param expirationDate the expiration date to set
   */
  public void setExpirationDate(String expirationDate) {
    this.expirationDate = expirationDate;
  }

  /**
   * Gets the user who owns this credit card.
   *
   * @return the user
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the user who owns this credit card.
   *
   * @param user the user to set
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Gets the creation date of the credit card.
   *
   * @return the creation date
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Gets the last modification date of the credit card.
   *
   * @return the modification date
   */
  public Date getModifiedAt() {
    return modifiedAt;
  }

  /**
   * Sets the last modification date of the credit card.
   *
   * @param modifiedAt the modification date to set
   */
  public void setModifiedAt(Date modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  /**
   * Sets the creation and initial modification dates before persisting the entity.
   */
  @PrePersist
  protected void onCreate() {
    this.createdAt = new Date();
    this.modifiedAt = new Date();
  }

  /**
   * Updates the modification date before updating the entity.
   */
  @PreUpdate
  protected void onUpdate() {
    this.modifiedAt = new Date();
  }
}