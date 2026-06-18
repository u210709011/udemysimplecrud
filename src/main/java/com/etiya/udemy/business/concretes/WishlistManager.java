package com.etiya.udemy.business.concretes;

import com.etiya.udemy.business.abstracts.WishlistService;
import com.etiya.udemy.business.dtos.requests.wishlist.CreateWishlistRequest;
import com.etiya.udemy.business.dtos.responses.wishlist.WishlistResponse;
import com.etiya.udemy.business.mappers.WishlistMapper;
import com.etiya.udemy.business.rules.CourseBusinessRules;
import com.etiya.udemy.business.rules.UserBusinessRules;
import com.etiya.udemy.business.rules.WishlistBusinessRules;
import com.etiya.udemy.dataAccess.abstracts.WishlistRepository;
import com.etiya.udemy.entities.concretes.Course;
import com.etiya.udemy.entities.concretes.User;
import com.etiya.udemy.entities.concretes.Wishlist;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class WishlistManager implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistMapper wishlistMapper;
    private final WishlistBusinessRules rules;
    private final UserBusinessRules userBusinessRules;
    private final CourseBusinessRules courseBusinessRules;

    public WishlistManager(WishlistRepository wishlistRepository,
                           WishlistMapper wishlistMapper,
                           WishlistBusinessRules rules,
                           UserBusinessRules userBusinessRules,
                           CourseBusinessRules courseBusinessRules) {
        this.wishlistRepository = wishlistRepository;
        this.wishlistMapper = wishlistMapper;
        this.rules = rules;
        this.userBusinessRules = userBusinessRules;
        this.courseBusinessRules = courseBusinessRules;
    }

    @Override
    public WishlistResponse add(CreateWishlistRequest request) {
        User user = userBusinessRules.userMustExist(request.getUserId());
        Course course = courseBusinessRules.courseMustExist(request.getCourseId());
        rules.courseCannotBeAddedToWishlistTwice(request.getUserId(), request.getCourseId());

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setCourse(course);

        return wishlistMapper.toResponse(wishlistRepository.save(wishlist));
    }

    @Override
    public WishlistResponse getById(Long id) {
        return wishlistMapper.toResponse(rules.wishlistMustExist(id));
    }

    @Override
    public List<WishlistResponse> getAll() {
        return wishlistMapper.toResponseList(wishlistRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        Wishlist wishlist = rules.wishlistMustExist(id);
        wishlist.setActive(false);
        wishlist.setDeletedDate(LocalDateTime.now());
        wishlistRepository.save(wishlist);
    }
}
