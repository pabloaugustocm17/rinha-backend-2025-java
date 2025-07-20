package com.rinha.coffe.models.dtos;

import java.time.Instant;
import java.util.UUID;

public record PaymentProcessorSendDTO (
        UUID correlationId,
        Double amount,
        Instant requestedAt
){
}
