package com.commerceapp.commerceapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.commerceapp.commerceapp.DTO.CartDTO;
import com.commerceapp.commerceapp.DTO.CartItemDTO;
import com.commerceapp.commerceapp.DTO.ProductDTO;
import com.commerceapp.commerceapp.entity.Cart;
import com.commerceapp.commerceapp.entity.CartItem;
import com.commerceapp.commerceapp.entity.Product;
import com.commerceapp.commerceapp.entity.User;
import com.commerceapp.commerceapp.repository.CartItemRepo;
import com.commerceapp.commerceapp.repository.CartRepo;
import com.commerceapp.commerceapp.repository.ProductRepo;
import com.commerceapp.commerceapp.repository.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class CartService {
    private final ProductRepo productRepo;
    private final CartRepo cartRepo;
    private final UserRepo userRepo;
    private final CartItemRepo cartItemRepo;

    public CartService(ProductRepo productRepo, CartRepo cartRepo, UserRepo userRepo, CartItemRepo cartItemRepo) {
        this.productRepo = productRepo;
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.cartItemRepo = cartItemRepo;
    }

    public CartDTO addCartToItems(Long userId, Long productId, Integer quantity, String size, String weight) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User cannot find"));
        Product product = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product cannot find"));

        Cart cart = cartRepo.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepo.save(newCart);
        });

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setProductQuantity(quantity);
        cartItem.setProductWeight(weight);
        cartItem.setProductSize(size);

        cart.getItems().add(cartItem);
        cartRepo.save(cart);

        return convertToCartDTO(cart);
    }

    public CartDTO getCartByUserId(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User cannot find"));
        Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new RuntimeException("Cart cannot find"));
        return convertToCartDTO(cart);
    }

    public List<CartItemDTO> getAllCartItems() {
        return cartItemRepo.findAll().stream()
            .map(this::convertToCartItemDTO)
            .collect(Collectors.toList());
    }
    @Transactional
    public void deleteCartItem(Long itemId) {
        cartItemRepo.deleteById(itemId);
    }
    @Transactional
    public void deleteAllCartItemsByUserId(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User cannot find"));
        Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new RuntimeException("Cart cannot find"));

        cartItemRepo.deleteAllByCart(cart);
    }

    private CartDTO convertToCartDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setCartId(cart.getCartId());
        dto.setItems(cart.getItems().stream()
            .map(this::convertToCartItemDTO)
            .collect(Collectors.toList()));
        return dto;
    }

    private CartItemDTO convertToCartItemDTO(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setItemId(cartItem.getItemId());
        dto.setProductQuantity(cartItem.getProductQuantity());
        dto.setProductWeight(cartItem.getProductWeight());
        dto.setProductSize(cartItem.getProductSize());

        if (cartItem.getProduct() != null) {
            dto.setProduct(convertToProductDTO(cartItem.getProduct()));
        }
        
        return dto;
    }
    public List<CartItemDTO> convertToCartItemDTOList(List<CartItem> cartItems) {
        return cartItems.stream()
                        .map(this::convertToCartItemDTO) 
                        .collect(Collectors.toList());
    }

    private ProductDTO convertToProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setProductTag(product.getProductTag());
        dto.setProductPrice(product.getProductPrice());
        dto.setProductDescription(product.getProductDescription());
        return dto;
    }

    public List<CartItemDTO> getCartByUser(Long userId) {
        User user = userRepo.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User cannot find"));
        Cart cart = cartRepo.findByUser(user)
                            .orElseThrow(() -> new RuntimeException("Cart cannot find"));
    
        List<CartItem> cartItems = cartItemRepo.findByCart(cart);
        
        return convertToCartItemDTOList(cartItems);
    }
}
