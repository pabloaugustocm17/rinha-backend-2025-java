package com.rinha.coffe.events;

import com.rinha.coffe.events.dtos.PaymentSaveEvent;
import com.rinha.coffe.models.Payment;
import com.rinha.coffe.repositories.PaymentRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaymentSaveListener {

    private final PaymentRepository PAYMENT_REPOSITORY;

    public PaymentSaveListener(PaymentRepository paymentRepository) {
        this.PAYMENT_REPOSITORY = paymentRepository;
    }

    @EventListener
    @Async
    @Transactional
    public void onPaymentSaveCreated(PaymentSaveEvent event){

        Payment payment_save = event.payment();

        payment_save.setSource(event.path());

        PAYMENT_REPOSITORY.save(payment_save);
    }

}
