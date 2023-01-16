package com.company.service;

import com.company.Application;
import com.company.database.Database;
import com.company.enums.CallStatus;
import com.company.enums.SimCardStatus;
import com.company.enums.SmsStatus;
import com.company.model.*;
import com.company.util.ScannerUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class SimCardService {
    public static boolean showOwnCallsHistory() {
        if (Database.callHistoryList.isEmpty()) {
            System.out.println("No calls");
            return false;
        }

        boolean hasMyCall = false;
        for (CallHistory callHistory : Database.callHistoryList) {
            if (callHistory.getFrom().equals(Application.currentSimCard) ||
                    callHistory.getTo().equals(Application.currentSimCard)) {
                System.out.println(callHistory);
                hasMyCall = true;
            }
        }
        return hasMyCall;
    }

    public static SimCard getSimCardByNumber(String number) {
        for (SimCard simCard : Database.simCardList) {
            if (simCard.getNumber().equals(number)) {
                return simCard;
            }
        }
        return null;
    }

    public static String giveSimCard(MobileOperator mobileOperator, Tariff tariff,
                                     String firstName, String lastName, String passport,
                                     int birthYear, int birthMonth, int birthDay, int code,
                                     String phoneNumber, String pinCode, Integer money) {

        String number = "+998" + code + phoneNumber;
        SimCard simCard = getSimCardByNumber(number);

        if (simCard != null) {
            return "This number has already taken";
        }

        User user = UserService.getUserByPassport(passport);
        if (user == null) {
            user = new User(firstName, lastName, passport, LocalDate.of(birthYear, birthMonth, birthDay));
            Database.userList.add(user);
        }

        SimCard simCard1 = new SimCard(mobileOperator, tariff, user, number, pinCode, money);
        Database.simCardList.add(simCard1);
        return String.format("%s successfully given to %s %s", number, user.getFirstName(), user.getLastName());
    }

    public static void showAllSimCards() {
        if (Database.simCardList.isEmpty()) {
            System.out.println("No sim cards");
            return;
        }
        for (SimCard simCard : Database.simCardList) {
            System.out.println(simCard);
        }
    }

    public static String activateSimCard(String phoneNumber) {
        SimCard simCard = SimCardService.getSimCardByNumber(phoneNumber);
        if (simCard == null) {
            return (phoneNumber + " doesn't exist. Try again");
        }
        if (simCard.getStatus().equals(SimCardStatus.ACTIVE)) {
            return (simCard + " this number is already active");
        }
        simCard.setStatus(SimCardStatus.ACTIVE);
        return (phoneNumber + " is successfully activated");
    }

    public static String call(String phoneNumber) {
        if (Application.currentSimCard.getNumber().equals(phoneNumber)) {
            return "You are can't call your self";
        }
        if (!Application.currentSimCard.getStatus().equals(SimCardStatus.ACTIVE)) {
            return "Your number is not active";
        }
        if (Application.currentSimCard.getMinute() <= 0 && Application.currentSimCard.getBalance() <= 0) {
            return "Your minute and balance limit is over ";
        }
        SimCard simCard = getSimCardByNumber(phoneNumber);
        if (simCard == null || simCard.isDeleted()) {
            return "There is not number on network";
        }

        System.out.println("calling...");

        System.out.println(Arrays.toString(CallStatus.values()));

        System.out.print("Enter CallStatus: ");
        String callStatusStr = ScannerUtil.SCANNER_STR.nextLine().trim().toUpperCase();

        CallStatus callStatus = null;
        for (CallStatus value : CallStatus.values()) {
            if (value.name().equals(callStatusStr)) {
                callStatus = value;
                break;
            }
        }
        if (callStatus == null) {
            return "Wrong call status";
        }

        int duration = 0, originalDuration = 0;

        if (callStatus.equals(CallStatus.ACTIVE)) {

            int maxCallDuration = Application.currentSimCard.getMobileOperator().getMaxCallDuration();

            System.out.printf("Enter calling duration(max %s): ", maxCallDuration);
            duration = ScannerUtil.SCANNER_NUM.nextInt();

            if (duration > maxCallDuration) {
                duration = maxCallDuration;
            }

            originalDuration = duration;

            if (duration > Application.currentSimCard.getMinute()) {
                duration -= Application.currentSimCard.getMinute();
                Application.currentSimCard.setMinute(0);

                double priceMinWithOutLimit = Application.currentSimCard.getTariff().getPriceMinWithOutLimit();

                if (Application.currentSimCard.getBalance() >= duration * priceMinWithOutLimit) {
                    Application.currentSimCard.setBalance(
                            Application.currentSimCard.getBalance() - duration * priceMinWithOutLimit
                    );
                    duration = 0;
                } else {
                    duration -= (int) (Application.currentSimCard.getBalance() / priceMinWithOutLimit) + 1;
                    Application.currentSimCard.setBalance(0);
                }


            } else {
                Application.currentSimCard.setMinute(Application.currentSimCard.getMinute() - duration);
                duration = 0;
            }
        }

        CallHistory callHistory = new CallHistory(Application.currentSimCard, simCard, LocalDateTime.now(), callStatus,
                originalDuration - duration);

        Database.callHistoryList.add(0, callHistory);
        return callStatus.name();

    }

    public static void showAllCallHistory() {
        if (Database.callHistoryList.isEmpty()) {
            System.out.println("There is no calls yet");
            return;
        }

        for (CallHistory callHistory : Database.callHistoryList) {
            System.out.println(callHistory);
        }
    }

    public static void showAllSmsHistory() {
        if (Database.smsHistoryList.isEmpty()) {
            System.out.println("There is no sms yet");
            return;
        }

        for (SmsHistory smsHistory : Database.smsHistoryList) {
            System.out.println(smsHistory);
        }
    }

    public static String sendSms(String phoneNumber, String sms) {
        if (Application.currentSimCard.getNumber().equals(phoneNumber)) {
            return "You are can't send message your self";
        }
        if (!Application.currentSimCard.getStatus().equals(SimCardStatus.ACTIVE)) {
            return "Your phone is not ACTIVE";
        }

        if (Application.currentSimCard.getSms() <= 0 && Application.currentSimCard.getBalance() <= 0) {
            return "Your sms and balance limit is over ";
        }

        double priceSmsWithOutLimit = Application.currentSimCard.getTariff().getPriceSmsWithOutLimit();

        if (Application.currentSimCard.getSms() <= 0 &&
                Application.currentSimCard.getBalance() < priceSmsWithOutLimit) {
            return "Balance not enough";
        }

        SimCard simCard = getSimCardByNumber(phoneNumber);
        if (simCard == null || simCard.isDeleted()) {
            return "There is not number on network";
        }

        System.out.println("Sending .... ");

        System.out.println(Arrays.toString(SmsStatus.values()));

        System.out.print("Enter SmsStatus: ");
        String SmsStatusStr = ScannerUtil.SCANNER_STR.nextLine().trim().toUpperCase();

        SmsStatus smsStatus = null;
        for (SmsStatus status : SmsStatus.values()) {
            if (status.name().equals(SmsStatusStr)) {
                smsStatus = status;
            }
        }

        if (smsStatus == null) {
            return "Wrong sms status";
        }

        if (smsStatus.name().equals(SmsStatus.SEND.name())) {
            if (Application.currentSimCard.getSms() <= 0 &&
                    Application.currentSimCard.getBalance() >= Application.currentSimCard.getTariff().getPriceSmsWithOutLimit()) {
                Application.currentSimCard.setBalance(Application.currentSimCard.getBalance() - Application.currentSimCard.getTariff().getPriceSmsWithOutLimit());
            } else {
                Application.currentSimCard.setSms(Application.currentSimCard.getSms() - 1);
            }
            SmsHistory smsHistory = new SmsHistory(Application.currentSimCard, simCard, LocalDateTime.now(), sms, smsStatus);
            Database.smsHistoryList.add(0, smsHistory);
        }

        return smsStatus.name();
    }

    public static boolean showOwnSmsHistory1() {
        if (Database.smsHistoryList.isEmpty()) {
            System.out.println("You don`t have any sms yet");
            return false;
        }

        Database.phoneNumbers.clear();
        for (SmsHistory sms : Database.smsHistoryList) {
            String phoneNumber = null;
            if (sms.getFrom().getNumber().equals(Application.currentSimCard.getNumber()) ||
                    sms.getTo().getNumber().equals(Application.currentSimCard.getNumber()) && sms.getStatus().equals(SmsStatus.SEND)) {
                phoneNumber = sms.getTo().getNumber();
            }

            if (phoneNumber != null && !Database.phoneNumbers.contains(phoneNumber)) {
                Database.phoneNumbers.add(phoneNumber);
            }
        }

        for (String phoneNumber : Database.phoneNumbers) {
            System.out.println(phoneNumber);
        }
        Database.phoneNumbers.clear();
        return true;
    }

    public static boolean getSmsInThisNumbers(String number) {
        boolean isHasSms = false;

        for (SmsHistory smsHistory : Database.smsHistoryList) {
            if(smsHistory.getFrom().getNumber().equals(Application.currentSimCard.getNumber()) &&
            smsHistory.getTo().getNumber().equals(number)){
                System.out.printf("message: %s, to: %s, time: %s\n", smsHistory.getMessage(), number, smsHistory.getTimestamp());
                isHasSms = true;
            }
            else if(smsHistory.getFrom().getNumber().equals(number) &&
                    smsHistory.getTo().getNumber().equals(Application.currentSimCard.getNumber())){
                System.out.printf("message: %s, from: %s, time: %s\n", smsHistory.getMessage(), number, smsHistory.getTimestamp());
                isHasSms = true;
            }
        }

        if (!isHasSms) {
            System.out.println("You don`t have any sms with this number yet");
        }
        return isHasSms;
    }

    public static String useInternet(int megaByte) {

        if(!Application.currentSimCard.getStatus().equals(SimCardStatus.ACTIVE)){
            return "your mobile phone is not active";
        }

        if(Application.currentSimCard.getMegabyte()>=megaByte){
            Application.currentSimCard.setMegabyte(Application.currentSimCard.getMegabyte()-megaByte);
            return "You successfully used "+megaByte+" megabytes";
        }

        if(Application.currentSimCard.getMegabyte()<megaByte && Application.currentSimCard.getBalance()>0){
            double otherMegabyte = megaByte-Application.currentSimCard.getMegabyte();

            if(Application.currentSimCard.getBalance()>=Application.currentSimCard.getTariff().getPriceMbWithOutLimit()*otherMegabyte){
                return "You have successfully used "+megaByte+" megabytes";
            }

            if(Application.currentSimCard.getBalance()<Application.currentSimCard.getTariff().getPriceMbWithOutLimit()*otherMegabyte){
                double remainderMegabyte = Application.currentSimCard.getBalance()/Application.currentSimCard.getTariff().getPriceMbWithOutLimit();
                Application.currentSimCard.setBalance(0);
                return "You have successfully spent only "+ remainderMegabyte+ " and now your balance is 0";
            }
        }

        if (Application.currentSimCard.getMegabyte() <= 0 && Application.currentSimCard.getBalance() <= 0) {
            return "You have no enough megabytes or balance to use internet ";
        }



        if(Application.currentSimCard.getMegabyte()==0 && Application.currentSimCard.getBalance()>=Application.currentSimCard.getTariff().getPriceMbWithOutLimit()*megaByte){
            Application.currentSimCard.setBalance(Application.currentSimCard.getBalance()-Application.currentSimCard.getTariff().getPriceMbWithOutLimit()*megaByte);
            return "You successfully spend your "+megaByte+" out of tariff";
        }
        if(Application.currentSimCard.getMegabyte()==0 && Application.currentSimCard.getBalance()<Application.currentSimCard.getTariff().getPriceMbWithOutLimit()*megaByte){
            double remainderMegabyte = Application.currentSimCard.getBalance()/Application.currentSimCard.getTariff().getPriceMbWithOutLimit();
            Application.currentSimCard.setBalance(0);
            return "You have successfully spent only "+ remainderMegabyte+ " and now your balance is 0";
        }


        return null;
    }
}
