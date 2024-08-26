package com.commerceapp.commerceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commerceapp.commerceapp.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    
}
