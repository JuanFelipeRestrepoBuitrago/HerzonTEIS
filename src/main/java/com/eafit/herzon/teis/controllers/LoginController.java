package com.eafit.herzon.teis.controllers;

import jakarta.servlet.http.HttpSession;
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
   * @param session the HttpSession to retrieve error messages
   * @return the name of the Thymeleaf template for the login page
   */
  @GetMapping("/login")
  public String getLoginPage(Model model, HttpSession session) {
    // Transfer error message from session to model
    String error = (String) session.getAttribute("error");
    if (error != null) {
      model.addAttribute("error", error);
      session.removeAttribute("error"); // Limpiar la sesión después de transferir
    }
    return "users/login"; // Coincide con templates/users/login.html
  }
}