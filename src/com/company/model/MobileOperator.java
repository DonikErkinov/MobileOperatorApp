package com.company.model;

import com.company.model.base.BaseModel;

import java.util.List;
import java.util.UUID;

public class MobileOperator extends BaseModel {
    private String name;
    private List<Integer> codes;
    private double balance;
    private int maxCallDuration;

    public MobileOperator(String name, List<Integer> codes, int maxCallDuration) {
        this.name = name;
        this.codes = codes;
        this.maxCallDuration = maxCallDuration;
    }

    public int getMaxCallDuration() {
        return maxCallDuration;
    }

    public void setMaxCallDuration(int maxCallDuration) {
        this.maxCallDuration = maxCallDuration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getCodes() {
        return codes;
    }

    public void setCodes(List<Integer> codes) {
        this.codes = codes;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "MobileOperator{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", codes=" + codes +
                ", balance=" + balance +
                ", maxCallDuration=" + maxCallDuration +
                ", deleted=" + deleted +
                '}';
    }
}
