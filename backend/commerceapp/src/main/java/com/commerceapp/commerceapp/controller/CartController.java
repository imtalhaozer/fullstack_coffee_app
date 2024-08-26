package com.commerceapp.commerceapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.commerceapp.commerceapp.DTO.AddCartItemRequest;
import com.commerceapp.commerceapp.DTO.CartDTO;
import com.commerceapp.commerceapp.DTO.CartItemDTO;
import com.commerceapp.commerceapp.service.CartService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart/add")
    public CartDTO addCartToItems(@RequestBody AddCartItemRequest request) {
        return cartService.addCartToItems(
            request.getUserId(), 
            request.getProductId(), 
            request.getQuantity(), 
            request.getSize(), 
            request.getWeight()
        );
    }

    @GetMapping("/cart/all")
    public List<CartItemDTO> getAllCartItems() {
        return cartService.getAllCartItems();
    }

    @GetMapping("/cart/get")
    public List<CartItemDTO> getCartById(@RequestParam Long userId) {
        return cartService.getCartByUser(userId);
    }

    @DeleteMapping("/cart/delete/{id}")
    public void deleteCartItem(@PathVariable("id") Long itemId) {
        cartService.deleteCartItem(itemId);
    }

    @DeleteMapping("/cart/deleteAll/{userId}")
    public void deleteAllCartItemsByUserId(@PathVariable("userId") Long userId) {
        cartService.deleteAllCartItemsByUserId(userId);
    }
}
