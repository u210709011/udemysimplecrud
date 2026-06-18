package com.etiya.udemy.api.controllers;

import com.etiya.udemy.business.abstracts.UserService;
import com.etiya.udemy.business.dtos.requests.user.CreateUserRequest;
import com.etiya.udemy.business.dtos.requests.user.UpdateUserRequest;
import com.etiya.udemy.business.dtos.responses.user.CreatedUserResponse;
import com.etiya.udemy.business.dtos.responses.user.GetUserResponse;
import com.etiya.udemy.business.dtos.responses.user.UserListItemResponse;
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
@RequestMapping("/api/v1/users")
@Tag(name = "Kullanıcılar", description = "Kullanıcı kaydı, güncelleme, listeleme ve silme işlemleri")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Yeni kullanıcı kaydet", description = "Verilen profil tipiyle (profileId) yeni bir kullanıcı oluşturur. E-posta benzersiz olmalıdır.")
    public CreatedUserResponse register(@Valid @RequestBody CreateUserRequest request) {
        return userService.register(request);
    }

    @PutMapping
    @Operation(summary = "Kullanıcıyı güncelle", description = "Ad, soyad ve e-posta bilgilerini günceller.")
    public CreatedUserResponse update(@Valid @RequestBody UpdateUserRequest request) {
        return userService.update(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Kullanıcıyı id ile getir")
    public GetUserResponse getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Tüm kullanıcıları listele")
    public List<UserListItemResponse> getAll() {
        return userService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Kullanıcıyı sil", description = "Soft-delete uygular (isActive=false).")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
