package com.company.server.sevice;

import com.company.server.database.Database;
import com.company.server.models.Card;
import com.company.server.models.User;
import com.company.server.models.UserPaymentHistory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicBoolean;

public class  TransactionService {
    public synchronized Boolean transfer(User client,Card sender, Card taker, BigDecimal sum){
        try {
            if (client == null || sender == null ||
                    taker == null || sum == null) {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if (!(sender.getValidTime().getYear()>=LocalDate.now().getYear() &&
        sender.getValidTime().getMonth().getValue()>=LocalDate.now().getMonthValue())) {
            client.addHistoryList(new UserPaymentHistory(sender,taker,new BigDecimal(0),sum, "",false));
            return false;
        }
        if(!sender.isActive() || !taker.isActive()){
            client.addHistoryList(new UserPaymentHistory(sender,taker,new BigDecimal(0),sum, "",true));
            return false;
        }
        boolean card1 = false;
        boolean card2 = false;
        for (Card card : Database.cards) {
            if(card.equals(sender)){
                card1 = true;
            }
            if(card.equals(taker)){
                card2 = true;
            }
        }
        if(!(card1 && card2)){
            client.addHistoryList(new UserPaymentHistory(sender,taker,new BigDecimal(0),sum, "",true));
            return false;
        }
        if (sum.compareTo(sender.getBalance()) > 0){
            client.addHistoryList(new UserPaymentHistory(sender,taker,new BigDecimal(0),sum, "",true));
            return false;
        }
        BigDecimal subtract = sender.getBalance().subtract(sum);
        sender.setBalance(subtract);

        BigDecimal add = taker.getBalance().add(sum);
        taker.setBalance(add);
        AtomicBoolean c1 = new AtomicBoolean(false);
        Database.users.forEach(user -> {
            user.getCards().forEach(card -> {
                if (card.getId().equals(taker.getId())) {
                    c1.set(true);
                    client.addHistoryList(new UserPaymentHistory(sender,taker,new BigDecimal(0),sum, "",true));
                    user.addHistoryList(new UserPaymentHistory(sender,taker,new BigDecimal(0),sum, "",true));
                }
            });
        });
        if (c1.get()){
            return true;
        }
        client.addHistoryList(new UserPaymentHistory(sender,taker,new BigDecimal(0),sum, "",false));
        return false;
    }
}
