package com.eafit.herzon.teis.models;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import java.util.Date;
import java.util.List;

/**
 * Represents a user entity in the system.
 */

@Entity
@Table(name = "users")
public class User {

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

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "modified_at")
  private Date modifiedAt;


  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "cart_id", nullable = false)
  private Cart cart;

  /**
   * Default constructor.
   */
  public User() {
  }

  /**
   * Constructor with required parameters.
   *
   * @param username the user's unique username
   * @param email the user's email
   * @param password the user's password
   */
  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = Role.USER; // Default role
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


  public Cart getCart() {
    return cart;
  }

  public void setCart(Cart cart) {
    this.cart = cart;
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