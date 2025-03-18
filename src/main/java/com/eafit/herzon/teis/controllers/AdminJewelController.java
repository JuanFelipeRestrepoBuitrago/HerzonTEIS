package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.models.Jewel;
import com.eafit.herzon.teis.services.JewelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for managing Jewel entities by ADMIN users.
 */
@Controller
@RequestMapping("/admin/jewels")
@PreAuthorize("hasRole('ADMIN')")
public class AdminJewelController {

  @Autowired
  private final JewelService jewelService;

  /**
   * Constructs a new AdminJewelController with the specified JewelService.
   *
   * @param jewelService the service to manage jewel operations
   */
  @Autowired
  public AdminJewelController(JewelService jewelService) {
    this.jewelService = jewelService;
  }

  /**
   * Displays the jewel management page with pagination.
   *
   * @param page the page number (default 0)
   * @param size the number of items per page (default 9)
   * @param model the model to add attributes to the view
   * @return the admin jewels management page
   */
  @GetMapping
  public String manageJewels(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "9") int size,
      Model model) {
    Page<Jewel> jewelPage = jewelService.getAllJewels(page, size);
    model.addAttribute("jewels", jewelPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", jewelPage.getTotalPages());
    return "admin/jewels";
  }

  /**
   * Displays the form to create a new jewel.
   *
   * @param model the model to add attributes to the view
   * @return the form view for creating a jewel
   */
  @GetMapping("/create")
  public String showCreateForm(Model model) {
    model.addAttribute("jewel", new Jewel());
    return "admin/jewel-form";
  }

  /**
   * Handles the creation of a new jewel.
   *
   * @param jewel the jewel object submitted from the form
   * @return redirects to the jewel management page
   */
  @PostMapping("/save")
  public String saveJewel(@ModelAttribute Jewel jewel) {
    jewel.setImageUrl(
        "https://cdn-media.glamira.com/media/product/newgeneration/view/1/sku/15549gisu/"
        + "diamond/emerald_AA/stone2/diamond-Brillant_AAA/stone3/diamond-Brillant_AAA/"
        + "alloycolour/yellow.jpg");
    jewelService.saveJewel(jewel);
    return "redirect:/admin/jewels";
  }

  /**
   * Displays the form to edit an existing jewel.
   *
   * @param id the ID of the jewel to edit
   * @param model the model to add attributes to the view
   * @return the form view for editing a jewel
   */
  @GetMapping("/edit/{id}")
  public String showEditForm(@PathVariable Long id, Model model) {
    Jewel jewel = jewelService.getJewelById(id);
    model.addAttribute("jewel", jewel);
    return "admin/jewel-form";
  }

  /**
   * Deletes a jewel.
   *
   * @param id the ID of the jewel to delete
   * @return redirects to the jewel management page
   */
  @PostMapping("/delete/{id}")
  public String deleteJewel(@PathVariable Long id) {
    jewelService.deleteJewel(id);
    return "redirect:/admin/jewels";
  }
}