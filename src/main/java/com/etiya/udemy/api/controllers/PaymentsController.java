package com.etiya.udemy.api.controllers;

import com.etiya.udemy.business.abstracts.PaymentService;
import com.etiya.udemy.business.dtos.requests.payment.CreatePaymentRequest;
import com.etiya.udemy.business.dtos.responses.payment.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Ödemeler", description = "Kurs ödemeleri. Ödeme tutarı kurs fiyatına eşit olmalı; aynı kursa iki kez ödeme yapılamaz.")
public class PaymentsController {

    private final PaymentService paymentService;

    public PaymentsController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Ödeme yap", description = "DEBIT/CREDIT yöntemiyle kurs ödemesi oluşturur. Tutar kurs fiyatıyla eşleşmelidir.")
    public PaymentResponse add(@Valid @RequestBody CreatePaymentRequest request) {
        return paymentService.add(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ödemeyi id ile getir")
    public PaymentResponse getById(@PathVariable Long id) {
        return paymentService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Tüm ödemeleri listele")
    public List<PaymentResponse> getAll() {
        return paymentService.getAll();
    }
}
