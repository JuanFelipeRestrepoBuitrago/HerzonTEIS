package com.eafit.herzon.teis.repositories;

import com.eafit.herzon.teis.models.Auction;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Interface that provides methods to interact with the database and the
 * Auctions
 * table through the JPA repository.
 * This interface extends the JpaRepository interface, which provides CRUD
 * operations
 */
@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
  /**
   * Finds all auctions whose end date is greater than the current date and time.
   *
   * @param currentDateTime The current date and time.
   * @return List of active auctions.
   */
  @Query("SELECT a FROM Auction AS a WHERE a.endDate > :currentDateTime")
  List<Auction> findAllActiveAuctions(@Param("currentDateTime") LocalDateTime currentDateTime);
}
