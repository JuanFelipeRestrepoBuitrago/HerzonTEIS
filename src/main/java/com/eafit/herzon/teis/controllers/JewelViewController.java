package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.models.Jewel;
import com.eafit.herzon.teis.services.JewelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for handling view requests related to jewels (public access).
 */
@Controller
@RequestMapping("/jewels")
public class JewelViewController {

  private final JewelService jewelService;

  /**
   * Constructs a new JewelViewController with the required service.
   *
   * @param jewelService the service for jewel operations
   */
  @Autowired
  public JewelViewController(JewelService jewelService) {
    this.jewelService = jewelService;
  }

  /**
   * Displays the list of all jewels with pagination and view options (accessible to all users).
   *
   * @param page the page number (default 0)
   * @param size the number of items per page (default 9)
   * @param view the view type (default 'grid')
   * @param model the model to add attributes to
   * @return the name of the Thymeleaf template for displaying jewels
   */
  @GetMapping
  public String listJewels(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "9") int size,
      @RequestParam(defaultValue = "grid") String view,
      Model model) {
    Page<Jewel> jewelPage = jewelService.getAllJewels(page, size);
    model.addAttribute("jewels", jewelPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", jewelPage.getTotalPages());
    model.addAttribute("view", view);
    model.addAttribute("currentPageActive", "jewels");
    model.addAttribute("currentViewActive", view);
    return "jewels/jewels";
  }

  /**
   * Displays the details of a specific jewel based on its ID.
   *
   * @param id the ID of the jewel to display
   * @param model the model to add attributes to
   * @return the name of the Thymeleaf template for displaying jewel details
   */
  @GetMapping("/details/{id}")
  public String viewJewelDetails(@PathVariable Long id, Model model) {
    Jewel jewel = jewelService.getJewelById(id);
    model.addAttribute("jewel", jewel);
    return "jewels/details";
  }
}