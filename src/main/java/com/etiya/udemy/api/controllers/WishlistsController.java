package com.etiya.udemy.api.controllers;

import com.etiya.udemy.business.abstracts.WishlistService;
import com.etiya.udemy.business.dtos.requests.wishlist.CreateWishlistRequest;
import com.etiya.udemy.business.dtos.responses.wishlist.WishlistResponse;
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
@RequestMapping("/api/v1/wishlists")
@Tag(name = "İstek Listesi (Wishlist)", description = "Kullanıcının ilgilendiği kursları istek listesine ekleme/çıkarma")
public class WishlistsController {

    private final WishlistService wishlistService;

    public WishlistsController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "İstek listesine kurs ekle", description = "Aynı kurs istek listesine iki kez eklenemez.")
    public WishlistResponse add(@Valid @RequestBody CreateWishlistRequest request) {
        return wishlistService.add(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "İstek listesi kaydını id ile getir")
    public WishlistResponse getById(@PathVariable Long id) {
        return wishlistService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Tüm istek listesi kayıtlarını listele")
    public List<WishlistResponse> getAll() {
        return wishlistService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "İstek listesi kaydını sil", description = "Soft-delete uygular.")
    public void delete(@PathVariable Long id) {
        wishlistService.delete(id);
    }
}
