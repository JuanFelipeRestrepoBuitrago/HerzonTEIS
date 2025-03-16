package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.models.User;
import com.eafit.herzon.teis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for handling user-related requests.
 */
@Controller
public class UserController {

  @Autowired
  private UserService userService;

  /**
   * Displays the registration page.
   *
   * @param model the Model object for adding attributes to the view
   * @return the name of the Thymeleaf template for the registration page
   */
  @GetMapping("/register")
  public String getRegisterPage(Model model) {
    model.addAttribute("user", new User());
    return "users/register";
  }

  /**
   * Handles user registration requests from the form.
   *
   * @param user the user data from the form
   * @param redirectAttributes attributes for passing flash messages
   * @return a redirect to the login page on success, or back to register on failure
   */
  @PostMapping("/api/users/register")
  public String registerUser(@ModelAttribute User user,
      RedirectAttributes redirectAttributes) {
    try {
      userService.register(user);
      redirectAttributes.addFlashAttribute("success",
          "¡Usuario registrado correctamente! Inicia sesión.");
      return "redirect:/login";
    } catch (IllegalArgumentException e) {
      redirectAttributes.addFlashAttribute("error", e.getMessage());
      return "redirect:/register";
    }
  }
}