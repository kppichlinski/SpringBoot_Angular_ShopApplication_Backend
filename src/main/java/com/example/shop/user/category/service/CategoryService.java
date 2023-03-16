package com.example.shop.user.category.service;

import com.example.shop.user.category.dto.CategoryProductsDto;
import com.example.shop.user.category.repository.CategoryRepository;
import com.example.shop.user.common.dto.ProductListDto;
import com.example.shop.user.common.model.Category;
import com.example.shop.user.common.model.Product;
import com.example.shop.user.common.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CategoryProductsDto getCategoriesWithProducts(String slug, Pageable pageable) {
        Category category = categoryRepository.findBySlug(slug);
        Page<Product> productsPage = productRepository.findByCategoryId(category.getId(), pageable);
        List<ProductListDto> productListDtos = productsPage.getContent().stream()
                .map(product -> ProductListDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .currency(product.getCurrency())
                        .image(product.getImage())
                        .slug(product.getSlug())
                        .build()).toList();

        return new CategoryProductsDto(category, new PageImpl<>(productListDtos, pageable, productsPage.getTotalElements()));
    }
}
