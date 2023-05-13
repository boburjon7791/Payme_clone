package com.company.client.dto;

import com.company.server.models.Card;

import java.time.LocalDate;

public class CardDTO {
    public String bankName;
    public String number;
    public String firstName;
    public String lastName;
    public String pinCode;
    public LocalDate validDate;

    public CardDTO(String bankName, String number, String firstName, String lastName, String pinCode, LocalDate validDate) {
        this.bankName = bankName;
        this.number = number;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pinCode = pinCode;
        this.validDate = validDate;
    }
}
