package com.eafit.herzon.teis.repositories;

import com.eafit.herzon.teis.models.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<Auction, Long> { }