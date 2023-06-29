package com.example.expensemanager.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.expensemanager.Adapters.TransactionsAdapter;
import com.example.expensemanager.Fragments.AddTransactionFragment;
import com.example.expensemanager.Utils.Constants;
import com.example.expensemanager.Utils.Helper;
import com.example.expensemanager.ViewModel.MainViewModel;
import com.example.expensemanager.databinding.ActivityMainBinding;

import java.util.Calendar;

import io.realm.Realm;

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


          binding.floatingActionButton.setOnClickListener(view -> new AddTransactionFragment().show(getSupportFragmentManager(),null));


        binding.nextMonthButton.setOnClickListener(view -> {
            calendar.add(Calendar.MONTH,1);
            updateDate();
        });
        binding.previousMonthButton.setOnClickListener(view -> {
            calendar.add(Calendar.MONTH,-1);
            updateDate();
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
viewModel.realmResultsMutableLiveData.observe(this, transaction_models -> {
    TransactionsAdapter transactionsAdapter = new TransactionsAdapter(MainActivity.this,transaction_models);
    binding.recyclerView.setAdapter(transactionsAdapter);
            if (transaction_models.size()>0){
                binding.emptyState.setVisibility(View.GONE);
            }else {
                binding.emptyState.setVisibility(View.VISIBLE);
            }


});

viewModel.totalIncome.observe(this, aDouble -> binding.incomeAmountText.setText(String.valueOf(aDouble)));
viewModel.totalExpense.observe(this, aDouble -> binding.expensesAmountText.setText(String.valueOf(aDouble)));

viewModel.totalAmount.observe(this, aDouble -> binding.totalAmountValue.setText(String.valueOf(aDouble)));

viewModel.getTransaction(calendar);

    }
    public void getTransaction()
    {
        viewModel.getTransaction(calendar);
    }
    private void updateDate()
    {

        binding.currentsMonthText.setText(Helper.formatMonth(calendar.getTime()));
        viewModel.getTransaction(calendar);
    }

}