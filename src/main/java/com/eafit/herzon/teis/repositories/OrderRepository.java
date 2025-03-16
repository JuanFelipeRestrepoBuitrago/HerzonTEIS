package com.eafit.herzon.teis.repositories;

import com.eafit.herzon.teis.models.Order;
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
public interface OrderRepository extends JpaRepository<Order, Long> {}