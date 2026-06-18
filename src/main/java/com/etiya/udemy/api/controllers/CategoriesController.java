package com.etiya.udemy.api.controllers;

import com.etiya.udemy.business.abstracts.CategoryService;
import com.etiya.udemy.business.dtos.requests.category.CreateCategoryRequest;
import com.etiya.udemy.business.dtos.requests.category.UpdateCategoryRequest;
import com.etiya.udemy.business.dtos.responses.category.CategoryListItemResponse;
import com.etiya.udemy.business.dtos.responses.category.CreatedCategoryResponse;
import com.etiya.udemy.business.dtos.responses.category.GetCategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Kategoriler", description = "Kurs kategorileri (hiyerarşik: parentId ile alt/üst kategori desteği)")
public class CategoriesController {

    private final CategoryService categoryService;

    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni kategori oluştur", description = "İsteğe bağlı parentId ile bir üst kategoriye bağlanabilir.")
    public CreatedCategoryResponse add(@Valid @RequestBody CreateCategoryRequest request) {
        return categoryService.add(request);
    }

    @PutMapping
    @Operation(summary = "Kategoriyi güncelle")
    public CreatedCategoryResponse update(@Valid @RequestBody UpdateCategoryRequest request) {
        return categoryService.update(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Kategoriyi id ile getir")
    public GetCategoryResponse getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Tüm kategorileri listele")
    public List<CategoryListItemResponse> getAll() {
        return categoryService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Kategoriyi sil", description = "Soft-delete uygular.")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
