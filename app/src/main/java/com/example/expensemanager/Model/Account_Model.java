package com.example.expensemanager.Model;

public class Account_Model {
    private double accountAmount;
    private String accountName;

    public Account_Model() {
    }

    public Account_Model(double accountAmount, String accountName) {
        this.accountAmount = accountAmount;
        this.accountName = accountName;
    }

    public double getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(double accountAmount) {
        this.accountAmount = accountAmount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}

