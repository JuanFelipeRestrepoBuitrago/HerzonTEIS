package com.eafit.herzon.teis.controllers.rest;

import com.eafit.herzon.teis.dto.ApiMessagesResponse;
import com.eafit.herzon.teis.dto.OfferForm;
import com.eafit.herzon.teis.exceptions.InvalidOfferException;
import com.eafit.herzon.teis.models.User;
import com.eafit.herzon.teis.services.OfferService;
import jakarta.validation.Valid;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
   * 
   * @param request Offer request DTO
   * @param user    The authenticated user
   * @return ResponseEntity with status
   */
  @PostMapping("/auction/offer/place")
  public ResponseEntity<ApiMessagesResponse> placeOffer(
      @Valid @RequestBody OfferForm request,
      @AuthenticationPrincipal User user
  ) {
    if (user == null) {
      return ResponseEntity.status(401)
          .body(new ApiMessagesResponse(true, Collections.singletonList("No autorizado: Por favor, inicie sesión")));
    }
    try {
      offerService.placeOffer(request.getOfferPrice(), request.getAuctionId(), user);
      return ResponseEntity.ok()
          .body(new ApiMessagesResponse(
              false, Collections.singletonList("¡Oferta pujada con éxito!")));

    } catch (InvalidOfferException e) {
      return ResponseEntity.badRequest()
          .body(new ApiMessagesResponse(true, Collections.singletonList(e.getMessage())));

    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body(new ApiMessagesResponse(
              true, Collections.singletonList("Ha ocurrido un error inesperado")));
    }
  }
}
