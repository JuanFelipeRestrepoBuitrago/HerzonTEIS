package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.models.Jewel;
import com.eafit.herzon.teis.repositories.JewelRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
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
   * Constructs a new JewelService with the specified JewelRepository.
   *
   * @param jewelRepository the repository to manage jewel data
   */
  public JewelService(JewelRepository jewelRepository) {
    this.jewelRepository = jewelRepository;
  }

  /**
   * Retrieves all jewels with pagination.
   *
   * @param page the page number (zero-based)
   * @param size the number of items per page
   * @return a page of jewels
   * @throws IllegalArgumentException if page or size is invalid
   */
  @Transactional(readOnly = true)
  public Page<Jewel> getAllJewels(int page, int size) {
    if (page < 0 || size <= 0) {
      throw new IllegalArgumentException("Page must be non-negative and size must be positive");
    }
    Pageable pageable = PageRequest.of(page, size);
    return jewelRepository.findAll(pageable);
  }

  /**
   * Gets all jewels in the database.
   *
   * @return a list of all jewels
   */
  @Transactional(readOnly = true)
  public List<Jewel> getAllJewels() {
    return jewelRepository.findAll();
  }

  /**
   * Retrieves all distinct categories.
   *
   * @return a list of distinct category names
   */
  @Transactional(readOnly = true)
  public List<String> getAllCategories() {
    return jewelRepository.findDistinctCategories();
  }

  /**
   * Filters jewels based on search, category, price, and sorting criteria.
   *
   * @param search   the search term for jewel names (can be null)
   * @param category the category to filter by (can be null)
   * @param price    the price filter ("high" or "low", can be null)
   * @param sort     the sorting criteria ("name" or "price", can be null)
   * @param page     the page number (zero-based)
   * @param size     the number of items per page
   * @return a page of filtered jewels
   * @throws IllegalArgumentException if page or size is invalid
   */
  @Transactional(readOnly = true)
  public Page<Jewel> filterJewels(
      String search,
      String category,
      String price,
      String sort,
      int page,
      int size) {
    if (page < 0 || size <= 0) {
      throw new IllegalArgumentException("Page must be non-negative and size must be positive");
    }

    // Normalizar los parámetros: convertir a mayúsculas y manejar valores vacíos
    search = search != null && !search.trim().isEmpty()
        ? "%" + search.trim().toUpperCase() + "%"
        : null;
    category = category != null && !category.trim().isEmpty()
        ? category.trim().toUpperCase()
        : null;
    price = price != null && !price.trim().isEmpty()
        ? price.trim().toLowerCase()
        : null;
    sort = sort != null && !sort.trim().isEmpty()
        ? sort.trim().toLowerCase()
        : null;

    Pageable pageable = PageRequest.of(page, size);
    return jewelRepository.findFilteredJewels(search, category, price, sort, pageable);
  }

  /**
   * Retrieves a jewel by its ID.
   *
   * @param id the ID of the jewel to retrieve
   * @return the jewel if found
   * @throws IllegalArgumentException if the ID is null or invalid
   * @throws EntityNotFoundException  if the jewel is not found
   */
  @Transactional(readOnly = true)
  public Jewel getJewelById(Long id) {
    if (id == null || id <= 0) {
      throw new IllegalArgumentException("Jewel ID must be a positive non-null value");
    }
    return jewelRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Jewel not found with ID: " + id));
  }

  /**
   * Saves a jewel entity.
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
    // Asegurarse de que la categoría y el nombre se guarden en mayúsculas
    if (jewel.getCategory() != null) {
      jewel.setCategory(jewel.getCategory().toUpperCase());
    }
    if (jewel.getName() != null) {
      jewel.setName(jewel.getName().toUpperCase());
    }
    return jewelRepository.save(jewel);
  }

  /**
   * Deletes a jewel by its ID.
   *
   * @param id the ID of the jewel to delete
   * @throws IllegalArgumentException if the ID is null or invalid
   */
  @Transactional
  public void deleteJewel(Long id) {
    if (id == null || id <= 0) {
      throw new IllegalArgumentException("Jewel ID must be a positive non-null value");
    }
    jewelRepository.deleteById(id);
  }
}