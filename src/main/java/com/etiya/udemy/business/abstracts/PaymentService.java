package com.etiya.udemy.business.abstracts;

import com.etiya.udemy.business.dtos.requests.payment.CreatePaymentRequest;
import com.etiya.udemy.business.dtos.responses.payment.PaymentResponse;

import java.util.List;

public interface PaymentService {
    PaymentResponse add(CreatePaymentRequest request);

    PaymentResponse getById(Long id);

    List<PaymentResponse> getAll();
}
