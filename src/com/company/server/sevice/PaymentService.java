package com.company.server.sevice;

import com.company.server.database.Database;
import com.company.server.enums.Role;
import com.company.server.models.Card;
import com.company.server.models.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    public BigDecimal showBalance(User client){
        try {
            if (client == null) {
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
                BigDecimal sum = new BigDecimal("0");
                for (Card card : user.getCards()) {
                    sum = sum.add(card.getBalance());
                }
                return sum;
            }
        }
        return null;
    }
    public List<Card> showMyCards(User client){
        try {
            if (client == null) {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if (!client.getRole().equals(Role.CLIENT)) {
            return new ArrayList<>();
        }
        boolean finded = false;
        for (User user : Database.users) {
            if (client.getId().equals(user.getId())){
                finded = true;
                break;
            }
        }

        ArrayList<Card> list = new ArrayList<>();
        client.getCards().forEach(card -> {
            if(card.isActive()){
                list.add(card);
            }
        });
        return list;
    }
}
