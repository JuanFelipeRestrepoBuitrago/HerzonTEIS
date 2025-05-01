package com.eafit.herzon.teis.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Formatter utility class.
 */
@ExtendWith(MockitoExtension.class)
public class FormatterTest {

  /**
   * Tests the formatCurrency method with a positive number and two minimum decimal places.
   */
  @Test
  public void formatCurrency_ShouldFormatPositiveNumberWithTwoDecimals() {
    // Arrange
    double number = 1234.5678;
    int minimumFractionDigits = 2;

    // Act
    String result = Formatter.formatCurrency(number, minimumFractionDigits);

    // Assert
    assertEquals("$1,234.5678", result);
  }

  /**
   * Tests the formatCurrency method with a positive number and two minimum decimal places
   * to complete. 
   */
  @Test
  public void formatCurrency_ShouldFormatPositiveNumberWithTwoDecimalsToComplete() {
    // Arrange
    double number = 1235;
    int minimumFractionDigits = 2;

    // Act
    String result = Formatter.formatCurrency(number, minimumFractionDigits);

    // Assert
    assertEquals("$1,235.00", result);
  }

  /**
   * Tests the formatCurrency method with a negative number and three minimum decimal places.
   */
  @Test
  public void formatCurrency_ShouldFormatNegativeNumberWithThreeDecimals() {
    // Arrange
    double number = -987.654321;
    int minimumFractionDigits = 3;

    // Act
    String result = Formatter.formatCurrency(number, minimumFractionDigits);

    // Assert
    assertEquals("-$987.654321", result);
  }

  /**
   * Tests the formatCurrency method with a negative number and three minimum decimal places
   * to complete. 
   */
  @Test
  public void formatCurrency_ShouldFormatNegativeNumberWithThreeDecimalsToComplete() {
    // Arrange
    double number = -987.6;
    int minimumFractionDigits = 3;

    // Act
    String result = Formatter.formatCurrency(number, minimumFractionDigits);

    // Assert
    assertEquals("-$987.600", result);
  }

  /**
   * Tests the formatCurrency method with zero and one minimum decimal place.
   */
  @Test
  public void formatCurrency_ShouldFormatZeroWithOneDecimal() {
    // Arrange
    double number = 0.0;
    int minimumFractionDigits = 1;

    // Act
    String result = Formatter.formatCurrency(number, minimumFractionDigits);

    // Assert
    assertEquals("$0.0", result);
  }
}