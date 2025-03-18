package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Controller for handling cart-related API requests.
 * This class provides endpoints for managing cart operations such
 * as adding, removing, and retrieving cart items.
 */
@Controller
public class CartApiController {

  private final CartService cartService;

  /**
  * Constructs a new CartApiController with the required service.
  *
  * @param cartService the service for jewel operations
  */
  @Autowired
  public CartApiController(CartService cartService) {
    this.cartService = cartService;
  }

  /**
  * Adds a new item to the user's cart.
  *
  * @param userId   the ID of the user who owns the cart
  * @param jewelId  the ID of the jewel to add to the cart
  * @param id       the ID of the cart item (if applicable)
  * @param quantity the quantity of the item to add
  * @return a response entity with a success message
  */
  @PostMapping
  public ResponseEntity<String> addCartItem(@RequestBody long userId,
                                            Long jewelId, long id, int quantity) {
    cartService.addItem(userId, jewelId, id, quantity);
    return ResponseEntity.ok("OK");
  }

  /**
  * Removes an item from the user's cart.
  *
  * @param userId      the ID of the user who owns the cart
  * @param cartItemId  the ID of the cart item to remove
  */
  @PostMapping("delete/{id}")
  public void  removeCartitem(@RequestBody Long userId, long cartItemId) {
    cartService.removeItem(userId, cartItemId);
  }

  /**
  * Empties the user's cart by removing all items.
  *
  * @param userId the ID of the user who owns the cart
  * @return a response entity with a success message
  */
  @PostMapping("empty/{id}")
  public ResponseEntity<String> emptyCart(@RequestBody long userId) {

    cartService.emptyCart(userId);
    return ResponseEntity.ok("OK");
  }

}
