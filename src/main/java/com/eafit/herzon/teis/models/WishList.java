package com.eafit.herzon.teis.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Represents a wishlist entity.
 * This class is mapped to the "wishlist" table in the database.
 */
@Entity
@Table(name = "wishlist")
public class WishList {

  /**
   * The unique identifier for the wishlist.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The user who owns this wishlist. Each wishlist is associated with a single user.
   */
  @OneToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private CustomUser user;

  /**
   * The list of jewels added to the wishlist.
   */
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "wishlist_id")
  private List<Jewel> jewels = new ArrayList<>();

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
   * Gets the ID of the wishlist.
   *
   * @return the ID of the wishlist
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the wishlist.
   *
   * @param id the ID to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the user associated with the wishlist.
   *
   * @return the user of the wishlist
   */
  public CustomUser getUser() {
    return user;
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
   * Gets the list of jewels in the wishlist.
   *
   * @return the list of jewels
   */
  public List<Jewel> getJewels() {
    return jewels;
  }

  /**
   * Adds a jewel to the wishlist.
   *
   * @param jewel the jewel to add
   */
  public void addJewel(Jewel jewel) {
    jewels.add(jewel);
  }

  /**
   * Removes a jewel from the wishlist.
   *
   * @param jewel the jewel to remove
   * @return true if the jewel was removed, false otherwise
   */
  public boolean removeJewel(Jewel jewel) {
    return jewels.remove(jewel);
  }
}

