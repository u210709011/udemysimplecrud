package com.etiya.udemy.business.abstracts;

import com.etiya.udemy.business.dtos.requests.review.CreateReviewRequest;
import com.etiya.udemy.business.dtos.responses.review.ReviewResponse;

import java.util.List;

public interface ReviewService {
    ReviewResponse add(CreateReviewRequest request);

    ReviewResponse getById(Long id);

    List<ReviewResponse> getAll();

    void delete(Long id);
}
