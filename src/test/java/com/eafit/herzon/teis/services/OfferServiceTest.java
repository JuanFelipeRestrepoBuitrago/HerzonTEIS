package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.exceptions.InvalidOfferException;
import com.eafit.herzon.teis.models.Auction;
import com.eafit.herzon.teis.models.Jewel;
import com.eafit.herzon.teis.models.Offer;
import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.repositories.AuctionRepository;
import com.eafit.herzon.teis.repositories.OfferRepository;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the OfferService class.
 */
@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

  /**
   * The mock repository used to simulate the OfferRepository dependency.
   */
  @Mock
  private OfferRepository offerRepository;

  /**
   * The mock repository used to simulate the AuctionRepository dependency.
   */
  @Mock
  private AuctionRepository auctionRepository;

  /**
   * The mock messaging template used to simulate the SimpMessagingTemplate
   * dependency.
   */
  @Mock
  private SimpMessagingTemplate messagingTemplate;

  /**
   * The service to test.
   */
  @InjectMocks
  private OfferService offerService;

  /**
   * Auction object used in the tests.
   */
  private Auction auction;

  /**
   * The active offers used in the tests.
   */
  private Offer activeOffer;

  /**
   * Jewel object used in the tests.
   */
  private Jewel jewel;

  /**
   * User object used in the tests.
   */
  private CustomUser user;

  @BeforeEach
  public void setUp() {
    // Initialize test data
    user = new CustomUser("Test User", "test@example.com", "testpassword");
    jewel = new Jewel("Test Jewel", "Test Category", "Test Details", 100.0, "Test Image URL");
    auction = new Auction(LocalDateTime.now(), LocalDateTime.now().plusDays(1), 100.0, 100.0, jewel);
    activeOffer = new Offer(1500, auction, user);
  }

  /**
   * Test the placeOffer method when the offer is placed with an invalid auction
   * ID.
   * The method should throw an exception.
   */
  @Test
  void placeOffer_ShouldThrowWhenAuctionNotFound() {
    // Arrange the mock repository behavior
    when(auctionRepository.findById(anyLong()))
        .thenReturn(Optional.empty());

    assertThrows(InvalidOfferException.class, () -> offerService.placeOffer(150.0, 1L, user));
  }

  /**
   * Test the placeOffer method when the offer is placed with a invalid price.
   * The method should throw an exception.
   */
  @Test
  void placeOffer_ShouldRejectLowerPrice() {
    when(auctionRepository.findById(anyLong()))
        .thenReturn(Optional.of(auction));
    when(offerRepository.findByAuctionAndStateAndUser(auction, true, user))
        .thenReturn(List.of(this.activeOffer));

    // placeOffer should throw an exception when the offer price is lower than the
    // current offer price
    InvalidOfferException exception = assertThrows(
        InvalidOfferException.class,
        () -> offerService.placeOffer(150, 1L, user));

    assertTrue(exception.getMessage()
        .contains("oferta debe ser mayor al precio actual"));
  }

  /**
   * Test the placeOffer method when the offer is placed with a valid price.
   * The method should complete successfully.
   */
  @Test
  void placeOffer_ShouldCompleteSuccessfully() {
    when(auctionRepository.findById(anyLong()))
        .thenReturn(Optional.of(this.auction));
    when(offerRepository.findByAuctionAndStateAndUser(auction, true, user))
        .thenReturn(List.of(this.activeOffer));
    when(offerRepository.save(any(Offer.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // Call the method to test
    offerService.placeOffer(1700.0, 1L, user);

    // Assert
    verify(offerRepository, times(2)).save(any(Offer.class));
    verify(auctionRepository, times(1)).save(auction);
    verify(messagingTemplate, times(1))
        .convertAndSend(anyString(), any(Auction.class));
  }
}