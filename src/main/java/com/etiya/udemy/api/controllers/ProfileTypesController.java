package com.etiya.udemy.api.controllers;

import com.etiya.udemy.business.abstracts.ProfileTypeService;
import com.etiya.udemy.business.dtos.requests.profiletype.CreateProfileTypeRequest;
import com.etiya.udemy.business.dtos.responses.profiletype.ProfileTypeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile-types")
@Tag(name = "Profil Tipleri", description = "Kullanıcı profil tipleri (ADMIN, TEACHER, STUDENT) yönetimi")
public class ProfileTypesController {

    private final ProfileTypeService profileTypeService;

    public ProfileTypesController(ProfileTypeService profileTypeService) {
        this.profileTypeService = profileTypeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni profil tipi oluştur", description = "ADMIN, TEACHER veya STUDENT profil tipi ekler.")
    public ProfileTypeResponse add(@Valid @RequestBody CreateProfileTypeRequest request) {
        return profileTypeService.add(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Profil tipini id ile getir")
    public ProfileTypeResponse getById(@PathVariable Long id) {
        return profileTypeService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Tüm profil tiplerini listele")
    public List<ProfileTypeResponse> getAll() {
        return profileTypeService.getAll();
    }
}
