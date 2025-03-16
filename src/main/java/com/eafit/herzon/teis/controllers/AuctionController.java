package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.models.Auction;
import com.eafit.herzon.teis.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling auction operations.
 */
@Controller
@RequestMapping("/auctions")
public class AuctionController {

  @Autowired
  private AuctionService auctionService;

  /**
   * Method to get the auctions view.

   * @return the auctions view.
   */
  @GetMapping
  public String getAuctions(Model model) {
    model.addAttribute("title", "Subastas - Herzon");
    model.addAttribute("auctions", auctionService.getAllActiveAuctions());

    return "auctions/index";
  }

  /**
   * This method handles the requests for the auction show page. 
   * It shows the details of a specific auction.

   * @param id The id of the auction to show
   * @param model The model object to pass data to the
   * @return The name of the view to render
   */
  @GetMapping("/{id}")
  public String show(@PathVariable String id, Model model) {

    long auctionId = Long.parseLong(id);
    Auction auction = auctionService.getAuctionById(auctionId);

    // If the offer does not exist redirect to the offers index
    if (auction == null) {
      return "redirect:/auctions";
    }

    model.addAttribute("title", "Subasta " + auction.getId() + " - Herzone");
    model.addAttribute("auction", auction);

    return "auctions/show";
  }
}
