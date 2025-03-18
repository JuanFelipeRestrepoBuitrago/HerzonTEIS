package com.eafit.herzon.teis.bootstrap;

import com.eafit.herzon.teis.models.Auction;
import com.eafit.herzon.teis.models.CartItem;
import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.models.Jewel;
import com.eafit.herzon.teis.models.Offer;
import com.eafit.herzon.teis.models.Order;
import com.eafit.herzon.teis.repositories.AuctionRepository;
import com.eafit.herzon.teis.repositories.CartItemRepository;
import com.eafit.herzon.teis.repositories.JewelRepository;
import com.eafit.herzon.teis.repositories.OrderRepository;
import com.eafit.herzon.teis.repositories.UserRepository;
import com.github.javafaker.Faker;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Loads initial data into the database when the application starts.
 * This class populates the database with users, jewels, auctions, and orders
 * if they do not already exist, using fake data generated by the Faker library.
 */
@Component
public class DataLoader implements CommandLineRunner {

  private final AuctionRepository auctionRepository;
  private final OrderRepository orderRepository;
  private final JewelRepository jewelRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final CartItemRepository cartItemRepository;

  /**
   * Constructs a new DataLoader with the required dependencies.
   *
   * @param auctionRepository the repository for managing auctions
   * @param orderRepository   the repository for managing orders
   * @param jewelRepository   the repository for managing jewels
   * @param userRepository    the repository for managing users
   * @param passwordEncoder   the encoder for hashing passwords
   * @param cartItemRepository the repository for managing cart items
   */
  public DataLoader(
      AuctionRepository auctionRepository,
      OrderRepository orderRepository,
      JewelRepository jewelRepository,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      CartItemRepository cartItemRepository) {
    this.auctionRepository = auctionRepository;
    this.orderRepository = orderRepository;
    this.jewelRepository = jewelRepository;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.cartItemRepository = cartItemRepository;
  }

  /**
   * Loads initial data into the database if it does not already exist.
   * Generates users (1 admin and 9 regular users), jewels, auctions (active and
   * ended),
   * and orders with various statuses.
   *
   * @param args the command-line arguments passed to the application
   * @throws Exception if an error occurs during data loading
   */
  @Override
  public void run(String... args) throws Exception {
    Faker faker = new Faker(new Locale("es"));

    // Generate users if none exist
    System.out.println("Checking if users exist...");
    if (userRepository.count() == 0) {
      // Create Admin User
      CustomUser admin = new CustomUser();
      admin.setUsername("admin");
      admin.setName("Administrator");
      admin.setEmail("admin@herzon.com");
      admin.setAddress("Calle 123, Ciudad");
      admin.setPassword(passwordEncoder.encode("123"));
      admin.setRole(CustomUser.Role.ADMIN);
      userRepository.save(admin);

      // Create 9 Normal Users
      for (int i = 0; i < 9; i++) {
        CustomUser user = new CustomUser();
        user.setUsername(faker.name().username());
        user.setName(faker.name().fullName());
        user.setEmail(faker.internet().emailAddress());
        user.setAddress(faker.address().fullAddress());
        user.setPassword(passwordEncoder.encode("123"));
        user.setRole(CustomUser.Role.USER);
        userRepository.save(user);
      }

      System.out.println("Initial users (1 ADMIN, 9 USERS) generated.");
    } else {
      System.out.println("Users already exist, skipping user generation.");
    }

    // Generate jewels if none exist
    System.out.println("Checking if jewels exist...");
    long jewelCount = jewelRepository.count();
    System.out.println("Current jewel count: " + jewelCount);
    if (jewelCount == 0) {
      System.out.println("No jewels found, generating initial jewels...");
      List<String> jewelCategories = Arrays.asList(
          "Anillos",
          "Pulseras",
          "Pendientes",
          "Cadenas",
          "Dijes");
      Random random = new Random();
      for (int i = 0; i < 10; i++) {
        Jewel jewel = new Jewel();
        jewel.setName(faker.commerce().productName());
        String selectedCategory = jewelCategories.get(random.nextInt(jewelCategories.size()));
        jewel.setCategory(selectedCategory);
        jewel.setDetails(faker.lorem().sentence());
        jewel.setPrice(Double.parseDouble(faker.commerce().price(50, 1000)));
        String imageUrlBase = "https://cdn-media.glamira.com/media/product/newgeneration/view/1/sku/15549gisu/";
        String imageUrlDetailsPart1 = "diamond/emerald_AA/stone2/";
        String imageUrlDetailsPart2 = "diamond-Brillant_AAA/stone3/diamond-Brillant_AAA/";
        String imageUrlEnd = "alloycolour/yellow.jpg";
        String fullImageUrl = imageUrlBase + imageUrlDetailsPart1
            + imageUrlDetailsPart2 + imageUrlEnd;
        jewel.setImageUrl(fullImageUrl);
        System.out.println("Saving jewel: " + jewel.getName()
            + ", Category: " + jewel.getCategory());
        try {
          jewelRepository.save(jewel);
        } catch (Exception e) {
          System.err.println("Error saving jewel: " + e.getMessage());
          e.printStackTrace();
        }
      }

      System.out.println("Initial jewels generated.");
    } else {
      System.out.println("Jewels already exist, skipping jewel generation.");
    }

    // Generate auctions if none exist
    System.out.println("Checking if auctions exist...");
    if (auctionRepository.count() == 0) {
      Random random = new Random();
      // Active auctions (future dates)
      for (int i = 0; i < 3; i++) {
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

          // Set no admin user as the offer creator
          List<CustomUser> users = userRepository.findAllByRole(CustomUser.Role.USER);
          CustomUser user = users.get(random.nextInt(users.size()));
          offer.setUser(user);

          auction.getOffers().add(offer);
        }

        auctionRepository.save(auction);
      }

      // Ended auctions (past dates)
      for (int i = 0; i < 3; i++) {
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

          // Set no admin user as the offer creator
          List<CustomUser> users = userRepository.findAllByRole(CustomUser.Role.USER);
          CustomUser user = users.get(random.nextInt(users.size()));
          offer.setUser(user);

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

          // Set no admin user as the offer creator
          List<CustomUser> users = userRepository.findAllByRole(CustomUser.Role.USER);
          CustomUser user = users.get(random.nextInt(users.size()));
          offer.setUser(user);

          auction.getOffers().add(offer);
        }

        auctionRepository.save(auction);
      }

      System.out.println("Initial auctions and offers generated.");
    } else {
      System.out.println("Auctions already exist, skipping auction generation.");
    }

    // Generate orders if none exist
    System.out.println("Checking if orders exist...");
    if (orderRepository.count() == 0) {
      Random random = new Random();
      List<Order.OrderStatus> statuses = Arrays.asList(
          Order.OrderStatus.PENDING,
          Order.OrderStatus.PAID,
          Order.OrderStatus.CANCELED);
      // Set no admin user as the offer creator
      List<CustomUser> users = userRepository.findAllByRole(CustomUser.Role.USER);
      List<Jewel> jewels = jewelRepository.findAll();
      

      for (int i = 0; i < 10; i++) {
        CustomUser user = users.get(random.nextInt(users.size()));
        Order order = new Order();
        order.setUser(user);
        order.setTotal(0.0);
        
        // Add 1-3 cart items to order
        int numItems = random.nextInt(3) + 1;
        for (int j = 0; j < numItems; j++) {
          Jewel jewel = jewels.get(random.nextInt(jewels.size()));
          CartItem cartItem = new CartItem(jewel, random.nextInt(3) + 1);
          order.getCartItems().add(cartItem);
          order.setTotal(order.getTotal() + cartItem.getQuantity() * jewel.getPrice());
        }

        Order.OrderStatus status = statuses.get(
            // First 5 orders get PENDING, last 5 get PAID/CANCELED
            random.nextInt(i < 5 ? 1 : 3) % statuses.size());
        order.setStatus(status);
        orderRepository.save(order);
      }

      System.out.println("Initial orders generated.");
    } else {
      System.out.println("Orders already exist, skipping order generation.");
    }
  }
}