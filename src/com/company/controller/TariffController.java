package com.company.controller;

import com.company.model.MobileOperator;
import com.company.model.Tariff;
import com.company.service.MobileOperatorService;
import com.company.service.TariffService;
import com.company.util.ScannerUtil;

public class TariffController {
    public static void crud() {
        System.out.println();
        System.out.println("1. Create tariff");
        System.out.println("2. Show tariffs");
        System.out.println("3. Update tariff");
        System.out.println("4. Delete tariff");

        System.out.print("Select operation number: ");
        int operation = ScannerUtil.SCANNER_NUM.nextInt();
        switch (operation) {
            case 1:
                TariffController.addTariff();
                break;
            case 2:
                TariffController.showTariffs();
                break;
            case 3:
                TariffController.updateTariff();
                break;
            case 4:
                TariffController.deleteTariff();
                break;

        }
    }

    private static void deleteTariff() {
        MobileOperatorService.showActiveMobileOperators();
        System.out.print("Enter name of mobile operator: ");
        String mobileOperator=ScannerUtil.SCANNER_STR.nextLine().trim();

        MobileOperator operatorByName = MobileOperatorService.getMobileOperatorByName(mobileOperator);
        if(operatorByName==null){
            System.out.println("No such mobile operator");
            return;
        }
        boolean hasNonDeletedTariff=TariffService.showActiveTariffs(operatorByName);
        if(!hasNonDeletedTariff){
            System.out.println("No tariff to delete!");
            return;
        }
        System.out.print("Enter name of tariff to delete: ");
        String name = ScannerUtil.SCANNER_STR.nextLine().trim();
        Tariff tariffByName = TariffService.findTariffByName(name, mobileOperator);
        if(tariffByName==null){
            System.out.println("Such tariff does not exit!");
            return;
        }

        if(tariffByName.isDeleted()){
            System.out.println(name+" already deleted");
            return;
        }

        tariffByName.setDeleted(true);
        System.out.println(tariffByName.getName()+" has been successfuly deleted ");
    }

    private static void updateTariff() {
        boolean hasTariff = TariffController.showTariffs();
        if (!hasTariff)
            return;

        System.out.print("Enter name of mobile operator: ");
        String mobileOperator=ScannerUtil.SCANNER_STR.nextLine().trim();

        System.out.print("Enter tariff name for updating: ");
        String name = ScannerUtil.SCANNER_STR.nextLine().trim();
        Tariff tariffToChange = TariffService.findTariffByName(name, mobileOperator);
        if (tariffToChange == null) {
            System.out.println("Such tariff does not exit");
            return;
        }

        System.out.printf("Tariff name (%s): " , tariffToChange.getName());
        String newName = ScannerUtil.SCANNER_STR.nextLine().trim();

        System.out.printf("Tariff monthly payment (%s): " , tariffToChange.getMonthPayment());
        double newMonthlyPayment = ScannerUtil.SCANNER_NUM.nextDouble();

        System.out.printf("Tariff free minutes (%s): ", tariffToChange.getMinute());
        int newFreeMinutes = ScannerUtil.SCANNER_NUM.nextInt();

        System.out.printf("Tariff free megabytes (%s): ", tariffToChange.getMegabyte());
        int newFreeMegabytes = ScannerUtil.SCANNER_NUM.nextInt();

        System.out.printf("Tariff free sms (%s): ", tariffToChange.getSms());
        int newFreeSms = ScannerUtil.SCANNER_NUM.nextInt();

        System.out.printf("Tariff price for minutes without limit (%s): ", tariffToChange.getPriceMinWithOutLimit());
        double newPriceMinWithoutLimit = ScannerUtil.SCANNER_NUM.nextDouble();

        System.out.printf("Tariff price for megabytes without limit (%s): ", tariffToChange.getPriceMbWithOutLimit());
        double newPriceMbWithoutLimit = ScannerUtil.SCANNER_NUM.nextDouble();

        System.out.printf("Tariff price for sms without limit (%s): ", tariffToChange.getPriceSmsWithOutLimit());
        double newPriceSmsWithoutLimit = ScannerUtil.SCANNER_NUM.nextDouble();

        String result = TariffService.updateTariff(tariffToChange, newName, newMonthlyPayment, newFreeMinutes,
                newFreeMegabytes, newFreeSms, newPriceMinWithoutLimit, newPriceMbWithoutLimit, newPriceSmsWithoutLimit);

        System.out.println(result);

    }


    private static boolean showTariffs() {
        return TariffService.showTariffs();
    }

    private static void addTariff() {
        boolean hasActiveMO = MobileOperatorService.showActiveMobileOperators();
        if (!hasActiveMO) {
            System.out.println("There is not any mobile operator to add tariff");
            return;
        }

        System.out.print("Enter name of mobile operator: ");
        String mobileOperator = ScannerUtil.SCANNER_STR.nextLine().trim();
        MobileOperator operator = MobileOperatorService.getMobileOperatorByName(mobileOperator);
        if (operator == null) {
            System.out.println("Invalid mobile operator");
            return;
        }
        System.out.print("Enter tariff name:");
        String name = ScannerUtil.SCANNER_STR.nextLine().trim();

        System.out.print("Enter monthly payment:");
        double monthlyPayment = ScannerUtil.SCANNER_NUM.nextDouble();

        System.out.print("Enter number of free minutes: ");
        int freeMinutes = ScannerUtil.SCANNER_NUM.nextInt();

        System.out.print("Enter number of free megabytes: ");
        int freemegabytes = ScannerUtil.SCANNER_NUM.nextInt();

        System.out.print("Enter number of free sms: ");
        int freeSms = ScannerUtil.SCANNER_NUM.nextInt();

        System.out.print("Enter price for Minutes without limit: ");
        double priceMinWithOutLimit = ScannerUtil.SCANNER_NUM.nextDouble();

        System.out.print("Enter price for megabytes without limit: ");
        double priceMbWithOutLimit = ScannerUtil.SCANNER_NUM.nextDouble();

        System.out.print("Enter price for sms without limit: ");
        double priceSmsWithOutLimit = ScannerUtil.SCANNER_NUM.nextDouble();

        String result = TariffService.addTariff(operator, name, monthlyPayment, freeMinutes, freemegabytes, freeSms, priceMinWithOutLimit, priceMbWithOutLimit, priceSmsWithOutLimit);
        System.out.println(result);

    }
}
