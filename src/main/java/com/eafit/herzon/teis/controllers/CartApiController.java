package com.eafit.herzon.teis.controllers;

import com.eafit.herzon.teis.models.CartItem;
import com.eafit.herzon.teis.models.Jewel;
import com.eafit.herzon.teis.services.CartService;
import com.eafit.herzon.teis.services.JewelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CartApiController {

    private final CartService cartService;

    /**
     * Constructs a new CartApiController with the required service.
     *
     * @param cartService the service for jewel operations
     */
    @Autowired
    public CartApiController(CartService cartService) { this.cartService = cartService; }






    @PostMapping
    public ResponseEntity<String> AddCartItem(@RequestBody long user_id, Long jewel_id, long id, int quantity) {
        cartService.addItem( user_id,  jewel_id, id, quantity);
        return ResponseEntity.ok("OK");
    }



}
