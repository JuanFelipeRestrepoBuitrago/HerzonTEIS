package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.services.CartService;
import com.eafit.herzon.teis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Controller for handling cart-related API requests.
 * This class provides endpoints for managing cart operations such
 * as adding, removing, and retrieving cart items.
 */
@Controller
public class CartApiController {

  private final CartService cartService;
  private final UserService userService;

  /**
  * Constructs a new CartApiController with the required service.
  *
  * @param cartService the service for jewel operations
  */
  @Autowired
  public CartApiController(CartService cartService,  UserService userService) {
    this.cartService = cartService;
    this.userService = userService;
  }

  /**
  * Adds a new item to the user's cart.
  *
  * @param jewelId  the ID of the jewel to add to the cart
  * @param quantity the quantity of the item to add
  * @return a response entity with a success message
  */
  @PostMapping("/cart/add/{jewelId}")
  public String addCartItem(@PathVariable Long jewelId,
                                            @RequestParam(defaultValue = "1") int quantity,
                                            Model model) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    CustomUser user = userService.getUserByUsername(username);

    cartService.addItem(user.getId(), jewelId, quantity);
    return "redirect:/cart";
  }

  /**
  * Removes an item from the user's cart.
  *
  * @param id  the ID of the cart item to remove
  */
  @PostMapping("cart/delete/{id}")
  public String removeCartItem(@PathVariable long id) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    CustomUser user = userService.getUserByUsername(username);
    cartService.removeItem(user.getId(), id);

    return "redirect:/cart";
  }

  /**
  * Empties the user's cart by removing all items.
  *
  * @return a response entity with a success message
  */
  @PostMapping("/cart/empty")
  public String emptyCart() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    CustomUser user = userService.getUserByUsername(username);
    cartService.emptyCart(user.getId());
    return "redirect:/cart";
  }

  /**
   * Processes the checkout of the user's cart.
   *
   * @return a redirection to the orders page after a successful checkout.
   */
  @PostMapping("/cart/checkout")
  public String checkoutCart() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    CustomUser user = userService.getUserByUsername(username);
    cartService.checkout(user.getId());
    return "redirect:/orders";
  }

}
