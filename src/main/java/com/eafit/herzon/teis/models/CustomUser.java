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

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Cart cart;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "modified_at")
  private Date modifiedAt;

  /**
   * The list of offers which have been made by the user.
   */
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user",
          cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  @Fetch(FetchMode.SUBSELECT)
  private List<Offer> offers;

  /**
   * The list of orders which owned by the user.
   */
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", 
      cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  @Fetch(FetchMode.SUBSELECT)
  private List<Order> orders;

  /**
   * Default constructor.
   */
  public CustomUser() {
    this.creditCards = new ArrayList<>();
    this.offers = new ArrayList<>();
    this.orders = new ArrayList<>();
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
  }

  /**
   * Gets the ID of the user.
   *
   * @return the ID
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the user.
   *
   * @param id the ID to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the username of the user.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username of the user.
   *
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets the name of the user.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the user.
   *
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the email of the user.
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email of the user.
   *
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the address of the user.
   *
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * Sets the address of the user.
   *
   * @param address the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Gets the password of the user.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password of the user.
   *
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Gets the role of the user.
   *
   * @return the role
   */
  public Role getRole() {
    return role;
  }

  /**
   * Sets the role of the user.
   *
   * @param role the role to set
   */
  public void setRole(Role role) {
    this.role = role;
  }

  /**
   * Gets the list of credit cards associated with the user.
   *
   * @return the list of credit cards
   */
  public List<CreditCard> getCreditCards() {
    return creditCards;
  }

  /**
   * Sets the list of credit cards associated with the user.
   *
   * @param creditCards the list of credit cards to set
   */
  public void setCreditCards(List<CreditCard> creditCards) {
    this.creditCards = creditCards;
  }

  /**
   * Gets the creation date of the user.
   *
   * @return the creation date
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Gets the last modification date of the user.
   *
   * @return the modification date
   */
  public Date getModifiedAt() {
    return modifiedAt;
  }

  /**
   * Sets the last modification date of the user.
   *
   * @param modifiedAt the modification date to set
   */
  public void setModifiedAt(Date modifiedAt) {
    this.modifiedAt = modifiedAt;
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

  /**
   * Gets the list of offers made by the user.
   *
   * @return the list of offers
   */
  public List<Offer> getOffers() {
    return offers;
  }

  /**
   * Sets the list of offers made by the user.
   *
   * @param offers the list of offers to set
   */
  public void setOffers(List<Offer> offers) {
    this.offers = offers;
  }

  /**
   * Gets the list of orders owned by the user.
   *
   * @return the list of orders
   */
  public List<Order> getOrders() {
    return orders;
  }

  /**
   * Sets the list of orders owned by the user.
   *
   * @param orders the list of orders to set
   */
  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  /**
   * Gets the user's associated cart.
   *
   * @return the cart
   */
  public Cart getCart() {
    return cart;
  }

  /**
   * Sets the user's associated cart.
   *
   * @param cart the cart to set
   */
  public void setCart(Cart cart) {
    this.cart = cart;
  }

}