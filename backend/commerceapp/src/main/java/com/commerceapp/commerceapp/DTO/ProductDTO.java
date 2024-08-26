package com.commerceapp.commerceapp.DTO;

import java.util.List;

import com.commerceapp.commerceapp.entity.CartItem;

import lombok.Data;

@Data
public class ProductDTO {
    private Long productId;
    private String productName;
    private String productTag;
    private double productPrice;
    private String productDescription;
    private List<CartItem> items;
}
