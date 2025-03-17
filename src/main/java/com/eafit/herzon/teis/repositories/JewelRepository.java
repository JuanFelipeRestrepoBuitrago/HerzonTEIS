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
 * Repository interface for managing jewel entities.
 */
@Repository
public interface JewelRepository extends JpaRepository<Jewel, Long> {

  /**
   * Retrieves distinct categories of jewels.
   *
   * @return a list of distinct category names
   */
  @Query("SELECT DISTINCT j.category FROM Jewel j")
  List<String> findDistinctCategories();

  /**
   * Retrieves jewels filtered by search criteria with pagination.
   *
   * @param search the search term for jewel names (can be null)
   * @param category the category to filter by (can be null)
   * @param price the price sorting criteria ("high" or "low", can be null)
   * @param sort the sorting criteria ("name" or "price", can be null)
   * @param pageable pagination details
   * @return a page of jewels matching the filter criteria
   */
  @Query("SELECT j FROM Jewel j WHERE "
      + "(:search IS NULL OR j.name LIKE :search) "
      + "AND (:category IS NULL OR j.category = :category) "
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