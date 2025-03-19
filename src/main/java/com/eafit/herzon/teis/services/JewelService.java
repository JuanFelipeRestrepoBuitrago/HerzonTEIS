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
 * Service layer for managing jewel-related operations.
 */
@Service
public class JewelService {

  private final JewelRepository jewelRepository;

  /**
   * Constructs a new JewelService with the specified repository.
   *
   * @param jewelRepository the repository for jewel data access
   */
  @Autowired
  public JewelService(JewelRepository jewelRepository) {
    this.jewelRepository = jewelRepository;
  }

  /**
   * Retrieves all jewels with pagination.
   *
   * @param page the page number (zero-based)
   * @param size the number of items per page
   * @return a page of {@link Jewel} entities
   */
  @Transactional(readOnly = true)
  public Page<Jewel> getAllJewels(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return jewelRepository.findAll(pageable);
  }

  /**
   * Retrieves all jewels without pagination.
   *
   * @return a list of all {@link Jewel} entities
   */
  @Transactional(readOnly = true)
  public List<Jewel> getAllJewels() {
    return jewelRepository.findAll();
  }

  /**
   * Retrieves all distinct categories of jewels.
   *
   * @return a list of distinct category names
   */
  @Transactional(readOnly = true)
  public List<String> getAllCategories() {
    return jewelRepository.findDistinctCategories();
  }

  /**
   * Filters jewels based on search criteria and pagination.
   *
   * @param search   the search term for jewel names (can be null or trimmed if empty)
   * @param category the category to filter by (can be null or trimmed if empty)
   * @param price    the price sorting criteria ("high" or "low", can be null or trimmed if empty)
   * @param sort     the sorting criteria ("name" or "price", can be null or trimmed if empty)
   * @param page     the page number (zero-based)
   * @param size     the number of items per page
   * @return a page of {@link Jewel} entities matching the filter criteria
   */
  @Transactional(readOnly = true)
  public Page<Jewel> filterJewels(
      String search,
      String category,
      String price,
      String sort,
      int page,
      int size) {

    search = (search != null && !search.trim().isEmpty()) ? search.trim() : null;
    category = (category != null && !category.trim().isEmpty()) ? category.trim() : null;
    price = (price != null && !price.trim().isEmpty()) ? price.trim().toLowerCase() : null;
    sort = (sort != null && !sort.trim().isEmpty()) ? sort.trim().toLowerCase() : null;
    
    Pageable pageable = PageRequest.of(page, size);
    return jewelRepository.findFilteredJewels(search, category, price, sort, pageable);
  }

  /**
   * Retrieves a jewel by its ID.
   *
   * @param id the ID of the jewel to retrieve
   * @return the {@link Jewel} entity if found
   * @throws EntityNotFoundException if no jewel is found with the given ID
   */
  @Transactional(readOnly = true)
  public Jewel getJewelById(Long id) {
    return jewelRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Jewel not found with ID: " + id));
  }

  /**
   * Saves a new or updates an existing jewel.
   *
   * @param jewel the {@link Jewel} entity to save
   * @return the saved {@link Jewel} entity
   */
  @Transactional
  public Jewel saveJewel(Jewel jewel) {
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
   */
  @Transactional
  public void deleteJewel(Long id) {
    jewelRepository.deleteById(id);
  }
}