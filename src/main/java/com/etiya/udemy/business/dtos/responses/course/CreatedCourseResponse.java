package com.etiya.udemy.business.dtos.responses.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatedCourseResponse {
    private Long id;
    private String code;
    private String name;
    private BigDecimal price;
    private Long categoryId;
    private Long instructorId;
}
