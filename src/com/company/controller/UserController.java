package com.company.controller;

import com.company.model.MobileOperator;
import com.company.model.SimCard;
import com.company.service.MobileOperatorService;
import com.company.service.SimCardService;
import com.company.service.UserService;
import com.company.util.ScannerUtil;

import java.util.Scanner;

public class UserController {
    public static int baseMenu() {
        System.out.println();
        System.out.println("1. Login ");
        System.out.println("0. Exit ");

        System.out.print("Select operation : ");
        int operation = ScannerUtil.SCANNER_NUM.nextInt();

        if (operation >= 0 && operation <= 1) {
            return operation;
        }
        return -1;
    }

    public static void register() {
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
        System.out.print("Enter login:");
        String login = ScannerUtil.SCANNER_STR.nextLine().trim();
        System.out.print("Enter password:");
        String password = ScannerUtil.SCANNER_STR.nextLine().trim();
        String checkRegister = UserService.checkRegister(firstName, lastName, passport, birthYear, birthMonth, birthDay, login,
                password);
        if (checkRegister == null) {
            String result = UserService.register(firstName, lastName, passport, birthYear, birthMonth, birthDay, login,
                    password);
            System.out.println(result);
        } else {
            System.out.println(checkRegister);
        }

    }


    public static void login() {
        System.out.println();
        System.out.print("Enter login:");
        String login = ScannerUtil.SCANNER_STR.nextLine().trim();
        System.out.print("Enter password:");
        String password = ScannerUtil.SCANNER_STR.nextLine().trim();

        String checkLogin = UserService.checkLogin(login, password);
        if (checkLogin == null) {
            UserService.login(login, password);
        } else {
            System.out.println(checkLogin);
        }
    }

    public static void adminPage() {
        System.out.println();
        System.out.println("1. Mobile operator CRUD ");
        System.out.println("2. Tariff CRUD ");
        System.out.println("3. Give sim card ");
        System.out.println("4. Activate blocked sim card ");
        System.out.println("5. Show history of calls ");
        System.out.println("6. Show history of sms ");
        System.out.println("7. Show company's balances");
        System.out.println("8. Show all sim cards");
        System.out.println("0. Log out");

        System.out.println("Select operation number: ");
        int operation = ScannerUtil.SCANNER_NUM.nextInt();

        switch (operation) {
            case 1: {
                MobileOperatorController.crud();
            }
            break;
            case 2: {
                TariffController.crud();
            }
            break;
            case 3: {
                SimCardController.giveSimCard();
            }
            break;
            case 4: {
                SimCardController.activateSimCard();
            }
            break;
            case 5: {
                SimCardController.showAllCallHistory();
            }
            break;
            case 6: {
                SimCardController.showAllSMSHistory();
            }
            break;
            case 7: {
                MobileOperatorService.showAllBalances();
            }
            break;
            case 8: {
                SimCardService.showAllSimCards();
            }
            break;
            case 0: {
                UserService.logOut();
            }
        }

    }

    public static void userPage() {
        System.out.println();
        System.out.println("1. Call to others ");
        System.out.println("2. Send sms to others ");
        System.out.println("3. Use internet ");
        System.out.println("4. Show info ");
        System.out.println("5. Show  own calls history   ");
        System.out.println("6. Show  own sms history  ");
        System.out.println("7. Change pin code");
        System.out.println("0. Log out");

        System.out.println("Select operation number: ");
        int operation = ScannerUtil.SCANNER_NUM.nextInt();
        switch (operation) {
            case 1: {
                SimCardController.call();
            }
            break;
            case 2: {
                SimCardController.sendSms();
            }
            break;
            case 3: {
                SimCardController.useInternet();
            }
            break;
            case 4: {
                SimCardController.showInfo();
            }
            break;
            case 5: {
                SimCardService.showOwnCallsHistory();
            }
            break;
            case 6: {
                SimCardController.showOwnSmsHistory();
            }
            break;
            case 7: {
                SimCardController.changePinCode();
            }
            break;
            case 0: {
                UserService.logOut();
            }
        }
    }
}






