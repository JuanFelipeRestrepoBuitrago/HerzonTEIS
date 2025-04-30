package com.eafit.herzon.teis.controllers.rest;

import com.eafit.herzon.teis.dto.ApiAuctionsResponse;
import com.eafit.herzon.teis.dto.AuctionDto;
import com.eafit.herzon.teis.dto.PaginationRequestDto;
import com.eafit.herzon.teis.models.Auction;  
import com.eafit.herzon.teis.services.AuctionService;
import com.eafit.herzon.teis.utils.AuctionFormatter;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling auction operations.
 */
@RestController
public class AuctionRestController {

  @Autowired
  private AuctionService auctionService;

  /**
   * Gets the list of auctions.
   *
   * @param request the request containing the pagination information
   * @return ResponseEntity with status
   */
  @PostMapping("/api/auctions")
  public ResponseEntity<ApiAuctionsResponse> getActiveAuctions(
      @Valid @RequestBody PaginationRequestDto request
  ) {
    try {
      Page<Auction> auctions = auctionService.getAllAuctions(
          request.getPage() - 1,
          request.getSize()
      );
      List<AuctionDto> auctionDtos = auctions.getContent()
          .stream()
          .map(AuctionFormatter::convertToDto)
          .toList();

      return ResponseEntity.ok()
          .body(new ApiAuctionsResponse(
              true,
              Collections.singletonList("¡Subastas obtenidas con éxito!"),
              auctionDtos,
              request.getPage(),
              request.getSize(),
              auctions.getTotalElements(),
              auctions.getTotalPages()
          ));
    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body(new ApiAuctionsResponse(
              false,
              Collections.singletonList("Ha ocurrido un error inesperado"),
              Collections.emptyList(),
              request.getPage(),
              request.getSize(),
              0,
              0
          ));
    }
  }
}
