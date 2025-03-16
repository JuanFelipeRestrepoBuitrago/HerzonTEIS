package com.eafit.herzon.teis.bootstrap;

import com.eafit.herzon.teis.models.Auction;
import com.eafit.herzon.teis.models.Offer;
import com.eafit.herzon.teis.repositories.AuctionRepository;
import com.github.javafaker.Faker;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Random;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Class to load initial data into the database.
 */
@Component
public class DataLoader implements CommandLineRunner {

  private final AuctionRepository auctionRepository;

  /**
   * Constructor for the DataLoader class.
   *
   * @param auctionRepository The repository for auctions.
   */
  public DataLoader(AuctionRepository auctionRepository) {
    this.auctionRepository = auctionRepository;
  }

  /**
   * Method to load initial data into the database.
   *
   * @param args The command line arguments.
   * @throws Exception If an error occurs.
   */
  @Override
  public void run(String... args) throws Exception {
    // Check if data already exists
    if (auctionRepository.count() == 0) {
      Faker faker = new Faker(Locale.of("es"));
      Random random = new Random();

      // Generate 3 auctions
      for (int i = 0; i < 3; i++) {
        // Create auction
        double startPrice = Double.parseDouble(faker.commerce().price(50, 1000));
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = startDate.plusDays(7);

        Auction auction = new Auction();
        auction.setStartPrice(startPrice);
        auction.setCurrentPrice(startPrice);
        auction.setStartDate(startDate);
        auction.setEndDate(endDate);

        // Generate 2-5 offers per auction
        int numOffers = random.nextInt(4) + 2;
        for (int j = 0; j < numOffers; j++) {
          double offerPrice = startPrice + random.nextDouble() * 500;
          Offer offer = new Offer();
          offer.setOfferPrice(offerPrice);
          offer.setAuction(auction);
          auction.getOffers().add(offer);
        }

        // Save auction (and cascade-save offers if configured)
        auctionRepository.save(auction);
      }

      System.out.println("Initial auctions and offers generated.");
    }
  }
}