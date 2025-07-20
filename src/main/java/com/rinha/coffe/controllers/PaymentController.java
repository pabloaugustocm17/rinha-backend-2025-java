package com.rinha.coffe.controllers;

import com.rinha.coffe.models.dtos.PaymentCreateDTO;
import com.rinha.coffe.services.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
public class PaymentController {

    private final Logger LOG;

    private final PaymentService PAYMENT_SERVICE;

    public PaymentController(PaymentService paymentService) {
        this.LOG = LoggerFactory.getLogger(PaymentController.class);
        this.PAYMENT_SERVICE = paymentService;
    }

    @PostMapping("/payments")
    public ResponseEntity<?> doPost(
        @RequestBody PaymentCreateDTO dto
    ){

        //PAYMENT_SERVICE.createPaymentSync(dto);

        PAYMENT_SERVICE.createPaymentAsync(dto);

        return ResponseEntity.status(201).build();
    }

    @GetMapping("/payments-summary")
    public ResponseEntity<?> doGet(
        @RequestParam Instant from,
        @RequestParam Instant to

    ) {
        LOG.info(">> GET /payments-summary");

        return ResponseEntity.ok(PAYMENT_SERVICE.getPayments(from, to));
    }

}
