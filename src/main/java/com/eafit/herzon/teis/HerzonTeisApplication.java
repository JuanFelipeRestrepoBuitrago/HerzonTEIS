package com.eafit.herzon.teis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class of the application.
 */
@EnableScheduling
@SpringBootApplication
public class HerzonTeisApplication {

  /**
   * Main method of the application.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(HerzonTeisApplication.class, args);
  }
}