package com.eafit.herzon.teis.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Represents an item in a user's shopping cart.
 */
@Entity
@Table(name = "cartItems")
public class CartItem {

  /**
   * The unique identifier for the cart item.
   */
  public Long getId() {
    return id;
  }

  /**
   * The unique identifier for the cart item.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * The unique identifier for the cart item.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The quantity of the jewel in the cart.
   */
  @Column(name = "quantity", nullable = false)
  private int quantity;

  /**
   * The jewel associated with this cart item.
   */
  @OneToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "jewel_id", nullable = false)
  private Jewel jewel;

  /**
   * The date and time when the cart item was created.
   */
  @ManyToOne
  @JoinColumn(name = "cart_id", nullable = true)
  private Cart cart;

  /**
   * The date and time when the cart item was created.
   */
  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  /**
   * The date and time when the cart item was last updated.
   * Automatically updated by Hibernate when the entity is modified.
   */
  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @ManyToMany(mappedBy = "cartItems")
  private List<Order> orders = new ArrayList<>();

  /**
   * Default constructor for CartItem.
   */
  public CartItem() {
  }

  /**
   * Constructs a new CartItem with the specified jewel and quantity.
   *
   * @param jewel    the jewel to add to the cart
   * @param quantity the quantity of the jewel
   */
  public CartItem(Jewel jewel, int quantity, Cart cart) {
    this.jewel = jewel;
    this.quantity = quantity;
    this.cart = cart;
  }

  /**
   * Gets the quantity of the jewel in the cart.
   *
   * @return the quantity of the jewel
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Sets the quantity of the jewel in the cart.
   *
   * @param quantity the quantity to set
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * Gets the jewel associated with this cart item.
   *
   * @return the jewel
   */
  public Jewel getJewel() {
    return jewel;
  }

  /**
   * Sets the jewel associated with this cart item.
   *
   * @param jewel the jewel to set
   */
  public void setJewel(Jewel jewel) {
    this.jewel = jewel;
  }

  /**
   * Gets the creation date and time of the cart item.
   *
   * @return the creation date and time
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the creation date and time of the cart item.
   *
   * @param createdAt the creation date and time to set
   */
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Gets the last updated date and time of the cart item.
   *
   * @return the last updated date and time
   */
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  /**
   * Gets the last updated date and time of the cart item.
   *
   * @return the last updated date and time
   */
  public Cart getCart() {
    return cart;
  }

  /**
   * Gets the last updated date and time of the cart item.
   *
   * @param cart buy cart
   */
  public void setCart(Cart cart) {
    this.cart = cart;
  }

  /**
   * Sets the last updated date and time of the cart item.
   *
   * @param updatedAt the last updated date and time to set
   */

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * Gets the unique identifier of the cart item.
   *
   * @return the unique identifier
   */
  public List<Order> getOrders() {
    return orders;
  }

  /**
   * Sets the unique identifier of the cart item.
   *
   * @param orders the unique identifier to set
   */
  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }
}