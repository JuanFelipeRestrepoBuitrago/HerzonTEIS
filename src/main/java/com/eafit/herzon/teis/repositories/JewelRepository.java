package com.eafit.herzon.teis.repositories;

import com.eafit.herzon.teis.models.Jewel;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Jewel} entities.
 */
@Repository
public interface JewelRepository extends JpaRepository<Jewel, Long> {

  /**
   * Retrieves distinct categories of jewels from the database.
   *
   * @return a list of distinct category names
   */
  @Query("SELECT DISTINCT j.category FROM Jewel j")
  List<String> findDistinctCategories();

  /**
   * Retrieves jewels filtered by search criteria with pagination support.
   *
   * @param search   the search term for jewel names
   * @param category the category to filter by (case-insensitive; can be null)
   * @param price    the price sorting criteria
   * @param sort     the sorting criteria
   * @param pageable the pagination details (e.g., page number, size, sorting)
   * @return a page of {@link Jewel} entities matching the filter criteria
   */
  @Query(value = "SELECT j FROM Jewel j WHERE "
      + "(:search IS NULL OR LOWER(j.name) LIKE "
      + "LOWER(CONCAT('%', CAST(:search AS string), '%'))) "
      + "AND (:category IS NULL OR LOWER(j.category) LIKE "
      + "LOWER(CAST(:category AS string))) "
      + "ORDER BY "
      + "CASE WHEN :price = 'high' THEN j.price END DESC, "
      + "CASE WHEN :price = 'low' THEN j.price END ASC, "
      + "CASE WHEN :sort = 'name' THEN j.name END ASC, "
      + "CASE WHEN :sort = 'price' THEN j.price END ASC")
  Page<Jewel> findFilteredJewels(
      @Param("search") String search,
      @Param("category") String category,
      @Param("price") String price,
      @Param("sort") String sort,
      Pageable pageable);
}