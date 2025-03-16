package com.eafit.herzon.teis.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling dashboard-related requests.
 */
@Controller
public class DashboardController {

  /**
   * Displays the dashboard page for authenticated users.
   *
   * @param model the Model object for adding attributes to the view
   * @return the name of the Thymeleaf template for the dashboard page
   */
  @GetMapping("/dashboard")
  public String getDashboardPage(Model model) {
    model.addAttribute("message", "Welcome to your Dashboard!");
    return "users/dashboard"; // Especifica el directorio users/
  }
}