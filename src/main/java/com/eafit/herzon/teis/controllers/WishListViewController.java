package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.models.CustomUser;
import com.eafit.herzon.teis.models.Jewel;
import com.eafit.herzon.teis.models.WishList;
import com.eafit.herzon.teis.repositories.JewelRepository;
import com.eafit.herzon.teis.services.UserService;
import com.eafit.herzon.teis.services.WishListService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;




/**
* Obtiene la lista de deseos del usuario autenticado.
*
*/
@Controller
@RequestMapping("/wishlist")
public class WishListViewController {

  private final WishListService wishListService;
  private final UserService userService;
  private final JewelRepository jewelRepository;



  /**
  * Obtiene la lista de deseos del usuario autenticado.
  *
  */
  public WishListViewController(WishListService wishListService,
                                UserService userService, JewelRepository jewelRepository) {
    this.wishListService = wishListService;
    this.userService = userService;
    this.jewelRepository = jewelRepository;
  }

  /**
  * Muestra la vista de la lista de deseos.
  *
  * @param model el modelo para la vista
  * @return la plantilla "wishlist/items"
  */
  @GetMapping
  public String showWishList(Model model) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    CustomUser user = userService.getUserByUsername(username);
    WishList wishList = user.getWishList();
    if (wishList == null) {
      model.addAttribute("jewels", new ArrayList<Jewel>());
    } else {
      model.addAttribute("jewels", wishList.getJewels());
    }
    return "wishlist/items";
  }

  /**
  * Añade una joya a la lista de deseos y redirige a la vista.
  *
  * @param jewelId el ID de la joya a añadir
  * @return redirección a la vista de la wishlist
  */
  @PostMapping("/add/{jewelId}")
  @Transactional
  public String addJewel(@PathVariable Long jewelId) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    CustomUser user = userService.getUserByUsername(username);
    WishList wishList = user.getWishList();
    if (wishList == null) {
      wishList = new WishList();
      wishList.setUser(user);

    }
    Jewel jewel = jewelRepository.findById(jewelId)
            .orElseThrow(() -> new RuntimeException("Joya no encontrada"));
    wishListService.addJewels(wishList, jewel);
    return "redirect:/wishlist";
  }

  /**
  * Elimina una joya de la lista de deseos y redirige a la vista.
  *
  * @param jewelId el ID de la joya a eliminar
  * @return redirección a la vista de la wishlist
  */
  @PostMapping("/remove/{jewelId}")
  @Transactional
  public String removeJewel(@PathVariable Long jewelId) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    CustomUser user = userService.getUserByUsername(username);
    WishList wishList = user.getWishList();
    if (wishList != null) {
      Jewel jewel = jewelRepository.findById(jewelId)
              .orElseThrow(() -> new RuntimeException("Joya no encontrada"));
      wishListService.removeJewels(wishList, jewel);
    }
    return "redirect:/wishlist";
  }
}
