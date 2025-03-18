package com.eafit.herzon.teis.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
 * Represents an order made by a user for an auction or a purchase.
 */
@Entity
@Table(name = "orders")
public class Order {
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

  // /**
  // * The list of cart items in the order.
  // */
  // @OneToMany(fetch = FetchType.EAGER, mappedBy = "cart_item",
  // cascade = CascadeType.ALL, orphanRemoval = true)
  // @JsonIgnore
  // @Fetch(FetchMode.SUBSELECT)
  // private List<CartItem> cartItems;

  /**
   * The user who owns the order.
   */
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private CustomUser user;

  /**
   * The price offered by the user.
   */
  @Column(name = "total", nullable = false)
  private Double total;

  /**
   * The statuses of the order.
   */
  public enum OrderStatus {
    PENDING,
    PAID,
    CANCELED
  }

  /**
   * The status of the order.
   */
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "ENUM('PENDING', 'PAID', 'CANCELED')")
  private OrderStatus status;

  /**
   * Default constructor required by JPA.
   */
  public Order() {
  }

  /**
   * Creates a new order with the specified total price.
   * The state of the order is set to true by default.
   *
   * @param total the total price of the order.
   */
  public Order(Double total) {
    this.total = total;
    this.status = OrderStatus.PENDING;
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

  // /**
  // * Returns the list of cart items in the order.

  // * @return the list of cart items in the order.
  // */
  // public List<CartItem> getCartItems() {
  // return cartItems;
  // }

  /**
   * Returns the user who owns the order.
   *
   * @return the user who owns the order.
   */
  public CustomUser getUser() {
    return user;
  }

  /**
   * Returns the total price of the order.
   *
   * @return the total price of the order.
   */
  public Double getTotal() {
    return total;
  }

  /**
   * Returns the status of the order.
   *
   * @return the status of the order.
   */
  public OrderStatus getStatus() {
    return status;
  }

  /**
   * Sets the status of the order.
   *
   * @param status the status of the order.
   */
  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  /**
   * Sets the user who owns the order.
   *
   * @param user the user who owns the order.
   */
  public void setUser(CustomUser user) {
    this.user = user;
  }

  // /**
  // * Sets the list of cart items in the order.

  // * @param cartItems the list of cart items in the order.
  // */
  // public void setCartItems(List<CartItem> cartItems) {
  // this.cartItems = cartItems;
  // }

  /**
   * Sets the total price of the order.
   *
   * @param total the total price of the order.
   */
  public void setTotal(Double total) {
    this.total = total;
  }
}
