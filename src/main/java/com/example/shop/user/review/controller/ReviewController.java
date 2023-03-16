package com.example.shop.user.review.controller;

import com.example.shop.user.common.model.Review;
import com.example.shop.user.review.dto.ReviewDto;
import com.example.shop.user.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public Review addReview(@RequestBody @Valid ReviewDto reviewDto) {
        return reviewService.addReview(Review.builder()
                .authorName(clearContent(reviewDto.authorName()))
                .content(clearContent(reviewDto.content()))
                .productId(reviewDto.productId())
                .build());
    }

    private String clearContent(String text) {
        return Jsoup.clean(text, Safelist.none());
    }
}
