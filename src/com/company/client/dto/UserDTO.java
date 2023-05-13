package com.company.client.dto;

import com.company.server.enums.Role;
import com.company.server.enums.Sex;

import java.time.LocalDate;

public class UserDTO {
    public String firstName;
    public String lastName;
    public Sex sex;
    public LocalDate birthDate;
    public String phoneNumber;
    public String pinCode;

    public UserDTO(String firstName, String lastName, Sex sex, LocalDate birthDate, String phoneNumber, String pinCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.pinCode = pinCode;
    }
}
