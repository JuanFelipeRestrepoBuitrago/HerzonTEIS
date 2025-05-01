package com.eafit.herzon.teis.controllers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to fetch and return metal prices from GoldAPI.
 */
@RestController
@RequestMapping("/api/metals")
public class MetalPriceController {

  @Value("${goldapi.access-token}")
  private String accessToken;

  /**
   * Fetches real-time metal price (e.g. XAU or XAG) from GoldAPI.
   *
   * @param metal metal symbol (default is XAU)
   * @return ResponseEntity with metal price JSON or error message
   */
  @GetMapping("/prices")
  public ResponseEntity<String> getMetalPrices(
      @RequestParam(defaultValue = "XAU") String metal) {

    try {
      String url = "https://www.goldapi.io/api/" + metal + "/USD";

      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(url))
          .header("x-access-token", accessToken)
          .header("Content-Type", "application/json")
          .build();

      HttpClient client = HttpClient.newHttpClient();
      HttpResponse<String> response = client.send(
          request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() != 200) {
        return ResponseEntity.status(response.statusCode())
            .body("{\"error\": \"GoldAPI responded with status: \""
                + response.statusCode()
                + ", \"body\": \""
                + response.body()
                + "\"}");
      }

      return ResponseEntity.ok(response.body());

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(500)
          .body("{\"error\": \"Error fetching metal price: "
              + e.getMessage()
              + "\"}");
    }
  }
}
