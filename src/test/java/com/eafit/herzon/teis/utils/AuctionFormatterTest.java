package com.eafit.herzon.teis.utils;

import com.eafit.herzon.teis.dto.AuctionDto;
import com.eafit.herzon.teis.models.Auction;
import com.eafit.herzon.teis.models.Jewel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the AuctionFormatter utility class.
 */
@ExtendWith(MockitoExtension.class)
public class AuctionFormatterTest {

  private static final DateTimeFormatter DATE_FORMATTER = 
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

  private Auction auction;
  private AuctionDto auctionDto;
  private Jewel jewel;

  /**
   * Sets up test data before each test.
   */
  @BeforeEach
  public void setUp() {
    jewel = new Jewel("Emerald Ring", "Ring", "Colombian Emerald", 5000.0, "ring.jpg");
    jewel.setId(1L);

    auction = new Auction(
        LocalDateTime.parse("2025-05-01T10:00", DATE_FORMATTER),
        LocalDateTime.parse("2025-05-02T10:00", DATE_FORMATTER),
        1000.0,
        1500.0,
        jewel
    );
    auction.setId(1L);
    auction.setStatus(true);

    auctionDto = new AuctionDto();
    auctionDto.setAuctionId(1L);
    auctionDto.setJewelId(1L);
    auctionDto.setStartDateString("2025-05-01T10:00");
    auctionDto.setEndDateString("2025-05-02T10:00");
    auctionDto.setStartPrice(1000.0);
    auctionDto.setCurrentPrice(1500.0);
    auctionDto.setStatus(true);
  }

  /**
   * Tests the convertToEntity method to ensure it correctly converts an AuctionDto
   * to an Auction entity.
   */
  @Test
  public void convertToEntity_ShouldMapCorrectly() {
    // Arrange
    Auction targetAuction = new Auction();

    // Act
    Auction result = AuctionFormatter.convertToEntity(auctionDto, jewel, targetAuction);

    // Assert
    assertEquals(jewel, result.getJewel());
    assertEquals(LocalDateTime.parse("2025-05-01T10:00", DATE_FORMATTER), result.getStartDate());
    assertEquals(LocalDateTime.parse("2025-05-02T10:00", DATE_FORMATTER), result.getEndDate());
    assertEquals(1000.0, result.getStartPrice());
    assertEquals(1500.0, result.getCurrentPrice());
    assertEquals(true, result.getStatus());
  }

  /**
   * Tests the convertToDto method to ensure it correctly converts an Auction entity
   * to an AuctionDto.
   */
  @Test
  public void convertToDto_ShouldMapCorrectly() {
    // Act
    AuctionDto result = AuctionFormatter.convertToDto(auction);

    // Assert
    assertEquals(1L, result.getAuctionId());
    assertEquals(1L, result.getJewelId());
    assertEquals("2025-05-01T10:00", result.getStartDateString());
    assertEquals("2025-05-02T10:00", result.getEndDateString());
    assertEquals(1000.0, result.getStartPrice());
    assertEquals(1500.0, result.getCurrentPrice());
    assertEquals(true, result.getStatus());
  }

  /**
   * Tests the convertToEntity method with invalid date format to ensure it throws
   * an IllegalArgumentException.
   */
  @Test
  public void convertToEntity_ShouldThrowOnInvalidDateFormat() {
    // Arrange
    auctionDto.setStartDateString("invalid-date");
    Auction targetAuction = new Auction();

    // Act & Assert
    assertThrows(DateTimeParseException.class, 
        () -> AuctionFormatter.convertToEntity(auctionDto, jewel, targetAuction));
  }
}