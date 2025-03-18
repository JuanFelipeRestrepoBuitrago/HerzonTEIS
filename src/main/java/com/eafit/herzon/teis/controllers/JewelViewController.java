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
 * Controller for handling views related to jewels (public access).
 */
@Controller
public class JewelViewController {

  @Autowired
  private final JewelService jewelService;

  /**
   * Constructs a new JewelViewController with the specified JewelService.
   *
   * @param jewelService the service to manage jewel operations
   */
  @Autowired
  public JewelViewController(JewelService jewelService) {
    this.jewelService = jewelService;
  }

  /**
   * Displays the list of jewels with pagination.
   *
   * @param page the page number (default 0)
   * @param size the number of items per page (default 9)
   * @param model the model to pass attributes to the view
   * @return the name of the view template ("jewels/jewels")
   */
  @GetMapping("/jewels")
  public String listJewels(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "9") int size,
      Model model) {
    Page<Jewel> jewelPage = jewelService.getAllJewels(page, size);
    System.out.println("Total jewels found: " + jewelPage.getTotalElements());
    jewelPage.getContent().forEach(jewel -> {
      String message = "Jewel: " + jewel.getName() + ", Category: " + jewel.getCategory();
      System.out.println(message);
    });
    model.addAttribute("jewels", jewelPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", jewelPage.getTotalPages());
    model.addAttribute("categories", jewelService.getAllCategories());
    return "jewels/jewels";
  }

  /**
   * Handles filtering and searching of jewels via page reload.
   *
   * @param search the search term for jewel names (optional)
   * @param category the category to filter by (optional)
   * @param price the price filter ("high" or "low", optional)
   * @param sort the sorting criteria ("name" or "price", optional)
   * @param page the page number (default 0)
   * @param size the number of items per page (default 9)
   * @param model the model to pass attributes to the view
   * @return the name of the view template ("jewels/jewels")
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
    System.out.println("Filtering with search: " + search + ", category: " + category);
    System.out.println("price: " + price + ", sort: " + sort);

    search = search != null && !search.trim().isEmpty()
        ? search.trim().toUpperCase()
        : null;
    category = category != null && !category.trim().isEmpty()
        ? category.trim().toUpperCase()
        : null;
    price = price != null && !price.trim().isEmpty()
        ? price.trim().toLowerCase()
        : null;
    sort = sort != null && !sort.trim().isEmpty()
        ? sort.trim().toLowerCase()
        : null;

    Page<Jewel> filteredJewels = jewelService.filterJewels(
        search, category, price, sort, page, size);
    System.out.println("Filtered jewels found: " + filteredJewels.getTotalElements());
    filteredJewels.getContent().forEach(jewel -> {
      String part1 = "Filtered Jewel: " + jewel.getName();
      String part2 = ", Category: " + jewel.getCategory();
      System.out.println(part1 + part2);
    });

    model.addAttribute("jewels", filteredJewels.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", filteredJewels.getTotalPages());
    model.addAttribute("categories", jewelService.getAllCategories());
    return "jewels/jewels"; 
  }

  /**
   * Displays the details page for a specific jewel.
   *
   * @param id the ID of the jewel to display
   * @param model the model to pass attributes to the view
   * @return the name of the view template ("jewels/details")
   * @throws IllegalArgumentException if the ID is invalid
   */
  @GetMapping("/jewels/details/{id}")
  public String showJewelDetails(@PathVariable Long id, Model model) {
    Jewel jewel = jewelService.getJewelById(id);
    model.addAttribute("jewel", jewel);
    return "jewels/details";
  }
}