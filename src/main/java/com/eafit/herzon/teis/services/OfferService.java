package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.exceptions.InvalidOfferException;
import com.eafit.herzon.teis.models.Auction;
import com.eafit.herzon.teis.models.Offer;
import com.eafit.herzon.teis.repositories.AuctionRepository;
import com.eafit.herzon.teis.repositories.OfferRepository;
import com.eafit.herzon.teis.utils.Formatter;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * The OfferService class provides methods to send messages to clients
 * through the WebSocket server.
 */
@Service
public class OfferService {
  /**
   * The SimpMessagingTemplate object to send messages to the clients.
   */
  private SimpMessagingTemplate simpMessagingTemplate;
  /**
   * The OfferRepository object to access the offers in the database.
   */
  private OfferRepository offerRepository;
  /**
   * The AuctionRepository object to access the auctions in the database.
   */
  private AuctionRepository auctionRepository;

  /**
   * Constructor of the OfferService class.
   *
   * @param simpMessagingTemplate the SimpMessagingTemplate object.
   * @param offerRepository the OfferRepository object.
   * @param auctionRepository the AuctionRepository object.
   */
  public OfferService(
      SimpMessagingTemplate simpMessagingTemplate,
      OfferRepository offerRepository,
      AuctionRepository auctionRepository
  ) {
    this.simpMessagingTemplate = simpMessagingTemplate;
    this.offerRepository = offerRepository;
    this.auctionRepository = auctionRepository;
  }

  /**
   * Method to send a message to the clients subscribed to the auction topic
   * with the current offer price.
   *
   * @param offerPrice the new offer price.
   * @param auctionId the ID of the auction.
   * @throws InvalidOfferException if the new offer price is lower than the current 
      highest offer price or if the auction is not found.
   */
  @Transactional
  public void placeOffer(double offerPrice, Long auctionId) throws InvalidOfferException {
    // Find the auction with the specified ID
    Auction auction = auctionRepository.findById(auctionId)
        .orElseThrow(() -> new InvalidOfferException("Auction not found"));
    // Get all the offers with active state for the auction
    List<Offer> activeOffers = offerRepository.findByAuctionAndState(auction, true);

    if (offerPrice < auction.getCurrentPrice()) {
      throw new InvalidOfferException(
          "El precio de la oferta debe ser mayor al precio actual: " 
          + Formatter.formatCurrency(auction.getCurrentPrice(), 2)
        );
    }

    for (Offer offer : activeOffers) {
      // If the offer price is higher than the current offer price
      if (offerPrice > offer.getOfferPrice()) {
        offer.setState(false);
        offerRepository.save(offer);
      } else {
        throw new InvalidOfferException(
            "El precio de la oferta debe ser mayor al precio actual: " 
            + Formatter.formatCurrency(offer.getOfferPrice(), 2)
          );
      }
    }

    Offer newOffer = new Offer(offerPrice, auction);
    offerRepository.save(newOffer);

    // Update the current price of the auction
    auction.setCurrentPrice(offerPrice);
    auctionRepository.save(auction);

    // Send the new offer price to the clients subscribed to the auction topic
    simpMessagingTemplate.convertAndSend("/topic/auction/updates/" + auctionId, auction);
  }
}
