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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Represents a shopping cart associated with a user.
 * Each cart contains multiple cart items and is linked to a specific user.
 */
@Entity
@Table(name = "carts")
public class Cart {

  /**
   * Unique identifier for the cart.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * List of items in the cart. A cart can contain multiple items.
   * Items are lazily fetched to improve performance.
   */
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart",
          cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  @Fetch(FetchMode.SUBSELECT)
  private List<CartItem> items;

  /**
   * Timestamp indicating when the cart was created.
   * This value is automatically set and cannot be updated.
   */
  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  /**
   * Timestamp indicating the last time the cart was updated.
   * Automatically updated whenever the entity is modified.
   */
  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  /**
   * The user who owns this cart. Each cart is associated with a single user.
   */
  @OneToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private CustomUser user;

  /**
   * Default constructor initializes an empty cart with no items.
   */
  public Cart() {
    this.items = new ArrayList<>();
  }

  /**
   * Constructor to create a cart for a specific user.
   *
   * @param user The user who owns this cart.
   */
  public Cart(CustomUser user) {
    this.user = user;
    this.items = new ArrayList<>();
  }

  /**
   * Retrieves the list of items in the cart.
   *
   * @return List of CartItem objects.
   */
  public List<CartItem> getItems() {
    return this.items;
  }

  /**
   * Adds an item to the cart.
   *
   * @param item The CartItem to be added.
   */
  public void addItem(CartItem item) {
    this.items.add(item);
  }

  /**
   * Removes an item from the cart.
   *
   * @param item The CartItem to be removed.
   */
  public void removeItem(CartItem item) {
    this.items.remove(item);
  }

  /**
   * Retrieves the timestamp of when the cart was created.
   *
   * @return LocalDateTime representing the creation timestamp.
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the timestamp of when the cart was created.
   *
   * @param createdAt LocalDateTime value to set.
   */
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Retrieves the timestamp of the last update to the cart.
   *
   * @return LocalDateTime representing the last update timestamp.
   */
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Sets the timestamp of the last update to the cart.
   *
   * @param updatedAt LocalDateTime value to set.
   */
  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * Retrieves the user who owns the cart.
   *
   * @return The CustomUser associated with this cart.
   */
  public CustomUser getUser() {
    return user;
  }

  /**
   * Sets the user who owns the cart.
   *
   * @param customUser The CustomUser to associate with this cart.
   */
  public void setUser(CustomUser customUser) {
    this.user = customUser;
  }
}
