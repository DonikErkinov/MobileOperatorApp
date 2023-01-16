package com.company.model;

import com.company.enums.SimCardStatus;
import com.company.model.base.BaseModel;

public class SimCard extends BaseModel {
    private MobileOperator mobileOperator;
    private Tariff tariff;
    private User user;
    private String number; //901234567
    private String pinCode; // "1234"
    private double balance;
    private int minute;
    private int megabyte;
    private int sms;
    private SimCardStatus status;

    public SimCard(MobileOperator mobileOperator, Tariff tariff, User user, String number,
                   String pinCode, double balance) {
        this.mobileOperator = mobileOperator;
        this.tariff = tariff;
        this.user = user;
        this.number = number;
        this.pinCode = pinCode;
        this.balance = balance - tariff.getMonthPayment();
        mobileOperator.setBalance(mobileOperator.getBalance() + balance);
        this.minute = tariff.getMinute();
        this.megabyte = tariff.getMegabyte();
        this.sms = tariff.getSms();
        this.status = SimCardStatus.ACTIVE;
    }

    public MobileOperator getMobileOperator() {
        return mobileOperator;
    }

    public void setMobileOperator(MobileOperator mobileOperator) {
        this.mobileOperator = mobileOperator;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getMegabyte() {
        return megabyte;
    }

    public void setMegabyte(int megabyte) {
        this.megabyte = megabyte;
    }

    public int getSms() {
        return sms;
    }

    public void setSms(int sms) {
        this.sms = sms;
    }

    public SimCardStatus getStatus() {
        return status;
    }

    public void setStatus(SimCardStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SimCard{" +
                "id=" + id +
                ", mobileOperator=" + mobileOperator.getName() +
                ", tariff=" + tariff.getName() +
                ", user='" + user.getFirstName()+" "+user.getLastName() +'\'' +
                ", number='" + number + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", balance=" + balance +
                ", minute=" + minute +
                ", megabyte=" + megabyte +
                ", sms=" + sms +
                ", status=" + status +
                ", deleted=" + deleted +
                '}';
    }
}
