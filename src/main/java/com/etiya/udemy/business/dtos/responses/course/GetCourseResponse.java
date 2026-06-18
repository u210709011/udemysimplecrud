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
public class GetCourseResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private BigDecimal price;
    private String categoryName;
    private String instructorFirstName;
    private String instructorLastName;
}
