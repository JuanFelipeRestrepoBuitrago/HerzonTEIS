package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.models.CartItem;
import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.services.CartItemService;
import com.eafit.herzon.teis.services.CartService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for handling cart-related views and operations.
 */
@Controller
@RequestMapping()
public class CartViewController {

  private final CartService cartService;
  private final CartItemService cartItemService;

  /**
   * Constructs a new CartViewController with the specified services.
   *
   * @param cartService     the service for managing cart operations
   * @param cartItemService the service for managing cart item operations
   */
  public CartViewController(CartService cartService, CartItemService cartItemService) {
    this.cartService = cartService;
    this.cartItemService = cartItemService;
  }

  /**
   * Displays a paginated list of cart items for a specific user.
   *
   * @param page    the page number to display (default is 0)
   * @param size    the number of items per page (default is 9)
   * @param view    the view mode (default is "grid")
   * @param user the ID of the user whose cart items are being displayed
   * @param model   the model to add attributes for the view
   * @return the view name for displaying the cart items
   */
  @GetMapping("/cart")
  public String listItems(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "9") int size,
                          @RequestParam(defaultValue = "grid") String view,
                          @AuthenticationPrincipal CustomUser user,
                          Model model) {
    Long userId = user.getId();  // 🔹 Obtiene el ID del usuario autenticado
    Page<CartItem> itemsPage = cartService.getAllitems(userId, page, size);

    model.addAttribute("items", itemsPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", itemsPage.getTotalPages());
    model.addAttribute("view", view);
    model.addAttribute("currentPageActive", "items");
    model.addAttribute("currentViewActive", view);
    return "/cart/items";
  }

  /**
   * Displays the details of a specific cart item.
   *
   * @param id    the ID of the cart item to display
   * @param model the model to add attributes for the view
   * @return the view name for displaying the cart item details
   * @throws RuntimeException if the cart item is not found
   */
  @GetMapping("/details/{id}")
  public String viewCartItemDetails(@PathVariable Long id, Model model) {
    CartItem cartItem = cartItemService.findById(id)
            .orElseThrow(() -> new RuntimeException("Item no encontrado"));
    model.addAttribute("cartItem", cartItem);
    return "cartitems/details";
  }
}
