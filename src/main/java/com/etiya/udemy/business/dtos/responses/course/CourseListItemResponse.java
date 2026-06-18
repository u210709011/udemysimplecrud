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
public class CourseListItemResponse {
    private Long id;
    private String code;
    private String name;
    private BigDecimal price;
    private String categoryName;
}
