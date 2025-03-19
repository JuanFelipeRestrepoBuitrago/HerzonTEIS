package com.eafit.herzon.teis.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
 * Represents a user entity in the system.
 */
@Entity
@Table(name = "users")
public class CustomUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CreditCard> creditCards;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at", nullable = false, updatable = false)
  private Date createdAt;

  @OneToOne(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
  private Cart cart;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "modified_at")
  private Date modifiedAt;

  /**
   * The list of offers made by the user.
   */
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user",
          cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  @Fetch(FetchMode.SUBSELECT)
  private List<Offer> offers;

  /**
   * The list of orders owned by the user.
   */
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user",
          cascade = CascadeType.REMOVE, orphanRemoval = true)
  @JsonIgnore
  @Fetch(FetchMode.SUBSELECT)
  private List<Order> orders;

  /**
   * The wishlist associated with the user.
   */
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private WishList wishList;

  /**
   * Default constructor.
   */
  public CustomUser() {
    this.creditCards = new ArrayList<>();
    this.offers = new ArrayList<>();
    this.orders = new ArrayList<>();
    this.cart = new Cart(this);
    // Puedes crear la wishlist aquí o dejarla nula hasta que se necesite.
    this.wishList = new WishList();
    this.wishList.setUser(this);
  }

  /**
   * Constructor with required parameters.
   *
   * @param username the user's unique username
   * @param email the user's email
   * @param password the user's password
   */
  public CustomUser(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = Role.USER; // Default role
    this.creditCards = new ArrayList<>();
    this.offers = new ArrayList<>();
    this.orders = new ArrayList<>();
    this.cart = new Cart(this);
    this.wishList = new WishList();
    this.wishList.setUser(this);
  }

  // Getters y setters de los atributos existentes ...

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public List<CreditCard> getCreditCards() {
    return creditCards;
  }

  public void setCreditCards(List<CreditCard> creditCards) {
    this.creditCards = creditCards;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public Date getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(Date modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  public List<Offer> getOffers() {
    return offers;
  }

  public void setOffers(List<Offer> offers) {
    this.offers = offers;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  /**
   * Gets the user's associated cart.
   *
   * @return the cart
   */
  public Cart getCart() {
    if (cart == null) {
      cart = new Cart(this);
      setCart(cart);
    }
    return cart;
  }

  /**
   * Creates the user's associated cart.
   */
  public void createCart() {
    this.cart = new Cart(this);
  }

  /**
   * Sets the user's associated cart.
   *
   * @param cart the cart to set
   */
  public void setCart(Cart cart) {
    this.cart = cart;
  }

  /**
   * Gets the user's associated wishlist.
   *
   * @return the wishlist
   */
  public WishList getWishList() {
    return wishList;
  }

  /**
   * Sets the user's associated wishlist.
   *
   * @param wishList the wishlist to set
   */
  public void setWishList(WishList wishList) {
    this.wishList = wishList;
  }

  /**
   * Sets the creation and initial modification dates before persisting the entity.
   */
  @PrePersist
  protected void onCreate() {
    this.createdAt = new Date();
    this.modifiedAt = new Date();
  }

  /**
   * Updates the modification date before updating the entity.
   */
  @PreUpdate
  protected void onUpdate() {
    this.modifiedAt = new Date();
  }

  /**
   * Enum for defining user roles.
   */
  public enum Role {
    ADMIN, USER
  }
}
