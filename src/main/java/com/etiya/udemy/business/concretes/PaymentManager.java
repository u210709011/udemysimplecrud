package com.etiya.udemy.business.concretes;

import com.etiya.udemy.business.abstracts.PaymentService;
import com.etiya.udemy.business.dtos.requests.payment.CreatePaymentRequest;
import com.etiya.udemy.business.dtos.responses.payment.PaymentResponse;
import com.etiya.udemy.business.mappers.PaymentMapper;
import com.etiya.udemy.business.rules.CourseBusinessRules;
import com.etiya.udemy.business.rules.PaymentBusinessRules;
import com.etiya.udemy.business.rules.UserBusinessRules;
import com.etiya.udemy.dataAccess.abstracts.PaymentRepository;
import com.etiya.udemy.entities.concretes.Course;
import com.etiya.udemy.entities.concretes.Payment;
import com.etiya.udemy.entities.concretes.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PaymentManager implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentBusinessRules rules;
    private final UserBusinessRules userBusinessRules;
    private final CourseBusinessRules courseBusinessRules;

    public PaymentManager(PaymentRepository paymentRepository,
                          PaymentMapper paymentMapper,
                          PaymentBusinessRules rules,
                          UserBusinessRules userBusinessRules,
                          CourseBusinessRules courseBusinessRules) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.rules = rules;
        this.userBusinessRules = userBusinessRules;
        this.courseBusinessRules = courseBusinessRules;
    }

    @Override
    public PaymentResponse add(CreatePaymentRequest request) {
        User user = userBusinessRules.userMustExist(request.getUserId());
        Course course = courseBusinessRules.courseMustExist(request.getCourseId());
        rules.userCannotPayForSameCourseTwice(request.getUserId(), request.getCourseId());
        rules.amountMustMatchCoursePrice(request.getAmount(), course);

        Payment payment = new Payment();
        payment.setUser(user);
        payment.setCourse(course);
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentDate(LocalDateTime.now());

        return paymentMapper.toResponse(paymentRepository.save(payment));
    }

    @Override
    public PaymentResponse getById(Long id) {
        return paymentMapper.toResponse(rules.paymentMustExist(id));
    }

    @Override
    public List<PaymentResponse> getAll() {
        return paymentMapper.toResponseList(paymentRepository.findAll());
    }
}
