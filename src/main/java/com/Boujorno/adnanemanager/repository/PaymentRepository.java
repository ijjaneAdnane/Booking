package com.Boujorno.adnanemanager.repository;

import com.Boujorno.adnanemanager.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByUserEmail(String userEmail);
}
