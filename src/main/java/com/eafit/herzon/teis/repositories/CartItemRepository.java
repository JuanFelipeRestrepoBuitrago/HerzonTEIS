package com.eafit.herzon.teis.repositories;

import com.eafit.herzon.teis.models.CartItem;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for managing cart item data.
 * Extends JpaRepository to provide CRUD operations and custom queries for CartItem entities.
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

  /**
   * Finds a cart item by its ID.
   *
   * @param id the ID of the cart item to find
   * @return an Optional containing the cart item if found, otherwise empty
   */
  @Override
  Optional<CartItem> findById(Long id);

  /**
   * Finds all cart items associated with a specific user, paginated.
   *
   * @param userId  the ID of the user whose cart items are being retrieved
   * @param pageable the pagination information (page number, page size, etc.)
   * @return a Page of cart items associated with the user
   */
  @Query("SELECT ci FROM CartItem ci WHERE ci.cart.user.id = :userId")
  Page<CartItem> findByUserId(@Param("userId") Long userId, Pageable pageable);
}