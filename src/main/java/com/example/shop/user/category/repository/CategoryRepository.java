package com.example.shop.user.category.repository;

import com.example.shop.user.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findBySlug(String slug);
}
