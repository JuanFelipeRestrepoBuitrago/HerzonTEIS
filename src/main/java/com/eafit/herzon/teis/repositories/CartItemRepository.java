package com.eafit.herzon.teis.repositories;

import com.eafit.herzon.teis.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> { }