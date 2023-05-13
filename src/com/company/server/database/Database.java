package com.company.server.database;

import com.company.server.enums.Sex;
import com.company.server.models.Card;
import com.company.server.models.User;
import com.company.server.models.UserPaymentHistory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface Database {
  List<User> users = new ArrayList<>();
  List<UserPaymentHistory> commonHistory = new ArrayList<>();
  List<Card> cards = new ArrayList<>();
}
