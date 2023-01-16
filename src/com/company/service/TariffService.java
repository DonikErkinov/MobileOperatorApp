package com.company.service;

import com.company.database.Database;
import com.company.model.MobileOperator;
import com.company.model.Tariff;

import java.util.ArrayList;
import java.util.List;

public class TariffService {
    public static String addTariff(MobileOperator operator, String name, double monthlyPayment,
                                   int freeMinutes, int freemegabytes, int freeSms, double priceMinWithOutLimit,
                                   double priceMbWithOutLimit, double priceSmsWithOutLimit) {
        for (Tariff tariff : Database.tariffList) {
            if (tariff.getMobileOperator().equals(operator) && tariff.getName().equals(name)) {
                return tariff.getMobileOperator().getName() + " has already tariff called " + tariff.getName();
            }
        }
        Tariff newTariff = new Tariff(name, operator, monthlyPayment,
                freeMinutes, freemegabytes, freeSms, priceMinWithOutLimit,
                priceMbWithOutLimit, priceSmsWithOutLimit);
        Database.tariffList.add(newTariff);
        return name + " registered";
    }

    public static boolean showTariffs() {
        if (Database.tariffList.isEmpty()) {
            System.out.println("There is no tariff!");
            return false;
        } else {
            for (Tariff tariff : Database.tariffList) {
                System.out.println(tariff);
            }
            return true;
        }
    }

    public static Tariff findTariffByName(String name, String mobileOperator) {
        for (Tariff tariff : Database.tariffList) {
            if (tariff.getName().equalsIgnoreCase(name) && tariff.getMobileOperator().getName().equalsIgnoreCase(mobileOperator))
                return tariff;
        }
        return null;
    }

    public static String updateTariff(Tariff tariffToChange, String newName,
                                      double newMonthlyPayment, int newFreeMinutes,
                                      int newFreeMegabytes, int newFreeSms,
                                      double newPriceMinWithoutLimit,
                                      double newPriceMbWithoutLimit,
                                      double newPriceSmsWithoutLimit) {
        if (newName.isEmpty())
            return "Name required";
        if (newFreeMinutes < 0)
            return "Invalid minute input";
        if (newFreeMegabytes < 0)
            return "Invalid megabyte input";
        if (newFreeSms < 0)
            return "Invalid sms input";

        tariffToChange.setName(newName);
        tariffToChange.setMonthPayment(newMonthlyPayment);
        tariffToChange.setMinute(newFreeMinutes);
        tariffToChange.setMegabyte(newFreeMegabytes);
        tariffToChange.setSms(newFreeSms);
        tariffToChange.setPriceMinWithOutLimit(newPriceMinWithoutLimit);
        tariffToChange.setPriceMbWithOutLimit(newPriceMbWithoutLimit);
        tariffToChange.setPriceSmsWithOutLimit(newPriceSmsWithoutLimit);

        return tariffToChange.getName() + " updated!";
    }

    public static boolean showActiveTariffs() {
        if (Database.tariffList.isEmpty())
            return false;
        for (Tariff tariff : Database.tariffList) {
            if(!tariff.isDeleted())
                System.out.println(tariff);
        }
        return true;
    }

    public static boolean showActiveTariffs(MobileOperator operator) {
        if (Database.tariffList.isEmpty()){
            System.out.println("No tariffs");
            return false;
        }
        for (Tariff tariff : Database.tariffList) {
            if(tariff.getMobileOperator().equals(operator) && !tariff.isDeleted())
                System.out.println(tariff);
        }
        return true;
    }

    public static List<Tariff> getAllTariffListByMobileOperator(MobileOperator mobileOperator) {
        List<Tariff> tariffList = new ArrayList<>();

        for (Tariff tariff : Database.tariffList) {
            if(tariff.getMobileOperator().equals(mobileOperator)){
                tariffList.add(tariff);
            }
        }
        return tariffList;
    }

    public static List<Tariff> getActiveTariffListByMobileOperator(MobileOperator mobileOperator) {
        List<Tariff> tariffList = new ArrayList<>();

        for (Tariff tariff : Database.tariffList) {
            if(tariff.getMobileOperator().equals(mobileOperator) && !tariff.isDeleted()){
                tariffList.add(tariff);
            }
        }
        return tariffList;
    }
}
