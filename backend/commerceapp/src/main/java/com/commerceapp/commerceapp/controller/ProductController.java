package com.commerceapp.commerceapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.commerceapp.commerceapp.DTO.ProductDTO;
import com.commerceapp.commerceapp.entity.Product;
import com.commerceapp.commerceapp.service.ProductService;

@RestController
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @PostMapping("/save/product")
    private ProductDTO saveNewProduct(@RequestBody Product product){
        return productService.saveNewProduct(product);
    }
    

    @GetMapping("/product/all")
    private List<ProductDTO> getAllProduct(){
        return productService.getAllProduct();
    }

    @GetMapping("/product/{id}")
    private ProductDTO getProductById(@PathVariable ("id") Long productId){
        return productService.getProductById(productId);
    }

    @DeleteMapping("/delete/product/{id}")
    private void deleteProductById(@PathVariable ("id") Long productId){
        productService.deleteProductById(productId);
    }

    @PutMapping("/update/product/{id}")
    private ProductDTO updateProductById(@RequestBody Product product,@PathVariable ("id") Long productId){
        return productService.updateProductById(product,productId);
    }
}
