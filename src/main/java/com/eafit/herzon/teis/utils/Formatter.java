package com.eafit.herzon.teis.utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Utility class to format numbers.
 */
public class Formatter {
  /**
   * Method to format a number as a currency.
   *
   * @param number The number to format.
   * @return The number formatted as a currency.
   */
  public static String formatCurrency(double number) {
    NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
    formatter.setMinimumFractionDigits(2);
    formatter.setMaximumFractionDigits(2);
    return formatter.format(number);
  }
}