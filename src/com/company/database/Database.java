package com.company.database;

import com.company.enums.UserRole;
import com.company.model.*;

import java.time.LocalDate;
import java.util.*;

public interface Database {

    List<User> userList = new ArrayList<>();
    List<MobileOperator> mobileOperatorList = new ArrayList<>();
    List<Tariff> tariffList = new ArrayList<>();
    List<SimCard> simCardList = new ArrayList<>();
    List<CallHistory> callHistoryList = new ArrayList<>();
    List<SmsHistory> smsHistoryList = new ArrayList<>();

    List<String> phoneNumbers = new ArrayList<>();


    static void loadData(){

        MobileOperator ucell = new MobileOperator("Ucell", new ArrayList<>(Arrays.asList(93, 94)), 60);
        MobileOperator beeline = new MobileOperator("Beeline", new ArrayList<>(Arrays.asList(90, 91)), 40);
        MobileOperator ums = new MobileOperator("UMS", new ArrayList<>(Arrays.asList(97, 88)), 50);
        MobileOperator uzMobile = new MobileOperator("UzMobile", new ArrayList<>(Arrays.asList(95, 99)), 40);

        Collections.addAll(mobileOperatorList, ucell, beeline, uzMobile, ums);

        Tariff active40 = new Tariff("Active 40", ucell, 40000, 3000, 10000, 500,
                15, 20, 5);

        Tariff special35 = new Tariff("Speacial 35", ucell, 35000, 2000, 9000, 1000,
                5, 5, 5);

        Tariff simple10 = new Tariff("Oddiy 10", uzMobile, 15000, 1000, 8000, 200,
                10, 10, 10);

        Tariff super10 = new Tariff("Zo'r 10", beeline, 20_000, 500, 5000, 100,
                10, 10, 10);

        Tariff noComment = new Tariff("Gap yo'q", ums, 70_000, 3500, 50000, 1500,
                15, 25, 20);


        Collections.addAll(tariffList, active40, simple10, super10, noComment, special35);

        User user1 = new User("Jahongir", "Atametov", "AA2828428",
                LocalDate.of(1997, 8, 10));
        User user2 = new User("Aslonbek", "Hazratov", "AB1234567",
                LocalDate.of(1995, 11, 30));

        User user3 = new User("Doniyor", "Erkinov", "AC0428005",
                LocalDate.of(2002,11,5));

        Collections.addAll(userList, user1, user2, user3);

        SimCard simCard1 = new SimCard(ucell, special35, user1, "+998946311303", "7777", 50000);
        SimCard simCard2 = new SimCard(beeline, super10, user1, "+998917000007", "7777", 40000);
        SimCard simCard3 = new SimCard(uzMobile, simple10, user2, "+998997777777", "5555", 100_000);
        SimCard simCard4 = new SimCard(uzMobile, simple10, user3, "+9989954770415", "0404", 80_000);


        Collections.addAll(simCardList, simCard1, simCard2, simCard3, simCard4);
    }
}
