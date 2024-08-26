package com.commerceapp.commerceapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.commerceapp.commerceapp.DTO.ProductDTO;
import com.commerceapp.commerceapp.entity.Product;
import com.commerceapp.commerceapp.repository.ProductRepo;

@Service
public class ProductService {
    private ProductRepo productRepo;
    
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public ProductDTO saveNewProduct(Product product) {
        Product saveProduct=productRepo.save(product);
        return convertToProductDTO(saveProduct);
    }


    public List<ProductDTO> getAllProduct() {
        List<Product> product=productRepo.findAll();
        return product.stream().map(this::convertToProductDTO).collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long productId) {
        Product product=productRepo.findById(productId).orElseThrow(()->new RuntimeException("cannot find product"));
        return convertToProductDTO(product);

    }

    public void deleteProductById(Long productId) {
        productRepo.deleteById(productId);
    }

    public ProductDTO updateProductById(Product product,Long productId) {
        Product productDB=productRepo.findById(productId).orElseThrow(()->new RuntimeException("cannot find product"));

        productDB.setProductId(product.getProductId());
        productDB.setProductName(product.getProductName());
        productDB.setProductPrice(product.getProductPrice());
        productDB.setProductTag(product.getProductTag());
        productDB.setProductDescription(product.getProductDescription());

        Product savedProduct = productRepo.save(productDB);

        return convertToProductDTO(savedProduct);
    }

    private ProductDTO convertToProductDTO(Product product){
        ProductDTO dto=new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setProductTag(product.getProductTag());
        dto.setProductPrice(product.getProductPrice());
        dto.setProductDescription(product.getProductDescription());
        return dto;
    }
    
}
