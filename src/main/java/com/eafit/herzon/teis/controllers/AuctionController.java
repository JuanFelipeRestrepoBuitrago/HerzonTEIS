package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.exceptions.InvalidAuctionException;
import com.eafit.herzon.teis.models.Auction;
import com.eafit.herzon.teis.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
   *
   * @param page the page number (0-based).
   * @param size the number of items per page.
   * @param view the view type (default 'grid').
   * @param model the model to add attributes to.
   * @return the auctions view.
   */
  @GetMapping({"", "/"})
  public String getAuctions(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "9") int size,
      @RequestParam(defaultValue = "grid") String view, 
      Model model
  ) {
    Page<Auction> auctionPage = auctionService.getAllActiveAuctions(page, size);

    model.addAttribute("title", "Subastas - Herzon");
    model.addAttribute("auctions", auctionPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", auctionPage.getTotalPages());
    model.addAttribute("view", view);
    model.addAttribute("currentPageActive", "auctions");
    model.addAttribute("currentViewActive", view);

    return "auctions/index";
  }

  /**
   * Method to get the history auctions view.
   *
   * @param page the page number (0-based).
   * @param size the number of items per page.
   * @param view the view type (default 'grid').
   * @param model the model to add attributes to.
   * @return the auctions view.
   */
  @GetMapping({"/history", "/history/"})
  public String getHistoryAuctions(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "9") int size,
      @RequestParam(defaultValue = "grid") String view, 
      Model model
  ) {
    Page<Auction> auctionPage = auctionService.getAllInactiveAuctions(page, size);
    
    model.addAttribute("title", "Historial de Subastas - Herzon");
    model.addAttribute("auctions", auctionPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", auctionPage.getTotalPages());
    model.addAttribute("view", view);
    model.addAttribute("currentPageActive", "auctions_history");
    model.addAttribute("currentViewActive", view);

    return "auctions/index";
  }

  /**
   * This method handles the requests for the auction show page. 
   * It shows the details of a specific auction.
   *
   * @param id The id of the auction to show
   * @param model The model object to pass data to the
   * @return The name of the view to render
   */
  @GetMapping({"/{id}", "/{id}/"})
  public String show(@PathVariable String id, Model model) {
    long auctionId = Long.parseLong(id);
    
    try {
      Auction auction = auctionService.getAuctionById(auctionId);
      model.addAttribute("title", "Subasta " + auction.getId() + " - Herzone");
      model.addAttribute("auction", auction);
  
      return "auctions/show";
    } catch (InvalidAuctionException e) {
      return "redirect:/auctions";
    }
  }
}
