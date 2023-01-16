package com.company.controller;

import com.company.database.Database;
import com.company.model.MobileOperator;
import com.company.service.MobileOperatorService;
import com.company.util.ScannerUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MobileOperatorController {

    public static void crud() {
        System.out.println();
        System.out.println("1. Create mobile operator");
        System.out.println("2. Show mobile operators");
        System.out.println("3. Update mobile operator");
        System.out.println("4. Delete mobile operator");

        System.out.print("Select operation number: ");
        int operation= ScannerUtil.SCANNER_NUM.nextInt();

        switch (operation){
            case 1:{
                addMobileOperator();
            }break;
            case 2:{
                MobileOperatorService.showMobileOperators();
            }break;
            case 3:{
                updateMobileOperator();
            }break;
            case 4:{
                deleteMobileOperator();
            }break;

        }


    }

    private static void addMobileOperator() {
        System.out.println();
        System.out.print("Mobile name: ");
        String name = ScannerUtil.SCANNER_STR.nextLine().trim();
        System.out.print("Max call duration: ");
        int maxCallDuration = ScannerUtil.SCANNER_NUM.nextInt();

        List<Integer> codes=new LinkedList<>();

        System.out.print("How many code types: ");
        int codeSize = ScannerUtil.SCANNER_NUM.nextInt();
        for (int i = 0; i <codeSize ; i++) {
            System.out.print((i+1)+"-code: ");
            int code = ScannerUtil.SCANNER_NUM.nextInt();
            codes.add(code);
        }

        String result = MobileOperatorService.addMobileOperator(name, codes, codeSize, maxCallDuration);
        System.out.println(result);

    }

    private static void updateMobileOperator() {
        boolean hasMO = MobileOperatorService.showMobileOperators();

        if(!hasMO) return;

        System.out.println("Enter mobile operator name for updating: ");
        String oldName = ScannerUtil.SCANNER_STR.nextLine().trim();

        MobileOperator mobileOperator = MobileOperatorService.getMobileOperatorByName(oldName);
        if(mobileOperator==null){
            System.out.println("Such mobile operator does not exist");
            return;
        }

        System.out.printf("Mobile name (%s): ", mobileOperator.getName());
        String name = ScannerUtil.SCANNER_STR.nextLine().trim();

        List<Integer> codes=new LinkedList<>();

        System.out.println("old codes: "+mobileOperator.getCodes());
        System.out.print("How many code types: ");
        int codeSize = ScannerUtil.SCANNER_NUM.nextInt();
        for (int i = 0; i <codeSize ; i++) {
            System.out.print((i+1)+"-code: ");
            int code = ScannerUtil.SCANNER_NUM.nextInt();
            codes.add(code);
        }

        String result = MobileOperatorService.updateMobileOperator(mobileOperator, name, codes, codeSize);
        System.out.println(result);

    }

    private static void deleteMobileOperator() {
        boolean hasMO = MobileOperatorService.showActiveMobileOperators();

        if(!hasMO) {
            System.out.println("No mobile operators to delete");
            return;
        }

        System.out.println("Enter mobile operator name to delete: ");
        String name = ScannerUtil.SCANNER_STR.nextLine().trim();

        MobileOperator mobileOperator = MobileOperatorService.getMobileOperatorByName(name);
        if(mobileOperator == null){
            System.out.println("Such mobile operator does not exist");
            return;
        }

        if(mobileOperator.isDeleted()){
            System.out.println(name+" already deleted");
            return;
        }

        mobileOperator.setDeleted(true);
        System.out.println(name + " was deleted");
    }








}
