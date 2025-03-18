package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.models.Cart;
import com.eafit.herzon.teis.models.CartItem;
import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.models.Jewel;
import com.eafit.herzon.teis.repositories.CartItemRepository;
import com.eafit.herzon.teis.repositories.CartRepository;
import com.eafit.herzon.teis.repositories.JewelRepository;
import com.eafit.herzon.teis.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
* Service class for managing cart-related operations.
*/
@Service
public class CartService {

  private final CartRepository cartRepository;
  private final UserRepository userRepository;
  private final JewelRepository jewelRepository;
  private final CartItemRepository cartItemRepository;

  /**
   * Constructs a new CartService with the specified repositories.
   *
   * @param cartRepository     the repository for managing cart data
   * @param userRepository     the repository for managing user data
   * @param jewelRepository    the repository for managing jewel data
   * @param cartItemRepository the repository for managing cart item data
   */
  public CartService(CartRepository cartRepository, UserRepository userRepository,
                     JewelRepository jewelRepository, CartItemRepository cartItemRepository) {
    this.cartRepository = cartRepository;
    this.userRepository = userRepository;
    this.jewelRepository = jewelRepository;
    this.cartItemRepository = cartItemRepository;
  }

  /**
   * Adds an item to the user's cart.
   *
   * @param userId   the ID of the user
   * @param jewelId  the ID of the jewel to add
   * @param id       the ID of the cart (if applicable)
   * @param quantity the quantity of the item to add
   * @throws RuntimeException if the user or jewel is not found
   */
  @Transactional
  public void addItem(long userId, Long jewelId, long id, int quantity) {
    CustomUser customUser = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    Jewel jewel = jewelRepository.findById(jewelId)
            .orElseThrow(() -> new RuntimeException("Joya no encontrada"));

    Cart cart = customUser.getCart();
    CartItem cartItem = new CartItem(jewel, quantity);
    if (cart == null) {
      cart = new Cart();
      cart.setUser(customUser);
    }

    cart.addItem(cartItem);
    cartItemRepository.save(cartItem);
  }

  /**
   * Removes an item from the user's cart.
   *
   * @param userId     the ID of the user
   * @param cartItemId the ID of the cart item to remove
   * @throws RuntimeException if the user or cart item is not found
   */
  public void removeItem(long userId, long cartItemId) {
    CustomUser customUser = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    CartItem item = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new RuntimeException("Joya no encontrada"));

    Cart cart = customUser.getCart();
    cart.removeItem(item);
  }

  /**
   * Empties the user's cart by removing all items.
   *
   * @param userId the ID of the user
   * @throws RuntimeException if the user is not found
   */
  @Transactional
  public void emptyCart(long userId) {
    CustomUser customUser = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    Cart cart = customUser.getCart();

    cart.getItems().clear();
  }

  /**
   * Retrieves all items in the user's cart with pagination.
   *
   * @param userId the ID of the user
   * @param page   the page number
   * @param size   the number of items per page
   * @return a page of cart items
   * @throws RuntimeException if the user is not found
   */
  @Transactional
  public Page<CartItem> getAllitems(Long userId, int page, int size) {
    CustomUser customUser = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    Pageable pageable = PageRequest.of(page, size);
    return cartItemRepository.findByUserId(userId, pageable);
  }
}