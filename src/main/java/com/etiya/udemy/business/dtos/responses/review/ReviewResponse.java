package com.etiya.udemy.business.dtos.responses.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private int rating;
    private String comment;
    private LocalDate reviewDate;
    private Long userId;
    private Long courseId;
    private String courseName;
}
