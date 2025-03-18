package com.eafit.herzon.teis.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a wishlist entity.
 * This class is mapped to the "cartItems" table in the database.
 */
@Entity
@Table(name = "cartItems")
public class WishList {

  /**
   * The unique identifier for the wishlist.
   */
  @Id
  private Long id;

  /**
   * Sets the ID of the wishlist.
   *
   * @param id the ID to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the ID of the wishlist.
   *
   * @return the ID of the wishlist
   */
  public Long getId() {
    return id;
  }
}