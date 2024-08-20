package com.Boujorno.adnanemanager.service.interfac;

import com.Boujorno.adnanemanager.dto.Response;
import com.Boujorno.adnanemanager.model.Booking;

public interface IBookingService {

    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);

}