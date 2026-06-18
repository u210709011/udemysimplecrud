package com.etiya.udemy.api.controllers;

import com.etiya.udemy.business.abstracts.InstructorProfileService;
import com.etiya.udemy.business.dtos.requests.instructorprofile.CreateInstructorProfileRequest;
import com.etiya.udemy.business.dtos.responses.instructorprofile.InstructorProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/instructor-profiles")
@Tag(name = "Eğitmen Profilleri", description = "TEACHER profilindeki kullanıcılara ait eğitmen tanıtım bilgileri")
public class InstructorProfilesController {

    private final InstructorProfileService instructorProfileService;

    public InstructorProfilesController(InstructorProfileService instructorProfileService) {
        this.instructorProfileService = instructorProfileService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Eğitmen profili oluştur", description = "Yalnızca profili TEACHER olan ve henüz profili olmayan kullanıcı için eklenebilir.")
    public InstructorProfileResponse add(@Valid @RequestBody CreateInstructorProfileRequest request) {
        return instructorProfileService.add(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Eğitmen profilini id ile getir")
    public InstructorProfileResponse getById(@PathVariable Long id) {
        return instructorProfileService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Tüm eğitmen profillerini listele")
    public List<InstructorProfileResponse> getAll() {
        return instructorProfileService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eğitmen profilini sil", description = "Soft-delete uygular.")
    public void delete(@PathVariable Long id) {
        instructorProfileService.delete(id);
    }
}
