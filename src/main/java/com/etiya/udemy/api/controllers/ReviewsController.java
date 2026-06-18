package com.etiya.udemy.api.controllers;

import com.etiya.udemy.business.abstracts.ReviewService;
import com.etiya.udemy.business.dtos.requests.review.CreateReviewRequest;
import com.etiya.udemy.business.dtos.responses.review.ReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@Tag(name = "Yorumlar (Review)", description = "Kurs puanlama ve yorum. Eğitmen kendi kursuna yorum yapamaz; sadece kayıtlı öğrenci yorum yapar.")
public class ReviewsController {

    private final ReviewService reviewService;

    public ReviewsController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Kursa yorum yap", description = "rating 1-5 arası olmalıdır. Kullanıcı kursa kayıtlı olmalı ve daha önce yorum yapmamış olmalıdır.")
    public ReviewResponse add(@Valid @RequestBody CreateReviewRequest request) {
        return reviewService.add(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Yorumu id ile getir")
    public ReviewResponse getById(@PathVariable Long id) {
        return reviewService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Tüm yorumları listele")
    public List<ReviewResponse> getAll() {
        return reviewService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Yorumu sil", description = "Soft-delete uygular.")
    public void delete(@PathVariable Long id) {
        reviewService.delete(id);
    }
}
