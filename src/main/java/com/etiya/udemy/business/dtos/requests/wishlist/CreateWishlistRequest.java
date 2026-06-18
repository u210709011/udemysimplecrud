package com.etiya.udemy.business.dtos.requests.wishlist;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateWishlistRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long courseId;
}
