package com.commerceapp.commerceapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commerceapp.commerceapp.entity.Cart;
import com.commerceapp.commerceapp.entity.User;
@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {

    Optional<Cart> findByUser(User user);

    
}
