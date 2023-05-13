package com.company.server.sevice;

import com.company.server.database.Database;
import com.company.server.models.User;

public class AuthService {
    public User login(String phoneNumber,String pinCode){
        if(phoneNumber == null || phoneNumber.isBlank()
        || pinCode == null || pinCode.isBlank()){
            try {
                return null;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        for (User user : Database.users) {
            if(user.getPhoneNumber().equals(phoneNumber) &&
            user.getPinCode().equals(pinCode)){
                return user;
            }
        }
            return null;


    }
}
