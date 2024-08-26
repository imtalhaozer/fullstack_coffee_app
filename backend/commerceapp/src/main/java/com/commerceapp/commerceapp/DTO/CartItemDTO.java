package com.commerceapp.commerceapp.DTO;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long itemId;
    private Integer productQuantity;
    private String productWeight;
    private String productSize;
    private ProductDTO product;
}
