package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.exceptions.JewelNotFoundException;
import com.eafit.herzon.teis.models.Jewel;
import com.eafit.herzon.teis.repositories.JewelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing jewel entities.
 */
@Service
public class JewelService {

  private final JewelRepository jewelRepository;

  /**
   * Constructs a new JewelService with the required repository.
   *
   * @param jewelRepository the repository for jewel data access
   */
  @Autowired
  public JewelService(JewelRepository jewelRepository) {
    this.jewelRepository = jewelRepository;
  }

  /**
   * Saves a new jewel or updates an existing one.
   *
   * @param jewel the jewel to save
   * @return the saved jewel
   * @throws IllegalArgumentException if the jewel is null
   */
  @Transactional
  public Jewel saveJewel(Jewel jewel) {
    if (jewel == null) {
      throw new IllegalArgumentException("Jewel cannot be null");
    }
    return jewelRepository.save(jewel);
  }

  /**
   * Retrieves all jewels from the database with pagination.
   *
   * @param page the page number (0-based)
   * @param size the number of items per page
   * @return a page of all jewels
   */
  @Transactional(readOnly = true)
  public Page<Jewel> getAllJewels(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return jewelRepository.findAll(pageable);
  }

  /**
   * Finds a jewel by its ID.
   *
   * @param id the ID of the jewel to find
   * @return the jewel if found
   * @throws IllegalArgumentException if the ID is null or negative
   * @throws JewelNotFoundException if no jewel is found with the given ID
   */
  @Transactional(readOnly = true)
  public Jewel getJewelById(Long id) {
    validateId(id);
    return jewelRepository.findById(id)
        .orElseThrow(() -> new JewelNotFoundException("Jewel not found with ID: " + id));
  }

  /**
   * Deletes a jewel by its ID.
   *
   * @param id the ID of the jewel to delete
   * @throws IllegalArgumentException if the ID is null or negative
   * @throws JewelNotFoundException if no jewel is found with the given ID
   */
  @Transactional
  public void deleteJewel(Long id) {
    validateId(id);
    if (!jewelRepository.existsById(id)) {
      throw new JewelNotFoundException("Jewel not found with ID: " + id);
    }
    jewelRepository.deleteById(id);
  }

  /**
   * Searches for jewels by name with pagination.
   *
   * @param name the name to search for
   * @param page the page number (0-based)
   * @param size the number of items per page
   * @return a page of matching jewels
   * @throws IllegalArgumentException if the name is null or empty
   */
  @Transactional(readOnly = true)
  public Page<Jewel> findJewelsByName(String name, int page, int size) {
    validateString(name, "Name");
    Pageable pageable = PageRequest.of(page, size);
    return jewelRepository.searchJewelByName(name, pageable);
  }

  /**
   * Searches for jewels by category with pagination.
   *
   * @param category the category to search for
   * @param page the page number (0-based)
   * @param size the number of items per page
   * @return a page of matching jewels
   * @throws IllegalArgumentException if the category is null or empty
   */
  @Transactional(readOnly = true)
  public Page<Jewel> findJewelsByCategory(String category, int page, int size) {
    validateString(category, "Category");
    Pageable pageable = PageRequest.of(page, size);
    return jewelRepository.searchJewelByCategory(category, pageable);
  }

  /**
   * Searches for jewels containing a keyword in their details with pagination.
   *
   * @param keyword the keyword to search for
   * @param page the page number (0-based)
   * @param size the number of items per page
   * @return a page of matching jewels
   * @throws IllegalArgumentException if the keyword is null or empty
   */
  @Transactional(readOnly = true)
  public Page<Jewel> findJewelsByKeyword(String keyword, int page, int size) {
    validateString(keyword, "Keyword");
    Pageable pageable = PageRequest.of(page, size);
    return jewelRepository.searchJewelByKeyword(keyword, pageable);
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