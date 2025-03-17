package com.eafit.herzon.teis.services;


import com.eafit.herzon.teis.exceptions.InvalidOfferException;
import com.eafit.herzon.teis.models.Cart;
import com.eafit.herzon.teis.models.CartItem;
import com.eafit.herzon.teis.models.User;
import com.eafit.herzon.teis.models.Jewel;
import com.eafit.herzon.teis.repositories.CartItemRepository;
import jakarta.transaction.Transactional;

import com.eafit.herzon.teis.repositories.CartRepository;
import com.eafit.herzon.teis.repositories.UserRepository;
import  com.eafit.herzon.teis.repositories.JewelRepository;
import java.util.List;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
public class CartService {
    private CartRepository cartRepository;
    private UserRepository userRepository;
    private JewelRepository jewelRepository;
    private CartItemRepository cartItemRepository;


    public CartService(CartRepository cartRepository, UserRepository userRepository, JewelRepository jewelRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.jewelRepository = jewelRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public void addItem(long user_id, Long jewel_id, long id, int quantity) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Jewel jewel = jewelRepository.findById(jewel_id)
                .orElseThrow(() -> new RuntimeException("Joya no encontrada"));

        Cart cart = user.getCart();
        CartItem cartItem = new CartItem(jewel, quantity);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }

        cart.addItem(cartItem);

        cartItemRepository.save(cartItem);
    }

    public void removeItem(long user_id, long cart_item_id, long id) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        CartItem item = cartItemRepository.findById(cart_item_id)
                .orElseThrow(() -> new RuntimeException("Joya no encontrada"));

        Cart cart = user.getCart();
        cart.removeItem(item);

    }

    public void  emptyCart(long user_id) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Cart cart = user.getCart();

        cart.getItems().clear();
    }
}
