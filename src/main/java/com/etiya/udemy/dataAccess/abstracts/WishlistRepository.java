package com.etiya.udemy.dataAccess.abstracts;

import com.etiya.udemy.entities.concretes.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);
}
