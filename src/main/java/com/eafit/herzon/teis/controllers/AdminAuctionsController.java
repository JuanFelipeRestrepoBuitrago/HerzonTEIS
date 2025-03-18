package com.eafit.herzon.teis.controllers;

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
    // model.addAttribute("jewels", jewelPage.getContent());
    model.addAttribute("currentPage", page);
    // model.addAttribute("totalPages", jewelPage.getTotalPages());
    return "admin/jewels";
  }

}
