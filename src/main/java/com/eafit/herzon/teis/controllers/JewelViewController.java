package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.models.Jewel;
import com.eafit.herzon.teis.services.JewelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for handling jewel-related views.
 */
@Controller
public class JewelViewController {

  private final JewelService jewelService;

  /**
   * Constructs a new JewelViewController with the specified service.
   *
   * @param jewelService the service for jewel operations
   */
  @Autowired
  public JewelViewController(JewelService jewelService) {
    this.jewelService = jewelService;
  }

  /**
   * Displays a paginated list of all jewels.
   *
   * @param page  the page number (default is 0)
   * @param size  the number of items per page (default is 9)
   * @param model the model to add attributes for the view
   * @return the view name "jewels/jewels"
   */
  @GetMapping("/jewels")
  public String listJewels(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "9") int size,
      Model model) {
    Page<Jewel> jewelPage = jewelService.getAllJewels(page, size);
    model.addAttribute("jewels", jewelPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", jewelPage.getTotalPages());
    model.addAttribute("categories", jewelService.getAllCategories());
    return "jewels/jewels";
  }

  /**
   * Displays a paginated list of filtered jewels.
   *
   * @param search    the search term for jewel names (optional)
   * @param category  the category to filter by (optional)
   * @param price     the price sorting criteria ("high" or "low", optional)
   * @param sort      the sorting criteria ("name" or "price", optional)
   * @param page      the page number (default is 0)
   * @param size      the number of items per page (default is 9)
   * @param model     the model to add attributes for the view
   * @return the view name "jewels/jewels"
   */
  @GetMapping("/jewels/filter")
  public String filterJewels(
      @RequestParam(required = false) String search,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) String price,
      @RequestParam(required = false) String sort,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "9") int size,
      Model model) {

    Page<Jewel> filteredJewels = jewelService.filterJewels(
        search, category, price, sort, page, size);
    model.addAttribute("jewels", filteredJewels.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", filteredJewels.getTotalPages());
    model.addAttribute("categories", jewelService.getAllCategories());
    return "jewels/jewels";
  }

  /**
   * Displays details of a specific jewel.
   *
   * @param id    the ID of the jewel to display
   * @param model the model to add attributes for the view
   * @return the view name "jewels/details"
   */
  @GetMapping("/jewels/details/{id}")
  public String showJewelDetails(@PathVariable Long id, Model model) {
    Jewel jewel = jewelService.getJewelById(id);
    model.addAttribute("jewel", jewel);
    return "jewels/details";
  }
}