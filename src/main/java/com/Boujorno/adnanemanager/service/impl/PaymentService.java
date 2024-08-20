package com.Boujorno.adnanemanager.service.impl;

import com.Boujorno.adnanemanager.model.Booking;
import com.Boujorno.adnanemanager.model.Payment;
import com.Boujorno.adnanemanager.model.Room;
import com.Boujorno.adnanemanager.model.User;
import com.Boujorno.adnanemanager.repository.BookingRepository;
import com.Boujorno.adnanemanager.repository.PaymentRepository;
import com.Boujorno.adnanemanager.repository.RoomRepository;
import com.Boujorno.adnanemanager.repository.UserRepository;
import com.Boujorno.adnanemanager.dto.PaymentInfoRequest;
import com.Boujorno.adnanemanager.service.interfac.IPaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class PaymentService implements IPaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, BookingRepository bookingRepository, RoomRepository roomRepository, UserRepository userRepository, @Value("${stripe.key.secret}") String secretKey) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        Stripe.apiKey = secretKey;
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfoRequest paymentInfoRequest) throws StripeException {
        // Créer l'intention de paiement Stripe
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfoRequest.getAmount());
        params.put("currency", paymentInfoRequest.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        // Récupérer les entités associées
        User user = userRepository.findByEmail(paymentInfoRequest.getReceiptEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        Room room = roomRepository.findById(paymentInfoRequest.getRoomId()).orElseThrow(() -> new RuntimeException("Room not found"));
        Booking booking = bookingRepository.findByBookingConfirmationCode(paymentInfoRequest.getBookingConfirmationCode()).orElseThrow(() -> new RuntimeException("Booking not found"));

        // Créer l'objet Payment et établir les relations
        Payment payment = new Payment();
        payment.setAmount(new BigDecimal(paymentInfoRequest.getAmount()).movePointLeft(2)); // Convertir en unité monétaire
        payment.setUser(user);
        payment.setRoom(room);
        payment.setBooking(booking);
        payment.setBookingConfirmationCode(paymentInfoRequest.getBookingConfirmationCode());
        payment.setRoomType(room.getRoomType());

        // Sauvegarder le paiement
        paymentRepository.save(payment);

        return paymentIntent;
    }

    @Override
    public ResponseEntity<String> stripePayment(String userEmail) throws Exception {
        Payment payment = paymentRepository.findByUserEmail(userEmail).orElseThrow(() -> new Exception("Payment information is missing"));

        // Marquer le paiement comme complété ou gérer la logique ici
        payment.setAmount(BigDecimal.ZERO);
        paymentRepository.save(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public Optional<Payment> findById(Long paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public Payment updatePayment(Payment payment, PaymentInfoRequest paymentInfoRequest) {
        payment.setAmount(new BigDecimal(paymentInfoRequest.getAmount()).movePointLeft(2));
        payment.setRoomType(paymentInfoRequest.getRoomType());
        payment.setBookingConfirmationCode(paymentInfoRequest.getBookingConfirmationCode());
        return paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(Long paymentId) {
        paymentRepository.deleteById(paymentId);
    }
}
