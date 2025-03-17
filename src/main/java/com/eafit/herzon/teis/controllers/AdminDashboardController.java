package com.eafit.herzon.teis.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for the admin dashboard.
 */
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

  /**
   * Displays the admin dashboard.
   *
   * @param model The model to pass data to the view.
   * @return The admin dashboard view.
   */
  @GetMapping
  public String showDashboard(Model model) {
    return "admin/dashboard";
  }
}