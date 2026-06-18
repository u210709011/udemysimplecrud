package com.etiya.udemy.api.controllers;

import com.etiya.udemy.business.abstracts.CourseService;
import com.etiya.udemy.business.dtos.requests.course.CreateCourseRequest;
import com.etiya.udemy.business.dtos.requests.course.UpdateCourseRequest;
import com.etiya.udemy.business.dtos.responses.course.CourseListItemResponse;
import com.etiya.udemy.business.dtos.responses.course.CreatedCourseResponse;
import com.etiya.udemy.business.dtos.responses.course.GetCourseResponse;
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
@RequestMapping("/api/v1/courses")
@Tag(name = "Kurslar", description = "Kurs oluşturma, güncelleme, listeleme ve silme. Eğitmen TEACHER olmalıdır.")
public class CoursesController {

    private final CourseService courseService;

    public CoursesController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni kurs oluştur", description = "categoryId ve instructorId zorunludur. Kurs kodu (code) benzersiz olmalıdır.")
    public CreatedCourseResponse add(@Valid @RequestBody CreateCourseRequest request) {
        return courseService.add(request);
    }

    @PutMapping
    @Operation(summary = "Kursu güncelle", description = "Ad, açıklama, fiyat ve kategori bilgisini günceller.")
    public CreatedCourseResponse update(@Valid @RequestBody UpdateCourseRequest request) {
        return courseService.update(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Kursu id ile getir")
    public GetCourseResponse getById(@PathVariable Long id) {
        return courseService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Tüm kursları listele")
    public List<CourseListItemResponse> getAll() {
        return courseService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Kursu sil", description = "Soft-delete uygular.")
    public void delete(@PathVariable Long id) {
        courseService.delete(id);
    }
}
