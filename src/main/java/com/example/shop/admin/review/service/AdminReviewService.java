package com.example.shop.admin.review.service;

import com.example.shop.admin.review.model.AdminReview;
import com.example.shop.admin.review.repository.AdminReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminReviewService {

    private final AdminReviewRepository adminReviewRepository;

    public List<AdminReview> getReviews() {
        return adminReviewRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional
    public void moderate(Long id) {
        adminReviewRepository.moderate(id);
    }

    public void delete(Long id) {
        adminReviewRepository.deleteById(id);
    }
}


