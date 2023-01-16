package com.company.service;

import com.company.Application;
import com.company.container.ComponentContainer;
import com.company.database.Database;
import com.company.model.SimCard;
import com.company.model.User;

import java.time.LocalDate;
import java.util.List;

public class UserService {

    public static String checkRegister(String firstName, String lastName, String passport,
                                       int birthYear, int birthMonth, int birthDay, String login, String password) {
        if (firstName == null || firstName.isBlank()) {
            return "firstName required";
        }
        if (lastName == null || lastName.isBlank()) {
            return "lastName required";
        }
        if (passport == null || passport.isBlank()) {
            return "passport required";
        }
        if(!passport.matches("[A-Z]{2}[0-9]{7}")){
          return "passport must match of pattern: AA1234567";
        }
        if (birthYear <= 1900) {
            return "birthYear must be great then 1900 ";
        }
        if (birthMonth <= 0 || birthMonth > 12) {
            return "birthMonth must be between 1 and 12";
        }
        LocalDate birthDate = LocalDate.of(birthYear, birthMonth, 1);
        int maxDay = birthDate.getMonth().length(birthYear % 400 == 0 || birthYear % 4 == 0 && birthYear % 100 != 0);
        if (birthDay <= 0 || birthDay>maxDay) {
            return "birthDay must be between 1 and "+maxDay;
        }

        if (login == null || login.isBlank()) {
            return "login required";
        }
        if (password == null || password.isBlank()) {
            return "password required";
        }

        return null; // all correct
    }

    public static String register(String firstName, String lastName, String passport,
                                  int birthYear, int birthMonth, int birthDay, String login, String password) {
        return null;
    }

    public static String checkLogin(String login, String password) {
        if (login == null || login.isBlank()) {
            return "login required";
        }
        if (password == null || password.isBlank()) {
            return "password required";
        }
        return null;
    }

    public static String checkLogin2(String phone, String pinCode) {
        if (!phone.matches("[+]998\\d{9}")) {
            return "phone must be of pattern(X, Y are digits): +998XXYYYYYYY";
        }
        if (!pinCode.matches("\\d{4}")) {
            return "pinCode must be 4 digits";
        }

        return null;
    }

    public static void login(String login, String password) {
        if(login.equals(ComponentContainer.ADMIN_LOGIN) && password.equals(ComponentContainer.ADMIN_PASSWORD)){
            ComponentContainer.signInAdmin = true;
        }else{
            // check has phone and pin code
            String checkLogin2 = checkLogin2(login, password);
            if(checkLogin2 != null){
                System.out.println(checkLogin2);
                return;
            }

            for (SimCard simCard : Database.simCardList) {
                if(simCard.getNumber().equals(login) && simCard.getPinCode().equals(password)){
                    Application.currentSimCard = simCard;
                    break;
                }
            }

            if(Application.currentSimCard == null){
                System.out.println("Phone number or pin code incorrect.");
            }
        }
    }

    public static void logOut() {
        if(ComponentContainer.signInAdmin){
            ComponentContainer.signInAdmin = false;
        }else{
            Application.currentSimCard = null;
        }
    }

    public static String checkRegister(String firstName, String lastName, String passport,
                                       int birthYear, int birthMonth, int birthDay, int code,
                                       String phoneNumber, String pinCode, List<Integer> codes) {
        if (firstName == null || firstName.isBlank()) {
            return "firstName required";
        }
        if (lastName == null || lastName.isBlank()) {
            return "lastName required";
        }
        if (passport == null || passport.isBlank()) {
            return "passport required";
        }
        if(!passport.matches("[A-Z]{2}[0-9]{7}")){
            return "passport must match of pattern: AA1234567";
        }
        if (birthYear <= 1900) {
            return "birthYear must be great then 1900 ";
        }
        if (birthMonth <= 0 || birthMonth > 12) {
            return "birthMonth must be between 1 and 12";
        }
        LocalDate birthDate = LocalDate.of(birthYear, birthMonth, 1);
        int maxDay = birthDate.getMonth().length(birthYear % 400 == 0 || birthYear % 4 == 0 && birthYear % 100 != 0);
        if (birthDay <= 0 || birthDay>maxDay) {
            return "birthDay must be between 1 and "+maxDay;
        }

        if (phoneNumber == null || phoneNumber.isBlank()) {
            return "phone number required";
        }
        if (pinCode == null || pinCode.isBlank()) {
            return "Pin code required";
        }
        if(!codes.contains(code)){
            return "Code must be these shown " + codes;
        }
        if(!phoneNumber.matches("\\d{7}")) {
            return "Phone number 7 digits";
        }
        if(!pinCode.matches("\\d{4}")) {
            return "Pin code 4 digits";
        }


        return null; // all correct
    }

    public static User getUserByPassport(String passport) {
        for (User user : Database.userList) {
            if(user.getPassport().equalsIgnoreCase(passport)){
                return user;
            }
        }
        return null;
    }





}
