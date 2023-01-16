package com.company.model;

import com.company.model.base.BaseModel;

import java.util.UUID;

public class Tariff extends BaseModel {
    private String name;
    private MobileOperator mobileOperator;
    private double monthPayment;
    private int minute;
    private int megabyte;
    private int sms;

    private double priceMinWithOutLimit;
    private double priceMbWithOutLimit;
    private double priceSmsWithOutLimit;

    public Tariff(String name, MobileOperator mobileOperator, double monthPayment, int minute, int megabyte, int sms, double priceMinWithOutLimit, double priceMbWithOutLimit, double priceSmsWithOutLimit) {
        this.name = name;
        this.mobileOperator = mobileOperator;
        this.monthPayment = monthPayment;
        this.minute = minute;
        this.megabyte = megabyte;
        this.sms = sms;
        this.priceMinWithOutLimit = priceMinWithOutLimit;
        this.priceMbWithOutLimit = priceMbWithOutLimit;
        this.priceSmsWithOutLimit = priceSmsWithOutLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MobileOperator getMobileOperator() {
        return mobileOperator;
    }

    public void setMobileOperator(MobileOperator mobileOperator) {
        this.mobileOperator = mobileOperator;
    }

    public double getMonthPayment() {
        return monthPayment;
    }

    public void setMonthPayment(double monthPayment) {
        this.monthPayment = monthPayment;
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

    public double getPriceMinWithOutLimit() {
        return priceMinWithOutLimit;
    }

    public void setPriceMinWithOutLimit(double priceMinWithOutLimit) {
        this.priceMinWithOutLimit = priceMinWithOutLimit;
    }

    public double getPriceMbWithOutLimit() {
        return priceMbWithOutLimit;
    }

    public void setPriceMbWithOutLimit(double priceMbWithOutLimit) {
        this.priceMbWithOutLimit = priceMbWithOutLimit;
    }

    public double getPriceSmsWithOutLimit() {
        return priceSmsWithOutLimit;
    }

    public void setPriceSmsWithOutLimit(double priceSmsWithOutLimit) {
        this.priceSmsWithOutLimit = priceSmsWithOutLimit;
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobileOperator=" + mobileOperator.getName() +
                ", monthPayment=" + monthPayment +
                ", minute=" + minute +
                ", megabyte=" + megabyte +
                ", sms=" + sms +
                ", priceMinWithOutLimit=" + priceMinWithOutLimit +
                ", priceMbWithOutLimit=" + priceMbWithOutLimit +
                ", priceSmsWithOutLimit=" + priceSmsWithOutLimit +
                ", deleted=" + deleted +
                '}';
    }
}
