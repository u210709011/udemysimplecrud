package com.etiya.udemy.business.rules;

import com.etiya.udemy.business.constants.Messages;
import com.etiya.udemy.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.udemy.dataAccess.abstracts.WishlistRepository;
import com.etiya.udemy.entities.concretes.Wishlist;
import org.springframework.stereotype.Service;

@Service
public class WishlistBusinessRules {

    private final WishlistRepository wishlistRepository;

    public WishlistBusinessRules(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Wishlist wishlistMustExist(Long id) {
        return wishlistRepository.findById(id)
                .orElseThrow(() -> new BusinessException(Messages.Wishlist.NOT_FOUND));
    }

    public void courseCannotBeAddedToWishlistTwice(Long userId, Long courseId) {
        if (wishlistRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new BusinessException(Messages.Wishlist.ALREADY_IN_WISHLIST);
        }
    }
}
