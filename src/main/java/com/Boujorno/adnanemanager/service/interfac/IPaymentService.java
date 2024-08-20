package com.Boujorno.adnanemanager.service.interfac;

import com.Boujorno.adnanemanager.dto.PaymentInfoRequest;
import com.Boujorno.adnanemanager.model.Payment;
import com.stripe.model.PaymentIntent;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IPaymentService {
    PaymentIntent createPaymentIntent(PaymentInfoRequest paymentInfoRequest) throws Exception;
    ResponseEntity<String> stripePayment(String userEmail) throws Exception;
    Optional<Payment> findById(Long paymentId);
    Payment updatePayment(Payment payment, PaymentInfoRequest paymentInfoRequest);  // Assurez-vous que cette méthode est bien présente
    void deletePayment(Long paymentId);  // Assurez-vous que cette méthode est bien présente
}
