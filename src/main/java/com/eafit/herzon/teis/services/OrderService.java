package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.exceptions.InvalidOrderException;
import com.eafit.herzon.teis.models.Order;
import com.eafit.herzon.teis.models.Order.OrderStatus;
import com.eafit.herzon.teis.repositories.OrderRepository;
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
   * Constructor of the OrderService class.
   *
   * @param orderRepository the OrderRepository object.
   */
  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
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
   * @return The order with the given id.
   */
  @Transactional(readOnly = true)
  public Order getOrderById(long id) {
    Order order = orderRepository.findById(id).orElse(null);

    if (order == null) {
      throw new InvalidOrderException("La orden con el id " + id + " no existe.");
    }

    return order;
  }

  /**
   * Method to cancel an order.
   *
   * @param id The id of the order to cancel.
   */
  @Transactional
  public void cancel(long id) {
    Order order = orderRepository.findById(id).orElse(null);

    if (order != null) {
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
   */
  @Transactional
  public void submit(long id) {
    Order order = orderRepository.findById(id).orElse(null);

    if (order != null) {
      order.setStatus(OrderStatus.PAID);
      orderRepository.save(order);
    } else {
      throw new InvalidOrderException("La orden con el id " + id + " no existe.");
    }
  }
}
