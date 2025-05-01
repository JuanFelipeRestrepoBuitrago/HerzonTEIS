package com.eafit.herzon.teis.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/metals")
public class MetalPriceController {

    @GetMapping("/prices")
    public ResponseEntity<?> getMetalPrices() {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.goldapi.io/api/XAU,XAG/USD"))
                .header("x-access-token", "goldapi-2c7qesma4q03is-io")
                .header("Content-Type", "application/json")
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return ResponseEntity.ok(response.body());

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching metal prices: " + e.getMessage());
        }
    }
}
