package com.rinha.coffe.events.dtos;

import com.rinha.coffe.models.Payment;

public record PaymentSaveEvent(
        Payment payment,
        int path
) {
}
