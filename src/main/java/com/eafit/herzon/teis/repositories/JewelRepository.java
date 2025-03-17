package com.eafit.herzon.teis.repositories;

import com.eafit.herzon.teis.models.Jewel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Jewel entities with pagination support.
 */
@Repository
public interface JewelRepository extends JpaRepository<Jewel, Long> {

  /**
   * Finds jewels by name with case-insensitive partial match and pagination.
   *
   * @param name the name to search for
   * @param pageable pagination details
   * @return a page of matching jewels
   */
  @Query("SELECT j FROM Jewel j WHERE LOWER(j.name) LIKE LOWER(CONCAT('%', :name, '%'))")
  Page<Jewel> searchJewelByName(String name, Pageable pageable);

  /**
   * Finds jewels by category with case-insensitive partial match and pagination.
   *
   * @param category the category to search for
   * @param pageable pagination details
   * @return a page of matching jewels
   */
  @Query("SELECT j FROM Jewel j WHERE LOWER(j.category) LIKE LOWER(CONCAT('%', :category, '%'))")
  Page<Jewel> searchJewelByCategory(String category, Pageable pageable);

  /**
   * Finds jewels by keyword in details with case-insensitive match and pagination.
   *
   * @param keyword the keyword to search for
   * @param pageable pagination details
   * @return a page of matching jewels
   */
  @Query("SELECT j FROM Jewel j WHERE LOWER(j.details) LIKE LOWER(CONCAT('%', :keyword, '%'))")
  Page<Jewel> searchJewelByKeyword(String keyword, Pageable pageable);
}