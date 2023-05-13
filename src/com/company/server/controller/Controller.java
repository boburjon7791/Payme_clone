package com.company.server.controller;

import com.company.client.dto.CardDTO;
import com.company.client.dto.UserDTO;
import com.company.server.database.Database;
import com.company.server.models.Card;
import com.company.server.models.User;
import com.company.server.models.UserPaymentHistory;
import com.company.server.sevice.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    public User login(String phoneNumber, String pinCode) {
        try {
            AuthService service = new AuthService();
            return service.login(phoneNumber, pinCode);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Boolean register(UserDTO userDTO) {
       try {
           RegistrationService service = new RegistrationService();
           return service.registration(userDTO);
       }catch (Exception e){
           e.printStackTrace();
       }
       return null;
    }
    public Boolean addCard(User client, CardDTO cardDTO){
        UserService service = new UserService();
        return service.addCard(client, cardDTO);
    }
    public Boolean deleteCard(User client, Card card){
        UserService service = new UserService();
        return service.deleteCard(client, card);
    }
    public Boolean transition(User client, Card myCard, String friendCard, BigDecimal value) {
        TransactionService service = new TransactionService();
        int i = 0;
        List<Card> cards = Database.cards;
        for (; i < cards.size(); i++) {
            if(cards.get(i).getNumber().equals(friendCard)){
                break;
            }
        }
        return service.transfer(client, myCard, cards.get(i), value);
    }

    public Boolean editProfile(User sessionUser, UserDTO userDTO) {
        UserService service = new UserService();
        User user = new User(userDTO.firstName, userDTO.lastName, userDTO.phoneNumber, userDTO.sex,
                userDTO.birthDate,userDTO.pinCode);
        return service.editProfile(sessionUser, user, sessionUser.getPinCode());
    }

    public List<UserPaymentHistory> showHistory(User sessionUser) {
        UserService service = new UserService();
        try {
            return service.showHistories(sessionUser);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public User showMyInformation(User sessionUser) {
        UserService service = new UserService();
        try {
            return service.showMyInformation(sessionUser);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Card> showMyCards(User sessionUser) {
        UserService service = new UserService();
        List<Card> Cards = service.showMyCards(sessionUser);
        ArrayList<Card> cards = new ArrayList<>();
        Cards.forEach(card -> {
            if(card.isActive()){
                cards.add(card);
            }
        });
        return cards;
    }

    public List<UserPaymentHistory> showTransitions(User admin) {
        AdminService service = new AdminService();
        return service.showTransitions(admin);
    }

    public List<User> showUsers(User sessionUser) {
        AdminService service = new AdminService();
        return service.showUsers(sessionUser);
    }

    public List<Card> showCards(User admin) {
        AdminService service = new AdminService();
        return service.showCards(admin);
    }

    public Boolean blockUser(User admin,User client) {
        AdminService service = new AdminService();
        return service.block(admin, client);
    }

    public Boolean unblockUser(User admin, User client) {
        AdminService service = new AdminService();
        return service.unBlock(admin, client);
    }

    public BigDecimal showCommonBalance(User sessionUser) {
        PaymentService service = new PaymentService();
        return service.showBalance(sessionUser);
    }
}
