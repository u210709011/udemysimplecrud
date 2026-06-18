package com.etiya.udemy.business.rules;

import com.etiya.udemy.business.constants.Messages;
import com.etiya.udemy.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.udemy.dataAccess.abstracts.CategoryRepository;
import com.etiya.udemy.entities.concretes.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryBusinessRules {

    private final CategoryRepository categoryRepository;

    public CategoryBusinessRules(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category categoryMustExist(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(Messages.Category.NOT_FOUND));
    }

    public void categoryNameCannotBeDuplicatedUnderSameParent(String name, Long parentId) {
        if (categoryRepository.existsByNameAndParentId(name, parentId)) {
            throw new BusinessException(Messages.Category.NAME_ALREADY_EXISTS);
        }
    }

    public void categoryCannotBeItsOwnParent(Long id, Long parentId) {
        if (id != null && id.equals(parentId)) {
            throw new BusinessException(Messages.Category.CANNOT_BE_OWN_PARENT);
        }
    }
}
