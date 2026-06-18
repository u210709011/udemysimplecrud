package com.etiya.udemy.dataAccess.abstracts;

import com.etiya.udemy.entities.concretes.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameAndParentId(String name, Long parentId);
}
