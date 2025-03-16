package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.services.JewelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling view requests related to jewels.
 */
@Controller
@RequestMapping("/admin/jewels")
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
   * Displays the list of all jewels.
   *
   *
   * @param model the model to add attributes to
   * @return the name of the Thymeleaf template for displaying jewels
   */
  @GetMapping
  public String listJewels(Model model) {
    model.addAttribute("jewels", jewelService.getAllJewels());
    return "jewels/list";
  }
}