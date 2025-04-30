package com.eafit.herzon.teis.dto;

import jakarta.validation.constraints.Positive;

/**
 * DTO for capturing form input when pagination needed.
 */
public class PaginationRequestDto {

  /**
   * The page number to retrieve. With default value 1.
   */
  @Positive(message = "El número de página debe ser positivo")
  private int page = 1;

  /**
   * The number of items per page. With default value 10.
   */
  @Positive(message = "El tamaño de la página debe ser positivo")
  private int size = 10;

  /**
   * Get the page number.
   *
   * @return The page number
   */
  public int getPage() {
    return page;
  }

  /**
   * Set the page number.
   *
   * @param page The page number
   */
  public void setPage(int page) {
    this.page = page;
  }

  /**
   * Get the size of the page.
   *
   * @return The size of the page
   */
  public int getSize() {
    return size;
  }

  /**
   * Set the size of the page.
   *
   * @param size The size of the page
   */
  public void setSize(int size) {
    this.size = size;
  }
}
