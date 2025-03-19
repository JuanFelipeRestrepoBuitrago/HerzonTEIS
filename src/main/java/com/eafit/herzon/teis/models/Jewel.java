package com.eafit.herzon.teis.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

  @Column(nullable = false)
  private String imageUrl;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at", nullable = false, updatable = false)
  private Date createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "modified_at")
  private Date modifiedAt;

  /**
   * The list of auctions which have been made to the jewel.
   */
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "jewel", 
      cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  @Fetch(FetchMode.SUBSELECT)
  private List<Auction> auctions;

  /**
   * Default constructor required by JPA.
   */
  public Jewel() {
    this.auctions = new ArrayList<>();
  }

  /**
   * Constructor with required fields.
   *
   * @param name the name of the jewel
   * @param category the category of the jewel
   * @param details additional details about the jewel
   * @param price the price of the jewel
   * @param imageUrl the URL of the jewel's image
   */
  public Jewel(String name, String category, String details, double price, String imageUrl) {
    this.name = name;
    this.category = category;
    this.details = details;
    this.price = price;
    this.imageUrl = imageUrl;
    this.auctions = new ArrayList<>();
  }

  /**
   * Sets the creation and modification timestamps before persisting the entity.
   */
  @PrePersist
  protected void onCreate() {
    this.createdAt = new Date();
    this.modifiedAt = new Date();
  }

  /**
   * Updates the modification timestamp before updating the entity.
   */
  @PreUpdate
  protected void onUpdate() {
    this.modifiedAt = new Date();
  }

  /**
   * Gets the ID of the jewel.
   *
   * @return the jewel's ID
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the jewel.
   *
   * @param id the jewel's ID
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the name of the jewel.
   *
   * @return the jewel's name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the jewel.
   *
   * @param name the jewel's name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the category of the jewel.
   *
   * @return the jewel's category
   */
  public String getCategory() {
    return category;
  }

  /**
   * Sets the category of the jewel.
   *
   * @param category the jewel's category
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * Gets the details of the jewel.
   *
   * @return the jewel's details
   */
  public String getDetails() {
    return details;
  }

  /**
   * Sets the details of the jewel.
   *
   * @param details the jewel's details
   */
  public void setDetails(String details) {
    this.details = details;
  }

  /**
   * Gets the price of the jewel.
   *
   * @return the jewel's price
   */
  public double getPrice() {
    return price;
  }

  /**
   * Sets the price of the jewel.
   *
   * @param price the jewel's price
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * Gets the image URL of the jewel.
   *
   * @return the jewel's image URL
   */
  public String getImageUrl() {
    return imageUrl;
  }

  /**
   * Sets the image URL of the jewel.
   *
   * @param imageUrl the jewel's image URL
   */
  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  /**
   * Gets the creation date of the jewel.
   *
   * @return the jewel's creation date
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Gets the last modification date of the jewel.
   *
   * @return the jewel's last modification date
   */
  public Date getModifiedAt() {
    return modifiedAt;
  }

  /**
   * Gets the list of auctions associated with the jewel.
   *
   * @return The list of auctions.
   */
  public List<Auction> getAuctions() {
    return auctions;
  }

  /**
   * Sets the list of auctions associated with the jewel.
   *
   * @param auctions The list of auctions.
   */
  public void setAuctions(List<Auction> auctions) {
    this.auctions = auctions;
  }
}