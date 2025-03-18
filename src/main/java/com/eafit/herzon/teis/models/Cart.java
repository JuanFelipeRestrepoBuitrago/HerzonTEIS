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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;


/**
* comment.
*/
@Entity
@Table(name = "carts")
public class Cart {
  /**
  * The ID of the cart.
  */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
  * Comment.
  */
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart",
        cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  @Fetch(FetchMode.SUBSELECT)
  private List<CartItem> items;

  /**
  * The date and time when the auction was created.
  */
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  /**
  * The date and time when the auction was last updated.
  * It is automatically updated by Hibernate when the entity is modified.
  */
  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @OneToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  /**
  * Comment.
  */
  public Cart() {
    this.items = new ArrayList<>();
  }

  /**
  * Comment.
  */
  public List<CartItem> getItems() {
    return this.items;
  }

  /**
  * Comment.
  */
  public void addItem(CartItem item) {
    this.items.add(item);
  }

  /**
  * Comment.
  */
  public void removeItem(CartItem item) {
    this.items.remove(this.items.indexOf(item));
  }

  /**
  * Comment.
  */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
  * Comment.
  */
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
  * Comment.
  */
  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  /**
  * Comment.
  */
  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
  * Comment.
  */
  public User getUser() {
    return user;
  }

  /**
  * Comment.
  */
  public void setUser(User user) {
    this.user = user;
  }
}
