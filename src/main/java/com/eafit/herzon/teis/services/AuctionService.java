package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.dto.AuctionDto;
import com.eafit.herzon.teis.exceptions.InvalidAuctionException;
import com.eafit.herzon.teis.exceptions.JewelNotFoundException;
import com.eafit.herzon.teis.models.Auction;
import com.eafit.herzon.teis.models.CartItem;
import com.eafit.herzon.teis.models.Jewel;
import com.eafit.herzon.teis.models.Offer;
import com.eafit.herzon.teis.models.Order;
import com.eafit.herzon.teis.repositories.AuctionRepository;
import com.eafit.herzon.teis.repositories.JewelRepository;
import com.eafit.herzon.teis.repositories.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
  private final AuctionRepository auctionRepository;
  /**
   * The OrderRepository object to access the orders in the database.
   */
  private final OrderRepository orderRepository;
  /**
   * The JewelRepository object to access the jewels in the database.
   */
  private final JewelRepository jewelRepository;

  /**
   * Constructor of the AuctionService class.
   *
   * @param auctionRepository the AuctionRepository object.
   * @param orderRepository   the OrderRepository object.
   * @param jewelRepository   the JewelRepository object.
   */
  public AuctionService(
      AuctionRepository auctionRepository,
      OrderRepository orderRepository,
      JewelRepository jewelRepository) {
    this.auctionRepository = auctionRepository;
    this.orderRepository = orderRepository;
    this.jewelRepository = jewelRepository;
  }

  /**
   * Method to get all active the auctions in the database.
   *
   * @param page the page number (0-based).
   * @param size the number of items per page.
   * @return List of all active the auctions in the database (with pagination).
   */
  @Transactional(readOnly = true)
  public Page<Auction> getAllActiveAuctions(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return auctionRepository.findAllActiveAuctions(LocalDateTime.now(), pageable);
  }

  /**
   * Method to get all inactive the auctions in the database.
   *
   * @param page the page number (0-based).
   * @param size the number of items per page.
   * @return List of all inactive the auctions in the database (with pagination).
   */
  @Transactional(readOnly = true)
  public Page<Auction> getAllInactiveAuctions(int page, int size) {
    return auctionRepository.findAllByStatusAndEndDateBefore(
        false,
        LocalDateTime.now(),
        PageRequest.of(page, size));
  }

  /**
   * Method to get the auction with the specified ID.
   *
   * @param auctionId the ID of the auction.
   * @return the auction with the specified ID.
   */
  @Transactional(readOnly = true)
  public Auction getAuctionById(Long auctionId) {
    Auction auction = auctionRepository.findById(auctionId).orElse(null);
    if (auction == null) {
      throw new InvalidAuctionException("Auction with ID " + auctionId + " not found.");
    }
    return auction;
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
        Order order = new Order(offer.getOfferPrice(), offer.getUser());
        order.getCartItems().add(new CartItem(auction.getJewel(), 1));
        orderRepository.save(order);
      });

      // Close the auction
      auction.setStatus(false);
      auctionRepository.save(auction);
    });
  }

  /**
   * Method to get all the auctions in the database.
   *
   * @param page the page number (0-based).
   * @param size the number of items per page.
   * @return List of all the auctions in the database (with pagination).
   */
  @Transactional(readOnly = true)
  public Page<Auction> getAllAuctions(int page, int size) {
    return auctionRepository.findAll(PageRequest.of(page, size));
  }

  /**
   * Method to save an auction in the database.
   *
   * @param auctionDto the auction to save.
   * @throws JewelNotFoundException  if the jewel associated with the auction is
   *                                 not found.
   * @throws EntityNotFoundException if the auction is not found.
   */
  @Transactional
  public void save(AuctionDto auctionDto) throws JewelNotFoundException, EntityNotFoundException {
    Jewel jewel = jewelRepository.findById(auctionDto.getJewelId())
        .orElse(null);
    if (jewel == null) {
      throw new JewelNotFoundException(
          "Joya con Id " + auctionDto.getJewelId() + " no encontrada.");
    }

    // Convert date strings to LocalDateTime
    auctionDto.setStartDate(LocalDateTime.parse(auctionDto.getStartDateString()));
    auctionDto.setEndDate(LocalDateTime.parse(auctionDto.getEndDateString()));

    if (auctionDto.getStartDate().isAfter(auctionDto.getEndDate())) {
      throw new InvalidAuctionException("La fecha de inicio debe ser antes de la fecha de fin.");
    }

    Double currentPrice = auctionDto.getCurrentPrice() == null
        ? auctionDto.getStartPrice()
        : auctionDto.getCurrentPrice();

    Auction auction;
    if (auctionDto.getAuctionId() == null) {
      auction = new Auction(
          auctionDto.getStartDate(),
          auctionDto.getEndDate(),
          auctionDto.getStartPrice(),
          currentPrice,
          jewel);
    } else {
      auction = auctionRepository.findById(auctionDto.getAuctionId())
          .orElseThrow(
              () -> new EntityNotFoundException(
                  "Subasta con Id " + auctionDto.getAuctionId() + " no encontrada."));
      System.out.println(auctionDto.getStartDate().toString());
      auction.setStartDate(auctionDto.getStartDate());
      auction.setEndDate(auctionDto.getEndDate());
      auction.setStartPrice(auctionDto.getStartPrice());
      auction.setCurrentPrice(currentPrice);
      auction.setJewel(jewel);
    }

    auctionRepository.save(auction);
  }
}
