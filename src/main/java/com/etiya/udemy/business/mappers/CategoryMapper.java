package com.etiya.udemy.business.mappers;

import com.etiya.udemy.business.dtos.requests.category.CreateCategoryRequest;
import com.etiya.udemy.business.dtos.responses.category.CategoryListItemResponse;
import com.etiya.udemy.business.dtos.responses.category.CreatedCategoryResponse;
import com.etiya.udemy.business.dtos.responses.category.GetCategoryResponse;
import com.etiya.udemy.entities.concretes.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = CentralMapperConfig.class)
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "courses", ignore = true)
    Category toEntity(CreateCategoryRequest request);

    @Mapping(target = "parentId", source = "parent.id")
    CreatedCategoryResponse toCreatedResponse(Category category);

    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "parentName", source = "parent.name")
    GetCategoryResponse toGetResponse(Category category);

    @Mapping(target = "parentName", source = "parent.name")
    CategoryListItemResponse toListItemResponse(Category category);

    List<CategoryListItemResponse> toListItemResponseList(List<Category> categories);
}
