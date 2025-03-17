package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for managing user-related views accessible only to administrators.
 * Handles requests under the '/admin/users' endpoint and enforces ADMIN role
 * access.
 * Complies with the requirement to limit business logic and return Thymeleaf
 * views.
 */
@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

  @Autowired
  private UserService userService;

  /**
   * Retrieves and displays a list of all users to the admin.
   * Sensitive information (e.g., passwords) is excluded by the service layer.
   *
   * @param model the Spring Model object to pass data to the Thymeleaf view
   * @return the name of the Thymeleaf template ('admin/users') to render
   */
  @GetMapping
  public String listUsers(Model model) {
    List<CustomUser> users = userService.getAllUsers();
    model.addAttribute("users", users);
    return "admin/users";
  }
}