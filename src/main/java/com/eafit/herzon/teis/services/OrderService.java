package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.exceptions.InvalidOrderException;
import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.models.Order;
import com.eafit.herzon.teis.models.Order.OrderStatus;
import com.eafit.herzon.teis.repositories.OrderRepository;
import com.eafit.herzon.teis.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for handling orders logic and operations.
 */
@Service
public class OrderService {

  /**
   * The OrderRepository object to access the orders in the database.
   */
  private OrderRepository orderRepository;

  /**
   * The UserRepository object to access the users in the database.
   */
  private UserRepository userRepository;

  /**
   * Constructor of the OrderService class.
   *
   * @param orderRepository the OrderRepository object.
   * @param userRepository the UserRepository object.
   */
  public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
    this.orderRepository = orderRepository;
    this.userRepository = userRepository;
  }

  /**
   * Method to get all the orders in the database.
   *
   * @param page the page number (0-based).
   * @param size the number of items per page.
   * @return List of all the orders in the database
   */
  @Transactional(readOnly = true)
  public Page<Order> getAllOrdersByUsername(String username, int page, int size) {
    CustomUser user = this.userRepository.findByUsername(username)
        .orElseThrow(() -> new InvalidOrderException(
            "El usuario con el nombre de usuario " + username + " no existe."
          ));
    Pageable pageable = PageRequest.of(page, size);
    return orderRepository.findAllByUser(user, pageable);
  }

  /**
   * Method to get all the orders in the database.
   *
   * @param page the page number (0-based).
   * @param size the number of items per page.
   * @return List of all the orders in the database
   */
  @Transactional(readOnly = true)
  public Page<Order> getAllOrders(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return orderRepository.findAll(pageable);
  }

  /**
   * Method to get an order by its id.
   *
   * @param id The id of the order to get.
   * @param username The username of the logged user.
   * @return The order with the given id.
   */
  @Transactional(readOnly = true)
  public Order getOrderById(long id, String username) {
    Order order = orderRepository.findById(id).orElse(null);

    if (order == null) {
      throw new InvalidOrderException("La orden con el id " + id + " no existe.");
    }

    if (!order.getUser().getUsername().equals(username)) {
      throw new InvalidOrderException("La orden con el id " + id + " no pertenece al usuario " + username);
    }

    return order;
  }

  /**
   * Method to cancel an order.
   *
   * @param id The id of the order to cancel.
   * @param username The username of the logged user.
   */
  @Transactional
  public void cancel(long id, String username) {
    Order order = orderRepository.findById(id).orElse(null);

    if (order != null) {
      if (!order.getUser().getUsername().equals(username)) {
        throw new InvalidOrderException("La orden con el id " + id + " no pertenece al usuario " + username);
      }
      order.setStatus(OrderStatus.CANCELED);
      orderRepository.save(order);
    } else {
      throw new InvalidOrderException("La orden con el id " + id + " no existe.");
    }
  }

  /**
   * Method to pay an order.
   *
   * @param id The id of the order to pay.
   * @param username The username of the logged user.
   */
  @Transactional
  public void submit(long id, String username) {
    Order order = orderRepository.findById(id).orElse(null);

    if (order != null) {
      if (!order.getUser().getUsername().equals(username)) {
        throw new InvalidOrderException("La orden con el id " + id + " no pertenece al usuario " + username);
      }
      order.setStatus(OrderStatus.PAID);
      orderRepository.save(order);
    } else {
      throw new InvalidOrderException("La orden con el id " + id + " no existe.");
    }
  }
}
