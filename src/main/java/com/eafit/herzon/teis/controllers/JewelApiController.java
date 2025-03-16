package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.models.Jewel;
import com.eafit.herzon.teis.services.JewelService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling API requests related to jewels.
 */
@RestController
@RequestMapping("/admin/jewels")
public class JewelApiController {

  private final JewelService jewelService;

  /**
   * Constructs a new JewelApiController with the required service.
   *
   * @param jewelService the service for jewel operations
   */
  @Autowired
  public JewelApiController(JewelService jewelService) {
    this.jewelService = jewelService;
  }

  /**
   * Retrieves a jewel by its ID.
   *
   * @param id the ID of the jewel to retrieve
   * @return a ResponseEntity containing the jewel if found
   */
  @GetMapping("/{id}")
  public ResponseEntity<Jewel> getJewelById(@PathVariable Long id) {
    Jewel jewel = jewelService.getJewelById(id);
    return ResponseEntity.ok(jewel);
  }

  /**
   * Creates a new jewel.
   *
   * @param jewel the jewel object to create
   * @return a ResponseEntity containing the created jewel
   */
  @PostMapping
  public ResponseEntity<Jewel> createJewel(@RequestBody Jewel jewel) {
    Jewel savedJewel = jewelService.saveJewel(jewel);
    return ResponseEntity.ok(savedJewel);
  }

  /**
   * Deletes a jewel by its ID.
   *
   * @param id the ID of the jewel to delete
   * @return a ResponseEntity indicating success
   */
  @PostMapping("/delete/{id}")
  public ResponseEntity<Void> deleteJewel(@PathVariable Long id) {
    jewelService.deleteJewel(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Searches for jewels by name with pagination.
   *
   * @param name the name to search for
   * @param page the page number (default 0)
   * @param size the number of items per page (default 9)
   * @return a ResponseEntity containing the page of matching jewels
   */
  @GetMapping("/search")
  public ResponseEntity<Page<Jewel>> searchJewelsByName(
      @RequestParam String name,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "9") int size) {
    Page<Jewel> jewelPage = jewelService.findJewelsByName(name, page, size);
    return ResponseEntity.ok(jewelPage);
  }
}