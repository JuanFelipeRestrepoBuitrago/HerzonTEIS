package com.eafit.herzon.teis.repositories;

import com.eafit.herzon.teis.models.Auction;
import com.eafit.herzon.teis.models.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing wishlist-related data.
 * Extends JpaRepository to provide CRUD operations for Auction entities.
 */
public interface WishListRepository extends JpaRepository<WishList, Long> {}
