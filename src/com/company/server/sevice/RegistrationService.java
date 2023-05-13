package com.company.server.sevice;

import com.company.client.dto.UserDTO;
import com.company.server.database.Database;
import com.company.server.models.User;

public class RegistrationService {
    public Boolean registration(UserDTO userDTO){
        if (userDTO == null) {
            return null;
        }
        if (userDTO.firstName.isBlank() ||
                userDTO.lastName.isBlank() ||
                userDTO.phoneNumber.isBlank()) {
            return false;
        }
        for (User user : Database.users) {
            if (user.getPhoneNumber().equals(userDTO.phoneNumber)){
                return false;
            }
        }
        User user = new User(userDTO.firstName, userDTO.lastName, userDTO.phoneNumber,
                userDTO.sex, userDTO.birthDate, userDTO.pinCode);
        Database.users.add(user);
        return true;
    }
}
