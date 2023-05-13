package com.company.server.sevice;

import com.company.server.database.Database;
import com.company.server.enums.Role;
import com.company.server.models.Card;
import com.company.server.models.User;
import com.company.server.models.UserPaymentHistory;

import java.util.ArrayList;
import java.util.List;

public class AdminService {
    public List<User> showUsers(User admin){
        try {
            if (admin == null || !admin.getRole().equals(Role.ADMIN)
                    || !admin.getActive()) {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>(Database.users);
    }
    public List<Card> showCards(User admin){
        try {
            if (admin == null || !admin.getRole().equals(Role.ADMIN)
                    || !admin.getActive()) {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>(Database.cards);
    }
    public List<UserPaymentHistory> showHistory(User admin){
        try {
            if (admin == null || !admin.getRole().equals(Role.ADMIN)
                    || !admin.getActive()) {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>(Database.commonHistory);
    }
    public Boolean block(User admin,User client){
        try {
            if (admin == null || !admin.getRole().equals(Role.ADMIN)
                    || !admin.getActive()) {
                return null;
            }
            if(client == null || client.getRole().equals(Role.ADMIN)){
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        boolean have = false;
        for (User user : Database.users) {
            if (client.getId().equals(user.getId())){
                have = true;
                break;
            }
        }
        if(!have){
            return false;
        }
        if (!client.getActive()) {
            client.setActive(false);
            return true;
        }
        return false;
    }

    public Boolean unBlock(User admin,User client){
        try {
            if (admin == null || !admin.getRole().equals(Role.ADMIN)
                    || !admin.getActive()) {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        boolean have = false;
        for (User user : Database.users) {
            if (client.getId().equals(user.getId())){
                have = true;
                break;
            }
        }
        if (!have){
            return false;
        }
        if (client.getActive()) {
            client.setActive(true);
            return true;
        }
        return false;
    }

    public List<UserPaymentHistory> showTransitions(User admin) {
        try {
            if (admin == null || !admin.getRole().equals(Role.ADMIN)
                    || !admin.getActive()) {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Database.commonHistory;
    }
}
