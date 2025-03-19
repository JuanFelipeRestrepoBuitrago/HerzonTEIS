package com.eafit.herzon.teis.utils;

import com.eafit.herzon.teis.dto.AuctionDto;
import com.eafit.herzon.teis.models.Auction;
import com.eafit.herzon.teis.models.Jewel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class to format auctions between DTOs and entities.
 */
public class AuctionFormatter { // Fixed class name spelling

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
      .ofPattern("yyyy-MM-dd'T'HH:mm");

  /**
   * Converts AuctionDto to Auction entity.
   *
   * @param dto   the auction DTO to convert
   * @param jewel the jewel entity to associate with the auction
   * @param auction the auction entity to update
   * @return the auction entity
   */
  public static Auction convertToEntity(AuctionDto dto, Jewel jewel, Auction auction) {
    // Convert date strings to LocalDateTime
    LocalDateTime startDate = LocalDateTime.parse(
        dto.getStartDateString(), DATE_FORMATTER);
    LocalDateTime endDate = LocalDateTime.parse(
        dto.getEndDateString(), DATE_FORMATTER);
    
    auction.setJewel(jewel);
    auction.setStartDate(startDate);
    auction.setEndDate(endDate);
    auction.setStartPrice(dto.getStartPrice());
    auction.setCurrentPrice(dto.getCurrentPrice());
    auction.setStatus(dto.getStatus());

    return auction;
  }

  /**
   * Converts Auction entity to AuctionDto.
   *
   * @param auction the auction entity to convert
   * @return the auction DTO
   */
  public static AuctionDto convertToDto(Auction auction) {
    AuctionDto dto = new AuctionDto();

    dto.setAuctionId(auction.getId());
    dto.setJewelId(auction.getJewel().getId());
    dto.setStartDateString(auction.getStartDate().format(DATE_FORMATTER));
    dto.setEndDateString(auction.getEndDate().format(DATE_FORMATTER));
    dto.setStartPrice(auction.getStartPrice());
    dto.setCurrentPrice(auction.getCurrentPrice());
    dto.setStatus(auction.getStatus());

    return dto;
  }
}