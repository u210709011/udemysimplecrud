package com.etiya.udemy.business.dtos.responses.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListItemResponse {
    private Long id;
    private String name;
    private String parentName;
}
