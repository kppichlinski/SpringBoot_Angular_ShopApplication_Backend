package com.example.shop.user.review.repository;

import com.example.shop.user.common.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
