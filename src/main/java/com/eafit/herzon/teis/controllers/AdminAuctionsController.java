package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.models.Auction;
import com.eafit.herzon.teis.services.AuctionService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for the admin auctions dashboard.
 */
@Controller
@RequestMapping("/admin/auctions")
public class AdminAuctionsController {

  private final AuctionService auctionService;

  /**
   * Constructs a new AdminAuctionsController with the specified AuctionService.
   *
   * @param auctionService the service to manage auction operations
   */
  public AdminAuctionsController(AuctionService auctionService) {
    this.auctionService = auctionService;
  }

  /**
   * Displays the jewel management page with pagination.
   *
   * @param page  the page number (default 0)
   * @param size  the number of items per page (default 9)
   * @param model the model to add attributes to the view
   * @return the admin jewels management page
   */
  @GetMapping({"", "/"})
  public String manageJewels(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "9") int size,
      Model model) {
    Page<Auction> auctionPage = auctionService.getAllAuctions(page, size);
    model.addAttribute("auctions", auctionPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", auctionPage.getTotalPages());
    model.addAttribute("title", "Subastas - Admin Herzon");
    return "admin/auctions/list";
  }

}
