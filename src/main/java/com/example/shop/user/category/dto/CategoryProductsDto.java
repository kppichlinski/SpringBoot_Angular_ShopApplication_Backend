package com.example.shop.user.category.dto;

import com.example.shop.user.common.dto.ProductListDto;
import com.example.shop.user.common.model.Category;
import org.springframework.data.domain.Page;

public record CategoryProductsDto(Category category, Page<ProductListDto> products) {
}
