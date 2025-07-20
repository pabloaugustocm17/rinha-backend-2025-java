package com.rinha.coffe.models.dtos;

public record PaymentGetDTO(
        int totalRequests,
        double totalAmount
) {
}
