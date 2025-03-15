package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    model.addAttribute("title", "Offers - Herzon");
    model.addAttribute("auctions", auctionService.getAllAuctions());

    return "auctions/index";
  }
}
