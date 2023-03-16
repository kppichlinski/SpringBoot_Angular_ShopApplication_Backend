package com.example.shop.user.category.model.dto;

import com.example.shop.user.category.model.Category;
import com.example.shop.user.product.model.dto.ProductListDto;
import org.springframework.data.domain.Page;

public record CategoryProductsDto(Category category, Page<ProductListDto> products) {
}
