package com.rinha.coffe.events;

import com.rinha.coffe.events.dtos.PaymentRequestEvent;
import com.rinha.coffe.events.dtos.PaymentSaveEvent;
import com.rinha.coffe.integrations.PaymentProcessorIntegration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PaymentRequestListener {

    private final ApplicationEventPublisher PUBLISHER;

    public PaymentRequestListener(ApplicationEventPublisher publisher) {
        this.PUBLISHER = publisher;
    }

    @EventListener
    @Async
    public void onPaymentRequestCreated(PaymentRequestEvent event){

        int path = PaymentProcessorIntegration.doPostPayment(
            event.payment().createSendDto()
        );

        PUBLISHER.publishEvent(new PaymentSaveEvent(event.payment(), path));
    }
}
