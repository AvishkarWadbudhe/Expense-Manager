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
    public void getTransaction(Calendar calendar)
    {
        this.calendar =calendar;
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        RealmResults<Transaction_Model> transaction_models =   realm.where(Transaction_Model.class)
                .greaterThanOrEqualTo("date",calendar.getTime())
                        .lessThan("date",new Date(calendar.getTime().getTime()
                                +(24*60*60*1000)))
                                .findAll();

      double income =   realm.where(Transaction_Model.class)
                .greaterThanOrEqualTo("date",calendar.getTime())
                .lessThan("date",new Date(calendar.getTime().getTime()
                        +(24*60*60*1000))).equalTo("type", Constants.INCOME)
              .sum("amount")
                      .doubleValue();

        double expense =   realm.where(Transaction_Model.class)
                .greaterThanOrEqualTo("date",calendar.getTime())
                .lessThan("date",new Date(calendar.getTime().getTime()
                        +(24*60*60*1000))).equalTo("type", Constants.EXPENSE)
                .sum("amount")
                .doubleValue();

        double total =   realm.where(Transaction_Model.class)
                .greaterThanOrEqualTo("date",calendar.getTime())
                .lessThan("date",new Date(calendar.getTime().getTime()
                        +(24*60*60*1000)))
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
