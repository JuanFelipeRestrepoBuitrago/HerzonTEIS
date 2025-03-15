package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.models.Auction;
import com.eafit.herzon.teis.repositories.AuctionRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for handling auction operations.
 */
@Service
public class AuctionService {

  /**
   * The AuctionRepository object to access the auctions in the database.
   */
  private AuctionRepository auctionRepository;

  /**
   * Constructor of the AuctionService class.

   * @param auctionRepository the AuctionRepository object.
   */
  public AuctionService(AuctionRepository auctionRepository) {
    this.auctionRepository = auctionRepository;
  }

  /**
   * Method to get all the auctions in the database.

   * @return List of all the auctions in the database
   */
  @Transactional(readOnly = true)
  public List<Auction> getAllActiveAuctions() {
    return auctionRepository.findAllActiveAuctions(LocalDateTime.now());
  }

  /**
   * Method to get the auction with the specified ID.

   * @param auctionId the ID of the auction.
   * @return the auction with the specified ID.
   */
  @Transactional(readOnly = true)
  public Auction getAuctionById(Long auctionId) {
    return auctionRepository.findById(auctionId).orElse(null);
  }
}
