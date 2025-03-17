package com.eafit.herzon.teis.bootstrap;

import com.eafit.herzon.teis.models.Auction;
import com.eafit.herzon.teis.models.Jewel;
import com.eafit.herzon.teis.models.Offer;
import com.eafit.herzon.teis.models.Order;
import com.eafit.herzon.teis.repositories.AuctionRepository;
import com.eafit.herzon.teis.repositories.JewelRepository;
import com.eafit.herzon.teis.repositories.OrderRepository;
import com.github.javafaker.Faker;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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
  private final OrderRepository orderRepository;
  private final JewelRepository jewelRepository;

  /**
   * Constructor for the DataLoader class.
   *
   * @param auctionRepository The repository for auctions.
   * @param orderRepository   The repository for orders.
   * @param jewelRepository   The repository for jewels.
   */
  public DataLoader(
      AuctionRepository auctionRepository,
      OrderRepository orderRepository,
      JewelRepository jewelRepository) {
    this.auctionRepository = auctionRepository;
    this.orderRepository = orderRepository;
    this.jewelRepository = jewelRepository;
  }

  /**
   * Method to load initial data into the database.
   *
   * @param args The command line arguments.
   * @throws Exception If an error occurs.
   */
  @Override
  public void run(String... args) throws Exception {
    // Generate jewels if none exist
    if (jewelRepository.count() == 0) {
      Faker faker = new Faker(Locale.of("es"));

      // Generate 10 jewels
      for (int i = 0; i < 10; i++) {
        Jewel jewel = new Jewel();
        jewel.setName(faker.commerce().productName());
        jewel.setCategory(faker.commerce().department());
        jewel.setDetails(faker.lorem().sentence());
        jewel.setPrice(Double.parseDouble(faker.commerce().price(50, 1000)));
        jewel.setImageUrl(
            "https://cdn-media.glamira.com/media/product/newgeneration/view/1/sku/15549gisu/"
                + "diamond/emerald_AA/stone2/diamond-Brillant_AAA/stone3/diamond-Brillant_AAA/"
                + "alloycolour/yellow.jpg");
        jewelRepository.save(jewel);
      }

      System.out.println("Initial jewels generated.");
    }

    // Generate auctions if none exist
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
        auction.setStatus(true);

        // Set a random jewel for the auction
        List<Jewel> jewels = jewelRepository.findAll();
        Jewel jewel = jewels.get(random.nextInt(jewels.size()));
        auction.setJewel(jewel);

        // Generate 2-5 offers per auction
        int numOffers = random.nextInt(4) + 2;
        for (int j = 0; j < numOffers; j++) {
          double offerPrice = startPrice + random.nextDouble() * 500;
          Offer offer = new Offer();
          offer.setOfferPrice(offerPrice);
          offer.setAuction(auction);

          if (offerPrice > auction.getCurrentPrice()) {
            auction.setCurrentPrice(offerPrice);
            offer.setState(true);
            // Set Auction active offers to false
            for (Offer activeOffer : auction.getOffers()) {
              activeOffer.setState(false);
            }
          }

          auction.getOffers().add(offer);
        }

        // Save auction (and cascade-save offers if configured)
        auctionRepository.save(auction);
      }

      // Generate 3 inactive auctions
      for (int i = 0; i < 3; i++) {
        // Create auction
        double startPrice = Double.parseDouble(faker.commerce().price(50, 1000));
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = startDate.minusDays(1);

        Auction auction = new Auction();
        auction.setStartPrice(startPrice);
        auction.setCurrentPrice(startPrice);
        auction.setStartDate(startDate);
        auction.setEndDate(endDate);
        auction.setStatus(false);

        // Set a random jewel for the auction
        List<Jewel> jewels = jewelRepository.findAll();
        Jewel jewel = jewels.get(random.nextInt(jewels.size()));
        auction.setJewel(jewel);

        // Generate 2-5 offers per auction
        int numOffers = random.nextInt(4) + 2;
        for (int j = 0; j < numOffers; j++) {
          double offerPrice = startPrice + random.nextDouble() * 500;
          Offer offer = new Offer();
          offer.setOfferPrice(offerPrice);
          offer.setAuction(auction);

          if (offerPrice > auction.getCurrentPrice()) {
            auction.setCurrentPrice(offerPrice);
            offer.setState(true);
            // Set Auction active offers to false
            for (Offer activeOffer : auction.getOffers()) {
              activeOffer.setState(false);
            }
          }

          auction.getOffers().add(offer);
        }

        // Save auction (and cascade-save offers if configured)
        auctionRepository.save(auction);
      }

      // Generate 3 auctions which will end in 5 minutes, 10 minutes, and 15 minutes
      for (int i = 0; i < 3; i++) {
        // Create auction
        double startPrice = Double.parseDouble(faker.commerce().price(50, 1000));
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusMinutes(5 * i + 5);

        Auction auction = new Auction();
        auction.setStartPrice(startPrice);
        auction.setCurrentPrice(startPrice);
        auction.setStartDate(startDate);
        auction.setEndDate(endDate);
        auction.setStatus(true);

        // Set a random jewel for the auction
        List<Jewel> jewels = jewelRepository.findAll();
        Jewel jewel = jewels.get(random.nextInt(jewels.size()));
        auction.setJewel(jewel);

        // Generate 2-5 offers per auction
        int numOffers = random.nextInt(4) + 2;
        for (int j = 0; j < numOffers; j++) {
          double offerPrice = startPrice + random.nextDouble() * 500;
          Offer offer = new Offer();
          offer.setOfferPrice(offerPrice);
          offer.setAuction(auction);

          if (offerPrice > auction.getCurrentPrice()) {
            auction.setCurrentPrice(offerPrice);
            offer.setState(true);
            // Set Auction active offers to false
            for (Offer activeOffer : auction.getOffers()) {
              activeOffer.setState(false);
            }
          }

          auction.getOffers().add(offer);
        }

        // Save auction (and cascade-save offers if configured)
        auctionRepository.save(auction);
      }

      System.out.println("Initial auctions and offers generated.");
    }
    // Generate orders if none exist
    if (orderRepository.count() == 0) {
      Faker faker = new Faker(Locale.of("es"));
      Random random = new Random();
      List<Order.OrderStatus> statuses = Arrays.asList(
          Order.OrderStatus.PENDING,
          Order.OrderStatus.PAID,
          Order.OrderStatus.CANCELED);

      // Generate 10 fake orders
      for (int i = 0; i < 10; i++) {
        Order order = new Order();
        order.setTotal(Double.parseDouble(
            faker.commerce().price(100, 2000))); // Total between $100-$2000
        // Get random status (weighted towards PENDING)
        Order.OrderStatus status = statuses.get(
            // First 5 orders get PENDING, last 5 get PAID/CANCELED
            random.nextInt(i < 5 ? 1 : 3) % statuses.size());
        order.setStatus(status);
        orderRepository.save(order);
      }

      System.out.println("Initial orders generated.");
    }
  }
}