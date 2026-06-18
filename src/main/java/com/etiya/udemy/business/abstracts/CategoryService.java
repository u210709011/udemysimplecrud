package com.etiya.udemy.business.abstracts;

import com.etiya.udemy.business.dtos.requests.category.CreateCategoryRequest;
import com.etiya.udemy.business.dtos.requests.category.UpdateCategoryRequest;
import com.etiya.udemy.business.dtos.responses.category.CategoryListItemResponse;
import com.etiya.udemy.business.dtos.responses.category.CreatedCategoryResponse;
import com.etiya.udemy.business.dtos.responses.category.GetCategoryResponse;

import java.util.List;

public interface CategoryService {
    CreatedCategoryResponse add(CreateCategoryRequest request);

    CreatedCategoryResponse update(UpdateCategoryRequest request);

    GetCategoryResponse getById(Long id);

    List<CategoryListItemResponse> getAll();

    void delete(Long id);
}
