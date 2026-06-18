package com.etiya.udemy.business.concretes;

import com.etiya.udemy.business.abstracts.CategoryService;
import com.etiya.udemy.business.dtos.requests.category.CreateCategoryRequest;
import com.etiya.udemy.business.dtos.requests.category.UpdateCategoryRequest;
import com.etiya.udemy.business.dtos.responses.category.CategoryListItemResponse;
import com.etiya.udemy.business.dtos.responses.category.CreatedCategoryResponse;
import com.etiya.udemy.business.dtos.responses.category.GetCategoryResponse;
import com.etiya.udemy.business.mappers.CategoryMapper;
import com.etiya.udemy.business.rules.CategoryBusinessRules;
import com.etiya.udemy.dataAccess.abstracts.CategoryRepository;
import com.etiya.udemy.entities.concretes.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CategoryManager implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryBusinessRules rules;

    public CategoryManager(CategoryRepository categoryRepository,
                           CategoryMapper categoryMapper,
                           CategoryBusinessRules rules) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.rules = rules;
    }

    @Override
    public CreatedCategoryResponse add(CreateCategoryRequest request) {
        rules.categoryNameCannotBeDuplicatedUnderSameParent(request.getName(), request.getParentId());

        Category category = categoryMapper.toEntity(request);
        if (request.getParentId() != null) {
            category.setParent(rules.categoryMustExist(request.getParentId()));
        }

        Category saved = categoryRepository.save(category);
        return categoryMapper.toCreatedResponse(saved);
    }

    @Override
    public CreatedCategoryResponse update(UpdateCategoryRequest request) {
        Category category = rules.categoryMustExist(request.getId());
        rules.categoryCannotBeItsOwnParent(request.getId(), request.getParentId());

        category.setName(request.getName());
        if (request.getParentId() != null) {
            category.setParent(rules.categoryMustExist(request.getParentId()));
        } else {
            category.setParent(null);
        }

        Category saved = categoryRepository.save(category);
        return categoryMapper.toCreatedResponse(saved);
    }

    @Override
    public GetCategoryResponse getById(Long id) {
        Category category = rules.categoryMustExist(id);
        return categoryMapper.toGetResponse(category);
    }

    @Override
    public List<CategoryListItemResponse> getAll() {
        return categoryMapper.toListItemResponseList(categoryRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        Category category = rules.categoryMustExist(id);
        category.setActive(false);
        category.setDeletedDate(LocalDateTime.now());
        categoryRepository.save(category);
    }
}
