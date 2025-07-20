package com.rinha.coffe.services;

import com.rinha.coffe.integrations.PaymentProcessorIntegration;
import com.rinha.coffe.models.Payment;
import com.rinha.coffe.models.dtos.PaymentCreateDTO;
import com.rinha.coffe.models.dtos.PaymentGetDTO;
import com.rinha.coffe.repositories.PaymentRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class PaymentService {

    private final PaymentRepository PAYMENT_REPOSITORY;

    public PaymentService(PaymentRepository paymentRepository) {
        this.PAYMENT_REPOSITORY = paymentRepository;
    }

    @Transactional
    public void createPaymentSync(PaymentCreateDTO dto){

        Payment payment = new Payment(dto);

        int path = PaymentProcessorIntegration.doPostPayment(
            payment.createSendDto()
        );

        payment.setSource(path);

        PAYMENT_REPOSITORY.save(payment);
    }

    public void createPaymentAsync(PaymentCreateDTO dto){
        doAsync(dto);
    }

    @Transactional
    @Async
    protected CompletableFuture<Void> doAsync(PaymentCreateDTO dto){

        Payment payment = new Payment(dto);

        int path = PaymentProcessorIntegration.doPostPayment(
                payment.createSendDto()
        );

        payment.setSource(path);

        PAYMENT_REPOSITORY.save(payment);

        return CompletableFuture.completedFuture(null);

    }

    public HashMap<String, PaymentGetDTO> getPayments(Instant from, Instant to){

        HashMap<String, PaymentGetDTO> hash_payments = new HashMap<>();

        List<Payment> payments = PAYMENT_REPOSITORY.getAllPaymentsFromTo(from, to);

        List<Payment> list_payment_default =
                payments.stream().
                        filter(payment -> payment.getSource() == "default")
                        .toList();

        List<Payment> list_payment_fallback =
                payments.stream().
                        filter(payment -> payment.getSource() != "default")
                        .toList();

        PaymentGetDTO payment_default = new PaymentGetDTO(
                list_payment_default.size(),
                list_payment_default.stream().mapToDouble(Payment::getAmount).sum()
        );

        PaymentGetDTO payment_fallback = new PaymentGetDTO(
                list_payment_fallback.size(),
                list_payment_fallback.stream().mapToDouble(Payment::getAmount).sum()
        );

        hash_payments.put("default", payment_default);
        hash_payments.put("fallback", payment_fallback);

        return hash_payments;
    }

}
