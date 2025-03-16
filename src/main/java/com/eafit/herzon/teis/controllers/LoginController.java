package com.eafit.herzon.teis.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling login-related requests.
 */
@Controller
public class LoginController {

  /**
   * Displays the login page.
   *
   * @param model the Model object for adding attributes to the view
   * @return the name of the Thymeleaf template for the login page
   */
  @GetMapping("/login")
  public String getLoginPage(Model model) {
    return "users/login"; // Especifica el directorio users/
  }
}