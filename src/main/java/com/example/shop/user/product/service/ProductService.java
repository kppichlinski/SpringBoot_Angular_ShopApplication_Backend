package com.example.shop.user.product.service;

import com.example.shop.user.common.model.Product;
import com.example.shop.user.common.model.Review;
import com.example.shop.user.common.repository.ProductRepository;
import com.example.shop.user.common.repository.ReviewRepository;
import com.example.shop.user.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    public static final boolean MODERATED = true;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public ProductDto getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug).orElseThrow();
        List<Review> reviews = reviewRepository.findAllByProductIdAndModerated(product.getId(), MODERATED);
        return mapToProductDto(product, reviews);
    }

    @Transactional(readOnly = true)
    private ProductDto mapToProductDto(Product product, List<Review> reviews) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .categoryId(product.getCategoryId())
                .description(product.getDescription())
                .fullDescription(product.getFullDescription())
                .price(product.getPrice())
                .currency(product.getCurrency())
                .image(product.getImage())
                .slug(product.getSlug())
                .reviews(reviews)
                .build();
    }
}
