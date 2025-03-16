package com.eafit.herzon.teis.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

/**
 * Represents a jewelry item in the system.
 */
@Entity
@Table(name = "jewels")
public class Jewel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String category;

  @Column(nullable = false, length = 1000)
  private String details;

  @Column(nullable = false)
  private double price;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at", nullable = false, updatable = false)
  private Date createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "modified_at")
  private Date modifiedAt;

  /**
   * Default constructor required by JPA.
   */
  public Jewel() {
  }

  /**
   * Constructor with required fields.
   *
   * @param name     The name of the jewel.
   * @param category The category of the jewel.
   * @param details  Additional details about the jewel.
   * @param price    The price of the jewel.
   */
  public Jewel(String name, String category, String details, double price) {
    this.name = name;
    this.category = category;
    this.details = details;
    this.price = price;
  }

  /**
   * Sets the creation and initial modification dates before persisting.
   */
  @PrePersist
  protected void onCreate() {
    this.createdAt = new Date();
    this.modifiedAt = new Date();
  }

  /**
   * Updates the modification date before updating.
   */
  @PreUpdate
  protected void onUpdate() {
    this.modifiedAt = new Date();
  }

  /**
   * Gets the ID of the jewel.
   *
   * @return The jewel's ID.
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the jewel.
   *
   * @param id The jewel's ID.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the name of the jewel.
   *
   * @return The jewel's name.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the jewel.
   *
   * @param name The jewel's name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the category of the jewel.
   *
   * @return The jewel's category.
   */
  public String getCategory() {
    return category;
  }

  /**
   * Sets the category of the jewel.
   *
   * @param category The jewel's category.
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * Gets the details of the jewel.
   *
   * @return The jewel's details.
   */
  public String getDetails() {
    return details;
  }

  /**
   * Sets the details of the jewel.
   *
   * @param details The jewel's details.
   */
  public void setDetails(String details) {
    this.details = details;
  }

  /**
   * Gets the price of the jewel.
   *
   * @return The jewel's price.
   */
  public double getPrice() {
    return price;
  }

  /**
   * Sets the price of the jewel.
   *
   * @param price The jewel's price.
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * Gets the creation date of the jewel.
   *
   * @return The jewel's creation date.
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Gets the last modification date of the jewel.
   *
   * @return The jewel's last modification date.
   */
  public Date getModifiedAt() {
    return modifiedAt;
  }
}