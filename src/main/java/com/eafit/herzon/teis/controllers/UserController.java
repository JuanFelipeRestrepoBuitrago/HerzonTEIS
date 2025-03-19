package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.dto.UserForm;
import com.eafit.herzon.teis.exceptions.InvalidUserException;
import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    model.addAttribute("userForm", new UserForm());
    return "users/register"; // Coincide con templates/users/register.html
  }

  /**
   * Handles user registration requests from the form.
   *
   * @param userForm           the user data from the form
   * @param redirectAttributes attributes for passing flash messages
   * @return a redirect to the login page on success, or back to register on
   *         failure
   */
  @PostMapping("/api/users/register")
  public String registerUser(
      @Valid @ModelAttribute("userForm") UserForm userForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      // Add all errors as a single flash attribute separated by a space
      redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors().stream()
          .map(error -> error.getDefaultMessage()).reduce("Errores: ", (a, b) -> a + " " + b));
      return "redirect:/register";
    }

    try {
      userService.register(userForm);
      redirectAttributes.addFlashAttribute("success",
          "¡Usuario registrado correctamente! Inicia sesión.");
      return "redirect:/login";
    } catch (InvalidUserException e) {
      redirectAttributes.addFlashAttribute("error", e.getMessage());
      return "redirect:/register";
    }
  }

  /**
   * Displays the home page.
   *
   * @return the name of the Thymeleaf template for the home page
   */
  @GetMapping("/home")
  public String getHomePage() {
    return "home/index"; // Coincide con templates/home.html
  }

  /**
   * Displays the dashboard page (accessible to ADMIN role).
   *
   * @return the name of the Thymeleaf template for the dashboard page
   */
  @GetMapping("/users/dashboard")
  public String getDashboardPage() {
    return "users/dashboard"; // Coincide con templates/users/dashboard.html
  }
}