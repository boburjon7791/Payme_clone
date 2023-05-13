package com.company.client.menu;

import com.company.client.dto.CardDTO;
import com.company.client.dto.UserDTO;
import com.company.server.controller.Controller;
import com.company.server.database.Database;
import com.company.server.enums.Role;
import com.company.server.enums.Sex;
import com.company.server.models.Card;
import com.company.server.models.User;
import com.company.server.models.UserPaymentHistory;
import com.company.server.sevice.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.company.client.Util.MyScanner.scannerNUM;
import static com.company.client.Util.MyScanner.scannerSTR;

public class menu {
    static {
        User admin = new User("Boburjon", "Soliyev", "+998999057761", Sex.MALE,
                LocalDate.of(2001, 5, 19), "0103");
        admin.setRole(Role.ADMIN);
        User client = new User("Ali", "Valiyev", "+998943654648", Sex.MALE,
                LocalDate.of(2008, 4, 8), "4444");
        User client2 = new User("Eshon", "Valiyev", "+998943654785", Sex.MALE,
                LocalDate.of(2008, 4, 8), "4444");
        Database.users.add(admin);
        Database.users.add(client);
        Database.users.add(client2);
        Card card = new Card("HAMKORBANK", "7845 4567 8974 1456", "ALI VALIYEV", "4444");
        Card card2 = new Card("ANORBANK", "7856 4164 8978 1456", "ESHON VALIYEV", "4444");
        client.addCard(card);
        card.setBalance(new BigDecimal(20000));
        client2.addCard(card2);
        card2.setBalance(new BigDecimal(45500));
        Database.cards.add(card);
        Database.cards.add(card2);
    }
    public static void main(String[] args) {
            while (true) {
                try {
                Database.users.forEach(System.out::println);
                System.out.println();
                Database.cards.forEach(System.out::println);
                System.out.println();
                System.out.println("""
                        1. Login
                        2. Register
                                        
                        0. Quit
                        """);
                int num = scannerNUM.nextInt();
                if (num == 0) {
                    break;
                }
                switch (num) {
                    case 1 -> loginPage();
                    case 2 -> registerPage();
                }
                    scannerSTR.nextLine();
                    System.out.println("\n\n\n\n\n\n\n");
            }catch (Exception e){
                    e.printStackTrace();
           }
        }
    }

    private static void registerPage() {
        System.out.print("First name - ");
        String firstName = scannerSTR.nextLine();

        System.out.print("Last name - ");
        String lastName = scannerSTR.nextLine();

        System.out.print("Select sex\n1. Male\n2.Female");
        int select = scannerNUM.nextInt();
        Sex sex = null;
        switch (select){
            case 1 -> sex = Sex.MALE;
            case 2 -> sex = Sex.FEMALE;
        }

        System.out.print("Year - ");
        int year = scannerNUM.nextInt();

        System.out.print("Month - ");
        int month = scannerNUM.nextInt();

        System.out.print("Day - ");
        int date = scannerNUM.nextInt();
        LocalDate birthdate = LocalDate.of(year, month, date);

        System.out.print("Pin code - ");
        String phoneNumber = scannerSTR.nextLine();
        String pinCode = scannerSTR.nextLine();

        UserDTO userDTO = new UserDTO(firstName,lastName,sex,birthdate,phoneNumber,pinCode);
        Controller controller = new Controller();
        Boolean registered = controller.register(userDTO);
        if (registered) {
            System.out.println("Success");
            loginPage();
        }else {
            System.out.println("Failed");
        }
    }

    private static void loginPage() {
        System.out.println("Input phone number");
        String phoneNumber = scannerSTR.nextLine();

        System.out.println("Input pin code");
        String pinCode = scannerSTR.nextLine();

        Controller controller = new Controller();
        User sessionUser = controller.login(phoneNumber, pinCode);
        if(sessionUser != null){
            System.out.println("Login succeed");
            switch (sessionUser.getRole()) {
                case ADMIN -> adminPage(sessionUser);
                case CLIENT -> clientPage(sessionUser);
            }
        }else {
            System.out.println("Login error");
        }

    }

    private static void clientPage(User sessionUser) {
        while (true) {
            System.out.println("""
                    Select option
                    1. Transition
                    2. Edit account
                    3. Show history
                    4. My information
                    5. My cards
                    6. Add or delete card
                                        
                    0. Logout
                    """);
            int n = scannerNUM.nextInt();
            if (n == 0) {
                break;
            }
            switch (n) {
                case 1 -> {
                    Controller controller = new Controller();
                    PaymentService service = new PaymentService();
                    List<Card> cards = service.showMyCards(sessionUser);
                    if (cards == null || cards.isEmpty()) {
                        System.out.println("You have not any card");
                        break;
                    }
                    for (int i = 0; i < cards.size(); i++) {
                        System.out.println(i + ". " + cards.get(i).getBankName()+", "+cards.get(i).getBalance());
                    }
                    System.out.println("Select card");
                    Card myCard = cards.get(scannerNUM.nextInt());
                    System.out.println("Friend's card number");
                    String friendCard = scannerSTR.nextLine();
                    System.out.print("Summa - ");
                    BigDecimal value = scannerNUM.nextBigDecimal();
                    Boolean transition = controller.transition(sessionUser, myCard, friendCard, value);
                    if (transition) {
                        System.out.println("Success");
                    } else {
                        System.out.println("Failed");
                    }
                    System.out.println("Number = "+myCard.getNumber());
                    System.out.println("Bank name = "+myCard.getBankName());
                    System.out.println("Balance = "+myCard.getBalance());
                }
                case 2 -> {
                    System.out.println("First name - (" + sessionUser.getFirstName() + ")");
                    String firstName = scannerSTR.nextLine();

                    System.out.println("Last name - (" + sessionUser.getLastName() + ")");
                    String lastName = scannerSTR.nextLine();

                    System.out.println("Your sex - " + sessionUser.getSex() + "1. Male\n2. Female");
                    int s = scannerNUM.nextInt();
                    Sex sex = null;
                    switch (s) {
                        case 1 -> sex = Sex.MALE;
                        case 2 -> sex = Sex.FEMALE;
                    }
                    System.out.println(sessionUser.getBirthDate());
                    System.out.print("Year - ");
                    int year = scannerNUM.nextInt();
                    System.out.print("Month - ");
                    int month = scannerNUM.nextInt();
                    System.out.print("Day - ");
                    int day = scannerNUM.nextInt();
                    LocalDate birthDate = LocalDate.of(year, month, day);

                    System.out.println("Phone number - " + sessionUser.getPhoneNumber());
                    String phoneNumber = scannerSTR.nextLine();

                    System.out.print("Old pin code - ");
                    String oldPinCode = scannerSTR.nextLine();
                    System.out.print("New pin code - ");
                    String newPinCode = scannerSTR.nextLine();

                    if (!newPinCode.equals(oldPinCode)) {
                        break;
                    }

                    UserDTO userDTO = new UserDTO(firstName, lastName, sex, birthDate, phoneNumber, newPinCode);
                    Controller controller = new Controller();
                    if (controller.editProfile(sessionUser, userDTO)) {
                        System.out.println("Success");
                    } else {
                        System.out.println("Failed");
                    }
                }
                case 3 -> {
                    Controller controller = new Controller();
                    List<UserPaymentHistory> histories = controller.showHistory(sessionUser);
                    if (histories == null) {
                        System.out.println("Error");
                        break;
                    }
                    if (histories.isEmpty()) {
                        System.out.println("List is empty");
                        break;
                    }
                    histories.forEach(history -> {
                        sessionUser.getCards().forEach(card -> {
                            if (history.getTakerCard().getId().equals(card.getId())){
                                System.out.println("+ " + history.getSumma());
                                if (history.getStatus()) {
                                    System.out.println("Success transition");
                                }else {
                                    System.out.println("Failure transition");
                                }
                                System.out.println("Sender card = " + history.getSenderCard().getNumber());
                            } else if (history.getSenderCard().getId().equals(card.getId())) {
                                System.out.println("- " + history.getSumma());
                                if (history.getStatus()) {
                                    System.out.println("Success transition");
                                }else {
                                    System.out.println("Failure transition");
                                }
                                System.out.println("Taker card = " + history.getTakerCard().getNumber());
                            }
                        });
                    });
                }
                case 4 -> {
                    Controller controller = new Controller();
                    User user = controller.showMyInformation(sessionUser);
                    if (user == null) {
                        System.out.println("Error");
                        break;
                    }
                    System.out.println("Fully name = " + user.getLastName()+" "+user.getFirstName());
                    System.out.println("Birthdate = " + user.getBirthDate());
                    System.out.println("Sex = " + user.getSex());
                }
            case 5 -> {
                    Controller controller = new Controller();
                List<Card> cards = controller.showMyCards(sessionUser);
                if (cards == null) {
                    System.out.println("Error");
                    break;
                }
                if(cards.isEmpty()){
                    System.out.println("List is empty");
                    break;
                }
                System.out.println("My common balance = "+controller.showCommonBalance(sessionUser));
                System.out.println();
                cards.forEach(card -> {
                    System.out.println("Bank name         = "+card.getBankName());
                    System.out.println("Card's number     = "+card.getNumber());
                    System.out.println("Card's valid time = "+card.getValidTime());
                    System.out.println("Card's balance    = "+card.getBalance());
                    System.out.println("***");
                });
            }
            case 6 -> {

                while (true) {
                    System.out.println("""
                                1. Add card
                                2. Delete card
                                
                                0. Back
                                """);
                    String str = scannerSTR.nextLine();
                    if(str.equals("0")){
                        break;
                    }
                    switch (str) {
                        case "1" -> {
                            Controller controller = new Controller();

                            System.out.print("Bank name = ");
                            String bankName = scannerSTR.nextLine();

                            System.out.print("First name = ");
                            String firstName = scannerSTR.nextLine();

                            System.out.print("Last name = ");
                            String lastName = scannerSTR.nextLine();

                            System.out.print("Pin code = ");
                            String pinCode = scannerSTR.nextLine();

                            LocalDate validDate = LocalDate.now().plusYears(2);
                            CardDTO cardDTO = new CardDTO(bankName,null,firstName,
                                    lastName,pinCode,validDate);
                            Boolean added = controller.addCard(sessionUser, cardDTO);
                            if (added == null) {
                                System.out.println("Error");
                                break;
                            }
                            if (added) {
                                System.out.println("Success");
                                break;
                            }
                            System.out.println("Error");
                        }
                        case "2" -> {
                            Controller controller = new Controller();
                            for (int i = 0; i < sessionUser.getCards().size(); i++) {
                                System.out.println(i+". "+sessionUser.getCards().get(i).getBankName()+", "
                                +sessionUser.getCards().get(i).getNumber());
                                System.out.println();
                            }
                            System.out.println("Select");
                            int select = scannerNUM.nextInt();
                            Boolean deleted = controller.deleteCard(sessionUser, sessionUser.getCards().get(select));
                            if (deleted) {
                                System.out.println("Deleted");
                                break;
                            }
                            System.out.println("Failed");
                        }
                            default -> System.out.println("Error");
                        }
                    }
                }
            }
            System.out.println();
        }
    }

    private static void adminPage(User sessionUser) {
        while (true) {
            System.out.println("""
                    1. Show all transitions
                    2. Show all users
                    3. Show all cards
                    4. Block any user
                    5. Unblock any user
                                    
                    0. Logout
                    """);
            int n = scannerNUM.nextInt();
            if (n == 0) {
                break;
            }
            switch (n) {
                case 1 -> {
                    Controller controller = new Controller();
                    List<UserPaymentHistory> histories = controller.showTransitions(sessionUser);
                    if (histories == null || histories.isEmpty()) {
                        System.out.println("List is empty");
                        break;
                    }
                    histories.forEach(System.out::println);
                }
                case 2 -> {
                    Controller controller = new Controller();
                    List<User> users = controller.showUsers(sessionUser);
                    if (users == null || users.isEmpty()) {
                        System.out.println("List is empty");
                        break;
                    }
                    users.forEach(System.out::println);
                }
                case 3 -> {
                    Controller controller = new Controller();
                    List<Card> cards = controller.showCards(sessionUser);
                    if (cards == null || cards.isEmpty()) {
                        System.out.println("List is empty");
                        break;
                    }
                    cards.forEach(System.out::println);
                }
                case 4 -> {
                    Controller controller = new Controller();
                    List<User> users = controller.showUsers(sessionUser);
                    for (int i = 0; i < users.size(); i++) {
                        System.out.println(i+". "+users.get(i));
                    }
                    System.out.println("Select");
                    int select = scannerNUM.nextInt();
                    Boolean blocked = controller.blockUser(sessionUser, users.get(select));
                    if (blocked == null) {
                        System.out.println("Error");
                        break;
                    }
                    if (blocked) {
                        System.out.println("Blocked");
                        break;
                    }
                    System.out.println("Failed");
                }
                case 5 -> {
                    Controller controller = new Controller();
                    List<User> users = controller.showUsers(sessionUser);
                    for (int i = 0; i < users.size(); i++) {
                        System.out.println(i+". "+users.get(i));
                    }
                    System.out.println("Select");
                    int select = scannerNUM.nextInt();
                    Boolean unblocked = controller.unblockUser(sessionUser, users.get(select));
                    if (unblocked == null) {
                        System.out.println("Error");
                        break;
                    }
                    if (unblocked) {
                        System.out.println("Unblocked");
                        break;
                    }
                    System.out.println("Failed");
                }
            }
        }
    }
}
