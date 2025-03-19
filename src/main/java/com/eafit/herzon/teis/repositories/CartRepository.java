package com.eafit.herzon.teis.repositories;


import com.eafit.herzon.teis.models.Auction;
import com.eafit.herzon.teis.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing cart-related data.
 * Extends JpaRepository to provide CRUD operations for Auction entities.
 */
public interface CartRepository extends JpaRepository<Cart, Long> {}
