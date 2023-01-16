package com.company.controller;

import com.company.Application;
import com.company.database.Database;
import com.company.enums.SimCardStatus;
import com.company.model.MobileOperator;
import com.company.model.SimCard;
import com.company.model.Tariff;
import com.company.service.MobileOperatorService;
import com.company.service.SimCardService;
import com.company.service.TariffService;
import com.company.service.UserService;
import com.company.util.ScannerUtil;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class SimCardController {
    public static void giveSimCard() {
        boolean hasMO = MobileOperatorService.showActiveMobileOperators();
        if (!hasMO) return;

        System.out.print("Enter mobile operator name: ");
        String operatorName = ScannerUtil.SCANNER_STR.nextLine().trim();

        MobileOperator mobileOperator = MobileOperatorService.getMobileOperatorByName(operatorName);
        if (mobileOperator == null) {
            System.out.println("There is no such kind of operator!");
            return;
        }
        if (mobileOperator.isDeleted()) {
            System.out.println(operatorName + " has already been deleted");
            return;
        }

        List<Tariff> tariffList = TariffService.getActiveTariffListByMobileOperator(mobileOperator);

        if (tariffList.isEmpty()) {
            System.out.println("Currently, there is no any active tariffs in " + operatorName);
            return;
        }

        for (Tariff tariff : tariffList) {
            System.out.println(tariff);
        }

        System.out.print("Enter tariff name: ");
        String tariffName = ScannerUtil.SCANNER_STR.nextLine().trim();

        Tariff tariff = null;
        for (Tariff tariff1 : tariffList) {
            if (tariffName.equalsIgnoreCase(tariff1.getName())) {
                tariff = tariff1;
                break;
            }
        }

        if (tariff == null) {
            System.out.println("Currently, there is no any active tariffs in " + operatorName);
            return;
        }

        System.out.println();
        System.out.print("Enter first name:");
        String firstName = ScannerUtil.SCANNER_STR.nextLine().trim();
        System.out.print("Enter last name:");
        String lastName = ScannerUtil.SCANNER_STR.nextLine().trim();
        System.out.print("Enter passport:");
        String passport = ScannerUtil.SCANNER_STR.nextLine().trim();
        System.out.print("Enter birth year:");
        int birthYear = ScannerUtil.SCANNER_NUM.nextInt();
        System.out.print("Enter birth month:");
        int birthMonth = ScannerUtil.SCANNER_NUM.nextInt();
        System.out.print("Enter birth day:");
        int birthDay = ScannerUtil.SCANNER_NUM.nextInt();

        System.out.printf("Enter code (%s):", mobileOperator.getCodes());
        int code = ScannerUtil.SCANNER_NUM.nextInt();

        System.out.print("Enter phone number (7 digits): ");
        String phoneNumber = ScannerUtil.SCANNER_STR.nextLine().trim();

        System.out.print("Enter pin code (4 digits): ");
        String pinCode = ScannerUtil.SCANNER_STR.nextLine().trim();

        System.out.printf("Give money for paynet (min %.2f): ", tariff.getMonthPayment());
        int money = ScannerUtil.SCANNER_NUM.nextInt();
        if (money < tariff.getMonthPayment()) {
            System.out.println("You must pay more than monthly payment of current tariff");
            return;
        }

        String checkRegister = UserService.checkRegister(firstName, lastName, passport,
                birthYear, birthMonth, birthDay, code, phoneNumber, pinCode, mobileOperator.getCodes());
        if (checkRegister == null) {
            String result = SimCardService.giveSimCard(mobileOperator, tariff, firstName, lastName, passport,
                    birthYear, birthMonth, birthDay, code, phoneNumber, pinCode, money);
            System.out.println(result);
        } else {
            System.out.println(checkRegister);
        }
    }

    public static void activateSimCard() {
        System.out.print("Enter phone number (+998901234567): ");
        String phoneNumber = ScannerUtil.SCANNER_STR.nextLine().trim();

        String result = SimCardService.activateSimCard(phoneNumber);
        System.out.println(result);

    }

    public static void showAllCallHistory() {
        SimCardService.showAllCallHistory();
    }

    public static void showAllSMSHistory() {
        SimCardService.showAllSmsHistory();
    }

    public static void call() {
        System.out.print("Enter calling number (+998901234567): ");
        String phoneNumber = ScannerUtil.SCANNER_STR.nextLine().trim();

        String result = SimCardService.call(phoneNumber);
        System.out.println(result);

    }

    public static void sendSms() {
        boolean isHas = SimCardService.showOwnSmsHistory1();

        System.out.print("Enter number to send sms: ");
        String number = ScannerUtil.SCANNER_STR.nextLine().trim();

        SimCardService.getSmsInThisNumbers(number);

        System.out.print("Write your sms: ");
        String sms = ScannerUtil.SCANNER_STR.nextLine().trim();

        String result = SimCardService.sendSms(number, sms);
        System.out.println(result);

    }

    public static void useInternet() {
        System.out.print("Enter the number of megabytes you want to use ");
        int megaByte = ScannerUtil.SCANNER_NUM.nextInt();

        String internetUse = SimCardService.useInternet(megaByte);
        System.out.println(internetUse);

    }

    public static void showInfo() {
        System.out.println();
        System.out.println("*100# - show balance");
        System.out.println("*102# - show minute and sms by limit");
        System.out.println("*105# - show mb by limit");
        System.out.println("*450# - who i am?");

        System.out.print("Enter USSD: ");
        String ussd = ScannerUtil.SCANNER_STR.nextLine().trim();

        if (ussd.isEmpty()) {
            System.out.println("USSD is empty!");
            return;
        }

        switch (ussd) {
            case "*100#": {
                System.out.printf("balance: %s, tariff: %s \n",
                        Application.currentSimCard.getBalance(),
                        Application.currentSimCard.getTariff().getName());
            }
            break;

            case "*102#": {
                System.out.printf("minute: %s, sms: %s \n",
                        Application.currentSimCard.getMinute(),
                        Application.currentSimCard.getSms());
            }
            break;
            case "*105#": {
                System.out.printf("mb: %s \n",
                        Application.currentSimCard.getMegabyte());
            }
            break;
            case "*450#": {
                System.out.printf("number: %s, tariff: %s, owner: %s, %s, passport: %s \n",
                        Application.currentSimCard.getMegabyte(),
                        Application.currentSimCard.getTariff().getName(),
                        Application.currentSimCard.getUser().getFirstName(),
                        Application.currentSimCard.getUser().getLastName(),
                        Application.currentSimCard.getUser().getPassport());
            }
            break;
            default:
                System.out.println("wrong USSD!");
        }
    }

    public static void showOwnSmsHistory() {
        boolean isHas = SimCardService.showOwnSmsHistory1();
        if (!isHas) return;

        System.out.print("Enter number to send sms: ");
        String number = ScannerUtil.SCANNER_STR.nextLine().trim();

        isHas = SimCardService.getSmsInThisNumbers(number);
        if (!isHas) return;
    }

    public static void changePinCode() {

        System.out.print("Enter current pin code: ");
        String currentPass = ScannerUtil.SCANNER_STR.nextLine();

        if (!currentPass.equals(Application.currentSimCard.getPinCode())) {
            System.out.println("wrong current pin code!");
            return;
        }

        System.out.print("Enter new pin code: ");
        String newPass = ScannerUtil.SCANNER_STR.nextLine();

        if (!newPass.matches("\\d{4}")) {
            System.out.println("Pin code must be 4 digits");
            return;
        }

        System.out.print("Repeat new pin code: ");
        String repeatNewPass = ScannerUtil.SCANNER_STR.nextLine();

        if (!newPass.equals(repeatNewPass)) {
            System.out.println("Don't match new pin codes");
            return;
        }

        Application.currentSimCard.setPinCode(newPass);
        System.out.printf("Successfully changed pin code from %s -> %s", currentPass, newPass);
        System.out.println();
    }
}
