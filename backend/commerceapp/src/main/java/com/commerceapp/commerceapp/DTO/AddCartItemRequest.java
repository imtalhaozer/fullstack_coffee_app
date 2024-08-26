package com.commerceapp.commerceapp.DTO;

import lombok.Data;

@Data
public class AddCartItemRequest {
    private Long userId;
    private Long productId;
    private Integer quantity;
    private String size;
    private String weight;
}
