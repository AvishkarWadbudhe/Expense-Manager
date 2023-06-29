package com.example.expensemanager.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.expensemanager.Model.Transaction_Model;
import com.example.expensemanager.Utils.Constants;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainViewModel extends AndroidViewModel {
  public   MutableLiveData<RealmResults<Transaction_Model>> realmResultsMutableLiveData = new MutableLiveData<>();

  public MutableLiveData<Double> totalIncome = new MutableLiveData<>();
    public MutableLiveData<Double> totalExpense = new MutableLiveData<>();
    public MutableLiveData<Double> totalAmount = new MutableLiveData<>();
        Realm realm;
        Calendar calendar;

    public MainViewModel(@NonNull Application application) {
        super(application);
        Realm.init(application);
        setDatabase();

    }
    void setDatabase()
    {
        realm = Realm.getDefaultInstance();
    }
    public void addTransaction(Transaction_Model transaction_model)
    {
        realm.beginTransaction();

        realm.copyToRealmOrUpdate(transaction_model);

        realm.commitTransaction();
    }
    public void getTransaction(Calendar calendar) {
        this.calendar = calendar;
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Set the calendar to the first day of the month
        calendar.set(year, month, 1, 0, 0, 0);
        Date startOfMonth = calendar.getTime();

        // Set the calendar to the last day of the month
        calendar.set(year, month, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
        Date endOfMonth = calendar.getTime();

        // Adjust the start and end dates if the selected date is from yesterday
        if (calendar.get(Calendar.DAY_OF_MONTH) == day - 1) {
            startOfMonth = calendar.getTime();
            endOfMonth = calendar.getTime();
        }

        RealmResults<Transaction_Model> transaction_models = realm.where(Transaction_Model.class)
                .greaterThanOrEqualTo("date", startOfMonth)
                .lessThanOrEqualTo("date", endOfMonth)
                .findAll();

        double income = realm.where(Transaction_Model.class)
                .greaterThanOrEqualTo("date", startOfMonth)
                .lessThanOrEqualTo("date", endOfMonth)
                .equalTo("type", Constants.INCOME)
                .sum("amount")
                .doubleValue();

        double expense = realm.where(Transaction_Model.class)
                .greaterThanOrEqualTo("date", startOfMonth)
                .lessThanOrEqualTo("date", endOfMonth)
                .equalTo("type", Constants.EXPENSE)
                .sum("amount")
                .doubleValue();

        double total = realm.where(Transaction_Model.class)
                .greaterThanOrEqualTo("date", startOfMonth)
                .lessThanOrEqualTo("date", endOfMonth)
                .sum("amount")
                .doubleValue();

        totalIncome.setValue(income);
        totalExpense.setValue(expense);
        totalAmount.setValue(total);

        realmResultsMutableLiveData.setValue(transaction_models);
    }





    public void deleteTransaction(Transaction_Model transaction_model)
    {
        realm.beginTransaction();
        transaction_model.deleteFromRealm();
        realm.commitTransaction();
        getTransaction(calendar);
    }

}
