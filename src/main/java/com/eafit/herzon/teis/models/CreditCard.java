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
 * Represents a credit card entity in the system.
 * This class maps to the 'credit_cards' table in the database and is associated with a User entity.
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

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "modified_at", nullable = false)
  private LocalDateTime modifiedAt;

  /**
   * Default constructor for the CreditCard entity.
   * Required by JPA for entity management.
   */
  public CreditCard() {
  }

  /**
   * Constructor with required parameters to create a new CreditCard instance.
   *
   * @param cardNumber the credit card number (must be unique and non-null)
   * @param name the cardholder's name (must be non-null)
   * @param expirationDate the expiration date of the card (must be non-null)
   * @param user the user who owns this credit card (must be non-null)
   */
  public CreditCard(String cardNumber, String name, String expirationDate, User user) {
    this.cardNumber = cardNumber;
    this.name = name;
    this.expirationDate = expirationDate;
    this.user = user;
  }

  /**
   * Returns the unique identifier of the credit card.
   *
   * @return the ID of the credit card
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the unique identifier of the credit card.
   *
   * @param id the ID to set for the credit card
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Returns the credit card number.
   *
   * @return the card number
   */
  public String getCardNumber() {
    return cardNumber;
  }

  /**
   * Sets the credit card number.
   *
   * @param cardNumber the card number to set (must be unique and non-null)
   */
  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  /**
   * Returns the cardholder's name.
   *
   * @return the name of the cardholder
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the cardholder's name.
   *
   * @param name the name to set for the cardholder (must be non-null)
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the expiration date of the credit card.
   *
   * @return the expiration date
   */
  public String getExpirationDate() {
    return expirationDate;
  }

  /**
   * Sets the expiration date of the credit card.
   *
   * @param expirationDate the expiration date to set (must be non-null)
   */
  public void setExpirationDate(String expirationDate) {
    this.expirationDate = expirationDate;
  }

  /**
   * Returns the user associated with this credit card.
   *
   * @return the user who owns the credit card
   */
  public User getUser() {
    return user;
  }

  /**
   * Sets the user associated with this credit card.
   *
   * @param user the user to set as the owner (must be non-null)
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Returns the creation timestamp of the credit card.
   *
   * @return the date and time when the credit card was created
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Returns the last modification timestamp of the credit card.
   *
   * @return the date and time when the credit card was last modified
   */
  public LocalDateTime getModifiedAt() {
    return modifiedAt;
  }
}