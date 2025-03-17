package com.eafit.herzon.teis.dto;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for capturing form input when canceling or paying an order.
 */
public class OrderForm {
  /**
   * The id of the order.
   */
  @NotNull(message = "El Id de la orden es requerido")
  private Long id;

  /**
   * Get the order id.

   * @return The order id
   */
  public Long getId() {
    return id;
  }

  /**
   * Set the order id.

   * @param id The order id
   */
  public void setId(Long id) {
    this.id = id;
  }
}
