package com.example.expensemanager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.expensemanager.Adapters.TransactionsAdapter;
import com.example.expensemanager.Fragments.AddTransationFragment;
import com.example.expensemanager.Model.Transaction_Model;
import com.example.expensemanager.Utils.Constants;
import com.example.expensemanager.Utils.Helper;
import com.example.expensemanager.ViewModel.MainViewModel;
import com.example.expensemanager.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Calendar calendar;
    Realm realm;
   public  MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        calendar = Calendar.getInstance();
        Constants.setCategories();
        updateDate();
        setListeners();

    }
    private void setListeners()
    {


          binding.floatingActionButton.setOnClickListener(view -> {
              new AddTransationFragment().show(getSupportFragmentManager(),null);
          });

          binding.nextDateBtn.setOnClickListener(view -> {
              calendar.add(Calendar.MONTH,1);
              updateDate();
          });
        binding.previousDateBtn.setOnClickListener(view -> {
            calendar.add(Calendar.MONTH,-1);
            updateDate();
        });
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
viewModel.realmResultsMutableLiveData.observe(this, new Observer<RealmResults<Transaction_Model>>() {
    @Override
    public void onChanged(RealmResults<Transaction_Model> transaction_models) {
        TransactionsAdapter transactionsAdapter = new TransactionsAdapter(MainActivity.this,transaction_models);
        binding.recycleView.setAdapter(transactionsAdapter);
                if (transaction_models.size()>0){
                    binding.emptyState.setVisibility(View.GONE);
                }else {
                    binding.emptyState.setVisibility(View.VISIBLE);
                }


    }
});

viewModel.totalIncome.observe(this, new Observer<Double>() {
    @Override
    public void onChanged(Double aDouble) {
    binding.incomeLbl.setText(String.valueOf(aDouble));
    }
});
viewModel.totalExpense.observe(this, new Observer<Double>() {
    @Override
    public void onChanged(Double aDouble) {
        binding.expenseLbl.setText(String.valueOf(aDouble));
    }
});

viewModel.totalAmount.observe(this, new Observer<Double>() {
    @Override
    public void onChanged(Double aDouble) {
        binding.totalLbl.setText(String.valueOf(aDouble));
    }
});

viewModel.getTransaction(calendar);

    }
    public void getTransaction()
    {
        viewModel.getTransaction(calendar);
    }
    private void updateDate()
    {
        binding.currentDate.setText(Helper.formatDate(calendar.getTime()));
        viewModel.getTransaction(calendar);
    }

}