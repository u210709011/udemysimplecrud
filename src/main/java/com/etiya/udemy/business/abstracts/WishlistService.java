package com.etiya.udemy.business.abstracts;

import com.etiya.udemy.business.dtos.requests.wishlist.CreateWishlistRequest;
import com.etiya.udemy.business.dtos.responses.wishlist.WishlistResponse;

import java.util.List;

public interface WishlistService {
    WishlistResponse add(CreateWishlistRequest request);

    WishlistResponse getById(Long id);

    List<WishlistResponse> getAll();

    void delete(Long id);
}
