package com.commerceapp.commerceapp.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commerceapp.commerceapp.entity.Cart;
import com.commerceapp.commerceapp.entity.CartItem;
import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Long> {
    void deleteAllByCart(Cart cart);
    List<CartItem> findByCart(Cart cart);
}
