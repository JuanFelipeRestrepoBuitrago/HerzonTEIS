package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.dto.OrderForm;
import com.eafit.herzon.teis.exceptions.InvalidOrderException;
import com.eafit.herzon.teis.models.Order;
import com.eafit.herzon.teis.services.OrderService;
import jakarta.validation.Valid;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for handling auction operations.
 */
@Controller
@RequestMapping("/orders")
public class OrderController {

  @Autowired
  private OrderService orderService;

  /**
   * Method to get the orders view.

   * @return the orders view.
   */
  @GetMapping({"", "/"})
  public String getOrders(Model model) {
    model.addAttribute("title", "Ordenes - Herzon");
    model.addAttribute("orders", orderService.getAllOrders());

    return "orders/index";
  }

  /**
   * This method handles the requests for the order show page. 
   * It shows the details of a specific order.

   * @param id The id of the order to show
   * @param model The model object to pass data to the view
   * @return The name of the view to render
   */
  @GetMapping({"/{id}", "/{id}/"})
  public String show(@PathVariable String id, Model model) {
    long orderId = Long.parseLong(id);
    Order order = orderService.getOrderById(orderId);

    // If the offer does not exist redirect to the offers index
    if (order == null) {
      return "redirect:/orders";
    }

    model.addAttribute("title", "Orden " + order.getId() + " - Herzone");
    model.addAttribute("order", order);
    model.addAttribute("orderForm", new OrderForm());

    return "orders/show";
  }

  /**
   * This method handles the requests for the order payment page.

    * @param orderForm The form data for the order payment
    * @param result The result of the form validation
    * @param model The model object to pass data to the
    * @return The name of the view to render
    */
  @PostMapping({"/order/pay", "/order/pay/"})
  public String pay(
      @Valid @ModelAttribute("orderForm") OrderForm orderForm, 
      BindingResult result, Model model, RedirectAttributes redirectAttributes
  ) {
    try {
      orderService.submit(orderForm.getId());
      redirectAttributes.addFlashAttribute("messages",
          Collections.singletonList("Pago realizado exitosamente"));
      redirectAttributes.addFlashAttribute("error", false);
    } catch (InvalidOrderException e) {
      redirectAttributes.addFlashAttribute("messages", 
          Collections.singletonList(e.getMessage()));
      redirectAttributes.addFlashAttribute("error", true);
    }

    return "redirect:/orders/" + orderForm.getId();
  }
}
