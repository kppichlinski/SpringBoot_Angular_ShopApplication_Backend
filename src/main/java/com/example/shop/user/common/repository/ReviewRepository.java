package com.example.shop.user.common.repository;

import com.example.shop.user.common.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByProductIdAndModerated(Long productId, boolean moderated);
}
