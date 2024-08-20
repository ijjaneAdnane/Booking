package com.Boujorno.adnanemanager.dto;

import lombok.Data;

@Data
public class PaymentInfoRequest {

    private int amount;
    private String currency;
    private String receiptEmail;
    private Long roomId;
    private String bookingConfirmationCode;
    private String roomType;
}
