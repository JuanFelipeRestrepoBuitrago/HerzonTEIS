package com.eafit.herzon.teis.services;

import com.eafit.herzon.teis.models.Jewel;
import com.eafit.herzon.teis.models.WishList;
import com.eafit.herzon.teis.repositories.WishListRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
* Service class for managing wish list operations.
*/
@Service
public class WishListService {

  private final WishListRepository wishListRepository;

  /**
  * Constructs a new WishListService with the specified repository.
  *
  * @param wishListRepository the repository for managing wish list data
  */
  public WishListService(WishListRepository wishListRepository) {
    this.wishListRepository = wishListRepository;
  }

  /**
  * Adds a jewel to the wish list.
  *
  * @param wishList the wish list
  * @param jewel    the jewel to add
  */
  @Transactional
  public void addJewels(WishList wishList, Jewel jewel) {
    wishList.getJewels().add(jewel);
    wishListRepository.save(wishList);
  }

  /**
  * Removes a jewel from the wish list.
  *
  * @param wishList the wish list
  * @param jewel    the jewel to remove
  * @return true if the jewel was removed, false otherwise
  */
  @Transactional
  public boolean removeJewels(WishList wishList, Jewel jewel) {
    boolean removed = wishList.getJewels().remove(jewel);
    if (removed) {
      wishListRepository.save(wishList);
    }
    return removed;
  }
}
