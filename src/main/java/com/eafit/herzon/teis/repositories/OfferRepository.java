package com.eafit.herzon.teis.repositories;

import com.eafit.herzon.teis.models.Auction;
import com.eafit.herzon.teis.models.Offer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface that provides methods to interact with the database and the Offer
 * table
 * through the JPA repository.
 * This interface extends the JpaRepository interface, which provides CRUD
 * operations
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
  /**
   * Finds all offers with the specified state and auction.

   * @param state the state of the offers to find.
   * @param auction the auction to which the offers belong.
   * @return a list of offers with the specified state.
   */
  List<Offer> findByAuctionAndState(Auction auction, boolean state);
}