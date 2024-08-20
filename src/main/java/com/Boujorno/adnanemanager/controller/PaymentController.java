package com.Boujorno.adnanemanager.controller;

import com.Boujorno.adnanemanager.dto.PaymentInfoRequest;
import com.Boujorno.adnanemanager.model.Payment;
import com.Boujorno.adnanemanager.service.impl.PaymentService;
import com.Boujorno.adnanemanager.utils.JWTUtils;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/payment/secure")
public class PaymentController {

    private final PaymentService paymentService;
    private final JWTUtils jwtUtils;

    @Autowired
    public PaymentController(PaymentService paymentService, JWTUtils jwtUtils) {
        this.paymentService = paymentService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoRequest paymentInfoRequest)
            throws StripeException {

        PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentInfoRequest);
        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

    @PutMapping("/payment-complete")
    public ResponseEntity<String> stripePaymentComplete(@RequestHeader(value="Authorization") String token)
            throws Exception {
        String userEmail = jwtUtils.extractUsername(token.substring(7)); // Extraire le token sans le préfixe "Bearer "
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        return paymentService.stripePayment(userEmail);
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long paymentId) {
        Optional<Payment> payment = paymentService.findById(paymentId);
        if (payment.isPresent()) {
            return ResponseEntity.ok(payment.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/payment/update/{paymentId}")
    public ResponseEntity<Payment> updatePayment(
            @PathVariable Long paymentId,
            @RequestBody PaymentInfoRequest paymentInfoRequest) {

        Optional<Payment> payment = paymentService.findById(paymentId);

        if (payment.isPresent()) {
            Payment updatedPayment = paymentService.updatePayment(payment.get(), paymentInfoRequest);
            return ResponseEntity.ok(updatedPayment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/payment/delete/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long paymentId) {
        Optional<Payment> payment = paymentService.findById(paymentId);

        if (payment.isPresent()) {
            paymentService.deletePayment(paymentId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
