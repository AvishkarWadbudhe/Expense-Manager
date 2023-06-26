package com.example.expensemanager.Utils;

import com.example.expensemanager.Model.Category_Model;
import com.example.expensemanager.R;

import java.util.ArrayList;

public class Constants {
    public static String INCOME = "INCOME";
    public static String EXPENSE = "EXPENSE";

    public static ArrayList<Category_Model> categories;

    public static int DAILY = 0;
    public static int MONTHLY = 1;
    public static int CALENDAR = 2;
    public static int SUMMARY = 3;
    public static int NOTES = 4;

    public static int SELECTED_TAB = 0;

    public static void setCategories() {
        categories = new ArrayList<>();
        categories.add(new Category_Model("Salary",R.drawable.ic_salary,R.color.cat1));
        categories.add(new Category_Model("Business",R.drawable.ic_business,R.color.cat2));
        categories.add(new Category_Model("Investment",R.drawable.ic_investment,R.color.cat3));
        categories.add(new Category_Model("Loan",R.drawable.ic_loan,R.color.cat4));
        categories.add(new Category_Model("Rent",R.drawable.ic_rent,R.color.cat5));
        categories.add(new Category_Model("Other",R.drawable.ic_other,R.color.cat6));

    }

    public static Category_Model getCategoryDetails(String categoryName) {
        for (Category_Model cat :
                categories) {
            if (cat.getCategoryName().equals(categoryName)) {
                return cat;
            }
        }
        return null;
    }

    public static int getAccountsColor(String accountName) {
        switch (accountName) {
            case "Bank":
                return R.color.bank_color;
            case "Cash":
                return R.color.cash_color;
            case "UPI":
                return R.color.upi_color;
            default:
                return R.color.other_color;
        }
    }

}
