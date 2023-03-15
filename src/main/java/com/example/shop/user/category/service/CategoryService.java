package com.example.shop.user.category.service;

import com.example.shop.user.category.model.Category;
import com.example.shop.user.category.model.dto.CategoryProductsDto;
import com.example.shop.user.category.repository.CategoryRepository;
import com.example.shop.user.product.model.Product;
import com.example.shop.user.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
        return new CategoryProductsDto(category, productsPage);
    }
}
