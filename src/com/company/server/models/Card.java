package com.company.server.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Card {
    private final String bankName;
    private String number;
    private String fullName;
    private final LocalDate validTime;
    private String pinCode;
    private BigDecimal balance;
    private boolean active;
    private final UUID id = UUID.randomUUID();


    public Card(String bankName, String number, String fullName, String pinCode) {
        this.bankName = bankName;
        this.number = number;
        this.fullName = fullName;
        this.validTime = LocalDate.now().plusYears(4);
        this.pinCode = pinCode;
        this.active = true;
        this.balance = BigDecimal.valueOf(Long.parseLong("0"));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card card)) return false;

        if (!getNumber().equals(card.getNumber())) return false;
        return getId().equals(card.getId());
    }

    @Override
    public int hashCode() {
        int result = getNumber().hashCode();
        result = 31 * result + getId().hashCode();
        return result;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getBankName() {
        return bankName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getValidTime() {
        return validTime;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Card{" +
                "bankName='" + bankName + '\'' +
                ", number='" + number + '\'' +
                ", fullName='" + fullName + '\'' +
                ", validTime=" + validTime +
                ", pinCode=" + pinCode +
                ", balance=" + balance +
                ", id=" + id +
                '}';
    }
}
