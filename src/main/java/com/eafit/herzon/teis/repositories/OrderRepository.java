package com.eafit.herzon.teis.repositories;

import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface that provides methods to interact with the database and the Order
 * table
 * through the JPA repository.
 * This interface extends the JpaRepository interface, which provides CRUD
 * operations
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  /**
   * Method to find all the orders in the database by user.
   *
   * @param user the user to find the orders by.
   * @return List of all the orders in the database by user.
   */
  Page<Order> findAllByUser(CustomUser user, Pageable pageable);
}