package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.models.CreditCard;
import com.eafit.herzon.teis.services.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling CreditCard-related HTTP requests.
 */
@Controller
@RequestMapping("/admin/credit-cards")
public class CreditCardController {

  private final CreditCardService creditCardService;

  /**
   * Constructs a new CreditCardController with the required service.
   *
   * @param creditCardService the service for credit card operations
   */
  @Autowired
  public CreditCardController(CreditCardService creditCardService) {
    this.creditCardService = creditCardService;
  }

  /**
   * Displays the list of all credit cards.
   *
   * @param model the model to add attributes to
   * @return the Thymeleaf template for displaying credit cards
   */
  @GetMapping
  public String listCreditCards(Model model) {
    model.addAttribute("creditCards", creditCardService.getAllCreditCards());
    return "credit-cards/list";
  }

  /**
   * Retrieves a credit card by its ID.
   *
   * @param id the ID of the credit card to retrieve
   * @return ResponseEntity containing the credit card if found
   */
  @GetMapping("/{id}")
  public ResponseEntity<CreditCard> getCreditCardById(@PathVariable Long id) {
    CreditCard creditCard = creditCardService.getCreditCardById(id);
    return ResponseEntity.ok(creditCard);
  }

  /**
   * Creates a new credit card.
   *
   * @param creditCard the credit card object to be saved
   * @return ResponseEntity containing the saved credit card
   */
  @PostMapping
  public ResponseEntity<CreditCard> createCreditCard(@RequestBody CreditCard creditCard) {
    CreditCard savedCreditCard = creditCardService.saveCreditCard(creditCard);
    return ResponseEntity.ok(savedCreditCard);
  }

  /**
   * Deletes a credit card by its ID.
   *
   * @param id the ID of the credit card to delete
   * @return ResponseEntity indicating success
   */
  @PostMapping("/delete/{id}")
  public ResponseEntity<Void> deleteCreditCard(@PathVariable Long id) {
    creditCardService.deleteCreditCard(id);
    return ResponseEntity.noContent().build();
  }
}