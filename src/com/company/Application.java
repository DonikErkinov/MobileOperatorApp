package com.company;

import com.company.container.ComponentContainer;
import com.company.controller.UserController;
import com.company.database.Database;
import com.company.enums.UserRole;
import com.company.model.SimCard;
import com.company.model.User;
import com.company.util.ScannerUtil;

public class Application {

    public static SimCard currentSimCard = null;

    public static void main(String[] args) {

        Database.loadData();

        while (true) {

            try {

                if (currentSimCard == null) {

                    if(ComponentContainer.signInAdmin){
                        UserController.adminPage();
                    }else{
                        //current must be login;
                        int operation = UserController.baseMenu();
                        switch (operation) {
                            case 1: {
                                UserController.login();
                            }
                            break;
                            case 0:
                                return;
                        }
                    }

                } else {
                    //has currentUser is simple user;
                    UserController.userPage();
                }

            }catch (Exception e){
                System.out.println("e = " + e);
                ScannerUtil.SCANNER_NUM.nextLine();
            }

        }


    }
}
