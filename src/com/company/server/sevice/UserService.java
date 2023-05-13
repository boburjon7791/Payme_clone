package com.company.server.sevice;

import com.company.client.dto.CardDTO;
import com.company.server.database.Database;
import com.company.server.enums.Role;
import com.company.server.models.Card;
import com.company.server.models.User;
import com.company.server.models.UserPaymentHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserService {
    public Boolean addCard(User client, CardDTO cardDTO){
        try {
            if (client == null ||
                    !client.getRole().equals(Role.CLIENT)) {
                return null;
            }
            if (!client.getActive()) {
                return null;
            }
            if(cardDTO == null){
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Random random = new Random();
        StringBuilder numberBuild = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int rand = random.nextInt(1000,9999);
            numberBuild.append(rand);
            numberBuild.append(" ");
        }
        numberBuild.deleteCharAt(numberBuild.length() - 1);
        Card card = new Card(cardDTO.bankName.toUpperCase(), String.valueOf(numberBuild), cardDTO.lastName.toUpperCase() +
                " " + cardDTO.firstName.toUpperCase(), cardDTO.pinCode);
        Database.cards.add(card);
        client.addCard(card);
        return true;
    }
    public Boolean deleteCard(User client, Card card){
        try {
            if (client == null ||
                    !client.getRole().equals(Role.CLIENT)) {
                return null;
            }
            if (!client.getActive()) {
                return null;
            }
            for (Card clientCard : client.getCards()) {
                if (clientCard.equals(card)) {
                    card.setActive(false);
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public List<UserPaymentHistory> showHistories(User client){
        try {
            if (client == null) {
                return null;
            }
            if (!client.getRole().equals(Role.CLIENT)) {
                return null;
            }
            if (!client.getActive()) {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if (!client.getRole().equals(Role.CLIENT)) {
            return new ArrayList<>();
        }
        for (User user : Database.users) {
            if (client.getId().equals(user.getId())){
                return new ArrayList<>(user.getHistoryList());
            }
        }
        return new ArrayList<>();
    }
    public User showMyInformation(User client){
        try {
            if (client == null) {
                return null;
            }
            if (!client.getActive()) {
                return null;
            }
            if (!client.getRole().equals(Role.CLIENT)) {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if (!client.getRole().equals(Role.CLIENT)) {
            return null;
        }
        for (User user : Database.users) {
            if (client.getId().equals(user.getId())){
                return new User(user.getFirstName(), user.getLastName(),
                        user.getPhoneNumber(),user.getSex(),
                        user.getBirthDate(), user.getPinCode());
            }
        }
        return null;
    }
    public Boolean editProfile(User user,User editedUser,String oldPinCode){
        try {
            if (user == null) {
                return null;
            }
            if (!user.getActive()) {
                return null;
            }
            if (!user.getRole().equals(Role.CLIENT)) {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if (!user.getRole().equals(Role.CLIENT)) {
            return null;
        }
        for (User user1 : Database.users) {
            if(user1.getId().equals(user.getId())){
                if(user1.getPinCode().equals(oldPinCode)){
                    user1 = editedUser;
                    return true;
                }
            }
        }
        return false;
    }

    public List<Card> showMyCards(User sessionUser) {
        if (sessionUser == null ||
                !sessionUser.getRole().equals(Role.CLIENT)) {
            return null;
        }
        AtomicBoolean finded = new AtomicBoolean(true);
        Database.users.forEach(user -> {
            if(user.getId().equals(sessionUser.getId())){
                finded.set(true);
            }
        });
        if(finded.get()){
            return sessionUser.getCards();
        }
        return null;
    }
}
