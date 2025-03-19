package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.models.Jewel;
import com.eafit.herzon.teis.models.WishList;
import com.eafit.herzon.teis.repositories.JewelRepository;
import com.eafit.herzon.teis.services.UserService;
import com.eafit.herzon.teis.services.WishListService;
import jakarta.transaction.Transactional;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;





/**
* Obtiene la lista de deseos del usuario autenticado.
*
*/
@RestController
@RequestMapping("/api/wishlist")
public class WishListApiController {

  private final WishListService wishListService;
  private final UserService userService;
  private final JewelRepository jewelRepository;

  /**
  * Obtiene la lista de deseos del usuario autenticado.
  *
  */
  public WishListApiController(WishListService wishListService,
                               UserService userService, JewelRepository jewelRepository) {
    this.wishListService = wishListService;
    this.userService = userService;
    this.jewelRepository = jewelRepository;
  }

  /**
  * Obtiene la lista de deseos del usuario autenticado.
  *
  * @return JSON con la lista de joyas de la wishlist.
  */
  @GetMapping
  public ResponseEntity<?> getWishList() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    CustomUser user = userService.getUserByUsername(username);
    WishList wishList = user.getWishList();
    if (wishList == null) {
      return ResponseEntity.ok(Collections.emptyList());
    }
    return ResponseEntity.ok(wishList.getJewels());
  }

  /**
  * Añade una joya a la lista de deseos del usuario autenticado.
  *
  * @param jewelId el ID de la joya a añadir
  * @return JSON confirmando la acción
  */
  @PostMapping("/add/{jewelId}")
  @Transactional
  public ResponseEntity<?> addJewel(@PathVariable Long jewelId) {
    String username = SecurityContextHolder
            .getContext().getAuthentication().getName();
    CustomUser user = userService.getUserByUsername(username);
    WishList wishList = user.getWishList();
    if (wishList == null) {
      wishList = new WishList();
      wishList.setUser(user);
    }
    Jewel jewel = jewelRepository.findById(jewelId)
            .orElseThrow(() -> new RuntimeException("Joya no encontrada"));
    wishListService.addJewels(wishList, jewel);
    return ResponseEntity.ok("Joya agregada a la lista de deseos.");
  }

  /**
  * Elimina una joya de la lista de deseos del usuario autenticado.
  *
  * @param jewelId el ID de la joya a eliminar
  * @return JSON confirmando la acción
  */
  @DeleteMapping("/remove/{jewelId}")
  @Transactional
  public ResponseEntity<?> removeJewel(@PathVariable Long jewelId) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    CustomUser user = userService.getUserByUsername(username);
    WishList wishList = user.getWishList();
    if (wishList == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wishlist not found");
    }
    Jewel jewel = jewelRepository.findById(jewelId)
            .orElseThrow(() -> new RuntimeException("Joya no encontrada"));
    boolean removed = wishListService.removeJewels(wishList, jewel);
    if (removed) {
      return ResponseEntity.ok("Joya removida de la lista de deseos.");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body("La joya no se encontraba en la lista de deseos.");
    }
  }
}
