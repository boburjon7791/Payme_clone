package com.company.server.models;

import com.company.server.enums.Role;
import com.company.server.enums.Sex;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private Role role;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String secondPhoneNumber;
    private Sex sex;
    private Boolean active = true;

    private String nickname;

    private final UUID id = UUID.randomUUID();
    private final List<UserPaymentHistory> historyList = new ArrayList<>();
    private final List<Card> cards = new ArrayList<>();
    private LocalDate birthDate;
    private String pinCode;
    public User(String firstName, String lastName, String phoneNumber,
                 Sex sex, LocalDate birthDate, String pinCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.secondPhoneNumber = secondPhoneNumber;
        this.sex = sex;
        this.nickname = nickname;
        this.birthDate = birthDate;
        this.pinCode = pinCode;
        this.role = Role.CLIENT;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Role getRole() {
        return role;
    }

    public List<Card> getCards() {
        return cards;
    }
    public void addCard(Card card){
        this.cards.add(card);
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSecondPhoneNumber() {
        return secondPhoneNumber;
    }

    public void setSecondPhoneNumber(String secondPhoneNumber) {
        this.secondPhoneNumber = secondPhoneNumber;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public UUID getId() {
        return id;
    }

    public List<UserPaymentHistory> getHistoryList() {
        return historyList;
    }
    public void addHistoryList(UserPaymentHistory history){
         this.historyList.add(history);
    };

    public String getPinCode() {
        return pinCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;

        if (!getPhoneNumber().equals(user.getPhoneNumber())) return false;
        if (!getId().equals(user.getId())) return false;
        if (!getHistoryList().equals(user.getHistoryList())) return false;
        return getCards().equals(user.getCards());
    }

    @Override
    public int hashCode() {
        int result = getPhoneNumber().hashCode();
        result = 31 * result + getId().hashCode();
        result = 31 * result + getHistoryList().hashCode();
        result = 31 * result + getCards().hashCode();
        return result;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public String toString() {
        return "User{" +
                "role=" + role +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", secondPhoneNumber='" + secondPhoneNumber + '\'' +
                ", sex=" + sex +
                ", nickname='" + nickname + '\'' +
                ", id=" + id +
                ", historyList=" + historyList +
                ", cards=" + cards +
                ", pinCode=" + "****" +
                '}';
    }
}
