package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.models.CreditCard;
import com.eafit.herzon.teis.repositories.CreditCardRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing CreditCard entities.
 */
@Service
public class CreditCardService {

  private final CreditCardRepository creditCardRepository;

  /**
   * Constructs a new CreditCardService with the required repository.
   *
   * @param creditCardRepository the repository for credit card data access
   */
  @Autowired
  public CreditCardService(CreditCardRepository creditCardRepository) {
    this.creditCardRepository = creditCardRepository;
  }

  /**
   * Saves a new credit card or updates an existing one.
   *
   * @param creditCard the credit card to save
   * @return the saved credit card
   * @throws IllegalArgumentException if the credit card is null
   */
  @Transactional
  public CreditCard saveCreditCard(CreditCard creditCard) {
    if (creditCard == null) {
      throw new IllegalArgumentException("Credit card cannot be null");
    }
    return creditCardRepository.save(creditCard);
  }

  /**
   * Retrieves all credit cards from the database.
   *
   * @return a list of all credit cards
   */
  @Transactional(readOnly = true)
  public List<CreditCard> getAllCreditCards() {
    return creditCardRepository.findAll();
  }

  /**
   * Finds a credit card by its ID.
   *
   * @param id the ID of the credit card to find
   * @return the credit card if found
   * @throws IllegalArgumentException if the ID is null or negative
   * @throws CreditCardNotFoundException if no credit card is found with the given ID
   */
  @Transactional(readOnly = true)
  public CreditCard getCreditCardById(Long id) {
    validateId(id);
    return creditCardRepository.findById(id)
        .orElseThrow(() -> new CreditCardNotFoundException("Credit card not found with ID: " + id));
  }

  /**
   * Deletes a credit card by its ID.
   *
   * @param id the ID of the credit card to delete
   * @throws IllegalArgumentException if the ID is null or negative
   * @throws CreditCardNotFoundException if no credit card is found with the given ID
   */
  @Transactional
  public void deleteCreditCard(Long id) {
    validateId(id);
    if (!creditCardRepository.existsById(id)) {
      throw new CreditCardNotFoundException("Credit card not found with ID: " + id);
    }
    creditCardRepository.deleteById(id);
  }

  /**
   * Finds a credit card by its card number.
   *
   * @param cardNumber the card number to search for
   * @return the credit card if found
   * @throws IllegalArgumentException if the card number is null or empty
   * @throws CreditCardNotFoundException if no credit card is found with the given number
   */
  @Transactional(readOnly = true)
  public CreditCard getCreditCardByNumber(String cardNumber) {
    validateString(cardNumber, "Card number");
    return creditCardRepository.findByCardNumber(cardNumber)
        .orElseThrow(() -> new CreditCardNotFoundException(
            "Credit card not found with number: " + cardNumber));
  }

  /**
   * Validates that the provided ID is not null or negative.
   *
   * @param id the ID to validate
   * @throws IllegalArgumentException if the ID is invalid
   */
  private void validateId(Long id) {
    if (id == null || id <= 0) {
      throw new IllegalArgumentException("ID must be a positive non-null value");
    }
  }

  /**
   * Validates that the provided string is not null or empty.
   *
   * @param value the string to validate
   * @param fieldName the name of the field being validated (for error messaging)
   * @throws IllegalArgumentException if the string is invalid
   */
  private void validateString(String value, String fieldName) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(fieldName + " cannot be null or empty");
    }
  }
}