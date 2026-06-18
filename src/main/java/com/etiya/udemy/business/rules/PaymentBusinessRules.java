package com.etiya.udemy.business.rules;

import com.etiya.udemy.business.constants.Messages;
import com.etiya.udemy.core.crosscuttingconcerns.exceptions.types.BusinessException;
import com.etiya.udemy.dataAccess.abstracts.PaymentRepository;
import com.etiya.udemy.entities.concretes.Course;
import com.etiya.udemy.entities.concretes.Payment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentBusinessRules {

    private final PaymentRepository paymentRepository;

    public PaymentBusinessRules(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment paymentMustExist(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(Messages.Payment.NOT_FOUND));
    }

    public void userCannotPayForSameCourseTwice(Long userId, Long courseId) {
        if (paymentRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new BusinessException(Messages.Payment.ALREADY_PAID);
        }
    }

    public void amountMustMatchCoursePrice(BigDecimal amount, Course course) {
        if (course.getPrice().compareTo(amount) != 0) {
            throw new BusinessException(Messages.Payment.AMOUNT_MISMATCH);
        }
    }
}
