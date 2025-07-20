package com.rinha.coffe.models.dtos;

import java.util.UUID;

public record PaymentCreateDTO(
        UUID correlationId,
        Double amount
) {
}
