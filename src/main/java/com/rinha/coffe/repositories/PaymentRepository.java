package com.rinha.coffe.repositories;

import com.rinha.coffe.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    @Query(
        "SELECT P FROM Payment AS P WHERE P.requestedAt BETWEEN :from AND :to"
    )
    List<Payment> getAllPaymentsFromTo(
        @Param("from")Instant from,
        @Param("to") Instant to
    );

}
