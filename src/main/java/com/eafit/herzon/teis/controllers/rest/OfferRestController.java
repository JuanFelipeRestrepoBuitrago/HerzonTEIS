package com.eafit.herzon.teis.controllers.rest;

import com.eafit.herzon.teis.dto.OfferForm;
import com.eafit.herzon.teis.exceptions.InvalidOfferException;
import com.eafit.herzon.teis.services.OfferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling auction offer operations.
 */
@RestController
public class OfferRestController {

  @Autowired
  private OfferService offerService;

  /**
   * Places a new offer for a specific auction.

   * @param request   Offer request DTO
   * @return ResponseEntity with status
   */
  @PostMapping("/auction/offer/place")
  public ResponseEntity<?> placeOffer(
      @Valid @RequestBody OfferForm request) {
    try {
      offerService.placeOffer(request.getOfferPrice(), request.getAuctionId());
      return ResponseEntity.ok().build();
    } catch (InvalidOfferException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("An error occurred while placing the offer");
    }
  }
}
