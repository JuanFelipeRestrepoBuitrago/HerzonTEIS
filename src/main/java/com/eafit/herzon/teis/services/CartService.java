package com.eafit.herzon.teis.services;

import static com.eafit.herzon.teis.models.Order.OrderStatus.PENDING;

import com.eafit.herzon.teis.models.Cart;
import com.eafit.herzon.teis.models.CartItem;
import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.models.Jewel;
import com.eafit.herzon.teis.models.Order;
import com.eafit.herzon.teis.repositories.CartItemRepository;
import com.eafit.herzon.teis.repositories.CartRepository;
import com.eafit.herzon.teis.repositories.JewelRepository;
import com.eafit.herzon.teis.repositories.OrderRepository;
import com.eafit.herzon.teis.repositories.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
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
  private final OrderRepository orderRepository;

  /**
   * Constructs a new CartService with the specified repositories.
   *
   * @param cartRepository     the repository for managing cart data
   * @param userRepository     the repository for managing user data
   * @param jewelRepository    the repository for managing jewel data
   * @param cartItemRepository the repository for managing cart item data
   */
  public CartService(CartRepository cartRepository, UserRepository userRepository,
                     JewelRepository jewelRepository, CartItemRepository cartItemRepository,
                     OrderRepository orderRepository) {
    this.cartRepository = cartRepository;
    this.userRepository = userRepository;
    this.jewelRepository = jewelRepository;
    this.cartItemRepository = cartItemRepository;
    this.orderRepository = orderRepository;
  }

  /**
   * Adds an item to the user's cart.
   *
   * @param userId   the ID of the user
   * @param jewelId  the ID of the jewel to add
   * @param quantity the quantity of the item to add
   * @throws RuntimeException if the user or jewel is not found
   */

  @Transactional
  public void addItem(long userId, Long jewelId, int quantity) {
    CustomUser user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    Jewel jewel = jewelRepository.findById(jewelId)
            .orElseThrow(() -> new RuntimeException("Joya no encontrada"));

    Cart cart = user.getCart();

    // Buscar si ya existe un CartItem para esa joya
    CartItem cartItem = cart.getItems().stream()
            .filter(item -> item.getJewel().getId().equals(jewelId))
            .findFirst()
            .orElse(null);

    if (cartItem == null) {
      // Si no existe, creamos uno nuevo con la cantidad proporcionada
      cartItem = new CartItem(jewel, quantity, cart);
      cart.addItem(cartItem);
    } else {
      // Si ya existe, actualizamos la cantidad sumando la nueva cantidad a la existente
      cartItem.setQuantity(cartItem.getQuantity() + quantity);
    }

    cartItemRepository.save(cartItem);
  }

  /**
   * Removes an item from the user's cart.
   *
   * @param userId     the ID of the user
   * @param cartItemId the ID of the cart item to remove
   * @throws RuntimeException if the user or cart item is not found
   */
  @Transactional
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

  /**
   * Realiza el proceso de checkout para el usuario indicado.
   * Calcula el total del carrito, crea una orden y vacía el carrito.
   *
   * @param userId El ID del usuario que realiza el checkout.
   * @throws RuntimeException si el usuario no existe o el carrito está vacío.
   */
  @Transactional
  public void checkout(long userId) {
    CustomUser user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    Cart cart = user.getCart();
    if (cart == null || cart.getItems().isEmpty()) {
      throw new RuntimeException("El carrito está vacío, no se puede realizar el checkout.");
    }

    double total = cart.getItems().stream()
            .mapToDouble((CartItem item) -> item.getJewel().getPrice() * item.getQuantity())
            .sum();

    Order order = new Order();
    order.setUser(user);
    order.setTotal(total);
    order.setStatus(PENDING);

    List<CartItem> updatedItems = cart.getItems().stream()
        .map(item -> {
          CartItem newItem = new CartItem();
          newItem.setQuantity(item.getQuantity());
          newItem.setJewel(item.getJewel());
          newItem.setCart(null); // Set cart to null
          return newItem;
        }).collect(Collectors.toList());
    order.setCartItems(updatedItems);

    orderRepository.save(order);

    cart.getItems().clear();
    cartRepository.save(cart);
  }
}