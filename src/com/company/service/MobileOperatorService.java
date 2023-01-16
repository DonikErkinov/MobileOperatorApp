package com.company.service;

import com.company.database.Database;
import com.company.model.MobileOperator;

import java.util.HashSet;
import java.util.List;

public class MobileOperatorService {

    public static MobileOperator getMobileOperatorByName(String name){
        for (MobileOperator mobileOperator : Database.mobileOperatorList) {
            if(mobileOperator.getName().equalsIgnoreCase(name)){
                return mobileOperator;
            }
        }
        return null;
    }

    public static void showAllBalances(){
        showMobileOperators();
    }

    public static boolean showMobileOperators() {
        if (Database.mobileOperatorList.isEmpty()){
            System.out.println("no mobile operators");
            return false;
        }
        for (MobileOperator mobileOperator : Database.mobileOperatorList) {
            System.out.println(mobileOperator);
        }
        return true;
    }

    public static boolean showActiveMobileOperators() {
        boolean hasMobileOperatorForDelete = false;

        for (MobileOperator mobileOperator : Database.mobileOperatorList) {
            if(!mobileOperator.isDeleted()) {
                System.out.println(mobileOperator);
                hasMobileOperatorForDelete = true;
            }
        }
        return hasMobileOperatorForDelete;
    }


    public static String  addMobileOperator(String name, List<Integer> codes, int codeSize, int maxCallDuration) {
        if (name==null || name.isEmpty()){
            return "name required ";
        }
        if (maxCallDuration <= 0) {
            return "maxCallDuration must be positive";
        }
        if (codes.isEmpty()) {
            return "codes required";
        }
        if (new HashSet<Integer>(codes).size() != codeSize){
            return "has repeat codes";
        }

        for (Integer code : codes) {
            if (code<10 || code>99){
                return "code must be between 10 and 99 ";
            }
        }

        for (Integer code : codes) {
            for (MobileOperator mobileOperator : Database.mobileOperatorList) {
                if (mobileOperator.getCodes().contains(code)){
                    return code+" has in "+mobileOperator.getName();
                }
            }
        }

        for (MobileOperator mobileOperator : Database.mobileOperatorList) {
            if(mobileOperator.getName().equalsIgnoreCase(name)){
                return name + " has already been registered";
            }
        }


        MobileOperator mobileOperator=new MobileOperator(name,codes, maxCallDuration);
        Database.mobileOperatorList.add(mobileOperator);

        return name+" registered ";
    }


    public static String updateMobileOperator(MobileOperator currentMobileOperator, String name, List<Integer> codes, int codeSize) {
        if (name==null || name.isEmpty()){
            return "name required ";
        }
        if (codes.isEmpty()) {
            return "codes required";
        }
        if (new HashSet<Integer>(codes).size() != codeSize){
            return "has repeat codes";
        }
        for (Integer code : codes) {
            if (code<10 || code>99){
                return "code must be between 10 and 99 ";
            }
        }

        for (Integer code : codes) {
            for (MobileOperator mobileOperator : Database.mobileOperatorList) {
                if (!currentMobileOperator.getId().equals(mobileOperator.getId()) && mobileOperator.getCodes().contains(code)){
                    return code+" has in "+mobileOperator.getName();
                }
            }
        }
        for (MobileOperator mobileOperator : Database.mobileOperatorList) {
            if(!currentMobileOperator.getId().equals(mobileOperator.getId()) && mobileOperator.getName().equalsIgnoreCase(name)){
                return name + " has already been registered";
            }
        }

        currentMobileOperator.setName(name);
        currentMobileOperator.setCodes(codes);
        return name+" is updated";
    }

}
