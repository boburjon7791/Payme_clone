package com.company.server.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserPaymentHistory {
    private Card senderCard;
    private Card takerCard;
    private BigDecimal commission;
    private BigDecimal summa;
    private final String comment;
    private final UUID id = UUID.randomUUID();
    private boolean status;
    private final LocalDateTime localDateTime = LocalDateTime.now();

    public UserPaymentHistory(Card senderCard, Card takerCard, BigDecimal commission, BigDecimal summa, String comment, boolean status) {
        this.senderCard = senderCard;
        this.takerCard = takerCard;
        this.commission = commission;
        this.summa = summa;
        this.comment = comment;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPaymentHistory that)) return false;

        if (!getSenderCard().equals(that.getSenderCard())) return false;
        if (!getTakerCard().equals(that.getTakerCard())) return false;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        int result = getSenderCard().hashCode();
        result = 31 * result + getTakerCard().hashCode();
        result = 31 * result + getId().hashCode();
        return result;
    }

    public BigDecimal getSumma() {
        return summa;
    }

    public void setSumma(BigDecimal summa) {
        this.summa = summa;
    }

    public boolean isStatus() {
        return status;
    }

    public Card getSenderCard() {
        return senderCard;
    }

    public void setSenderCard(Card senderCard) {
        this.senderCard = senderCard;
    }

    public Card getTakerCard() {
        return takerCard;
    }

    public void setTakerCard(Card takerCard) {
        this.takerCard = takerCard;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public String getComment() {
        return comment;
    }

    public UUID getId() {
        return id;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public String toString() {
        return "UserPaymentHistory{" +
                "senderCard=" + senderCard +
                ", takerCard=" + takerCard +
                ", commission=" + commission +
                ", summa=" + summa +
                ", comment='" + comment + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
