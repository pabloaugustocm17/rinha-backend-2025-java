package com.rinha.coffe.models;

import com.rinha.coffe.models.dtos.PaymentCreateDTO;
import com.rinha.coffe.models.dtos.PaymentProcessorSendDTO;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Table(name = "tb_payment")
@Entity
public class Payment {

    @Id
    private UUID correlationId;

    @Getter
    private Double amount;

    private Instant requestedAt;

    @Getter
    private String source;

    public Payment(PaymentCreateDTO dto) {
        this.correlationId = dto.correlationId();
        this.amount = dto.amount();
        this.requestedAt = Instant.now();
    }

    public Payment() {
    }

    public PaymentProcessorSendDTO createSendDto(){
        return new PaymentProcessorSendDTO(
            this.correlationId, this.amount, this.requestedAt
        );
    }

    public void setSource(int path){
        this.source = path == 1 ? "default" : "fallback";
    }
}
