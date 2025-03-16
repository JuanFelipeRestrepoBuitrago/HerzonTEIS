package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.models.Auction;
import com.eafit.herzon.teis.models.Offer;
import com.eafit.herzon.teis.models.Order;
import com.eafit.herzon.teis.repositories.AuctionRepository;
import com.eafit.herzon.teis.repositories.OrderRepository;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.scheduling.annotation.Scheduled;
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
   * The OrderRepository object to access the orders in the database.
   */
  private final OrderRepository orderRepository;

  /**
   * Constructor of the AuctionService class.

   * @param auctionRepository the AuctionRepository object.
   */
  public AuctionService(AuctionRepository auctionRepository, OrderRepository orderRepository) {
    this.auctionRepository = auctionRepository;
    this.orderRepository = orderRepository;
  }

  /**
   * Method to get all active the auctions in the database.

   * @return List of all active the auctions in the database
   */
  @Transactional(readOnly = true)
  public List<Auction> getAllActiveAuctions() {
    return auctionRepository.findAllActiveAuctions(LocalDateTime.now());
  }

    /**
   * Method to get all inactive the auctions in the database.

   * @return List of all inactive the auctions in the database
   */
  @Transactional(readOnly = true)
  public List<Auction> getAllInactiveAuctions() {
    return auctionRepository.findAllByStatusAndEndDateBefore(false, LocalDateTime.now());
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

  /**
   * Method to close the auctions that have expired.
   */
  @Scheduled(cron = "0 * * * * *") // Run every minute
  @Transactional
  public void closeExpiredAuctions() {
    LocalDateTime now = LocalDateTime.now();
    List<Auction> expiredAuctions = auctionRepository.findAllByStatusAndEndDateBefore(true, now);

    expiredAuctions.forEach(auction -> {
      // Find winning offer
      Optional<Offer> winningOffer = auction.getOffers().stream()
          .max(Comparator.comparingDouble(Offer::getOfferPrice));

      winningOffer.ifPresent(offer -> {
        // Create order for winner
        Order order = new Order();
        order.setTotal(offer.getOfferPrice());
        order.setStatus(Order.OrderStatus.PENDING);
        orderRepository.save(order);
      });

      // Close the auction
      auction.setStatus(false);
      auctionRepository.save(auction);
    });
  }
}
