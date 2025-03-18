package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.models.CartItem;
import com.eafit.herzon.teis.repositories.CartItemRepository;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing cart item-related operations.
 */
@Service
public class CartItemService {

  /**
   * Repository for managing cart item data.
   */
  private final CartItemRepository cartItemRepository;

  /**
   * Constructs a new CartItemService with the specified repository.
   *
   * @param cartItemRepository the repository used for cart item operations
   */
  public CartItemService(CartItemRepository cartItemRepository) {
    this.cartItemRepository = cartItemRepository;
  }

  /**
   * Finds a cart item by its ID.
   *
   * @param id the ID of the cart item to find
   * @return an Optional containing the cart item if found, otherwise empty
   */
  @Transactional(readOnly = true)
  public Optional<CartItem> findById(Long id) {
    return cartItemRepository.findById(id);
  }
}