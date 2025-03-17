package com.eafit.herzon.teis.repositories;

import com.eafit.herzon.teis.models.CreditCard;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for CreditCard entity.
 */
@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

  /**
   * Finds a credit card by its card number.
   *
   * @param cardNumber the card number
   * @return an Optional containing the credit card entity if found, empty otherwise
   */
  Optional<CreditCard> findByCardNumber(String cardNumber);
}