package com.eafit.herzon.teis.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
   * The user who owns this wishlist. Each cart is associated with a single user.
   */
  @OneToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private CustomUser user;

  /**
   * The timestamp when the wishlist was created.
   * This value is automatically set when the entity is persisted.
   */
  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  /**
   * The timestamp when the wishlist was last updated.
   * This value is automatically updated whenever the entity changes.
   */
  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

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

  /**
   * Gets the creation timestamp of the wishlist.
   *
   * @return the creation timestamp
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Gets the last update timestamp of the wishlist.
   *
   * @return the last update timestamp
   */
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Sets the user associated with the wishlist.
   *
   * @param user the user to associate
   */
  public void setUser(CustomUser user) {
    this.user = user;
  }

  /**
   * Gets the user associated with the wishlist.
   *
   * @return the user of the wishlist
   */
  public CustomUser getUser() {
    return user;
  }
}
