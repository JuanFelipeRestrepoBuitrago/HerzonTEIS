package com.eafit.herzon.teis.repositories;

import com.eafit.herzon.teis.models.Jewel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Jewel entities.
 */
@Repository
public interface JewelRepository extends JpaRepository<Jewel, Long> {

  /**
   * Finds jewels by their name using a case-insensitive partial match.
   *
   * @param name the name to search for
   * @return a list of jewels matching the name
   */
  @Query("SELECT j FROM Jewel j WHERE LOWER(j.name) LIKE LOWER(CONCAT('%', :name, '%'))")
  List<Jewel> searchJewelByName(String name);

  /**
   * Finds jewels by their category using a case-insensitive partial match.
   *
   * @param category the category to search for
   * @return a list of jewels matching the category
   */
  @Query("SELECT j FROM Jewel j WHERE LOWER(j.category) LIKE LOWER(CONCAT('%', :category, '%'))")
  List<Jewel> searchJewelByCategory(String category);

  /**
   * Finds jewels containing a keyword in their details using a case-insensitive match.
   *
   * @param keyword the keyword to search for
   * @return a list of jewels containing the keyword in their details
   */
  @Query("SELECT j FROM Jewel j WHERE LOWER(j.details) LIKE LOWER(CONCAT('%', :keyword, '%'))")
  List<Jewel> searchJewelByKeyword(String keyword);
}