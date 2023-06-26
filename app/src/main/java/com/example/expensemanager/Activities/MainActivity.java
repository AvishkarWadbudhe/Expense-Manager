package com.example.expensemanager.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.expensemanager.Adapters.TransactionsAdapter;
import com.example.expensemanager.Fragments.AddTransationFragment;
import com.example.expensemanager.Model.Transaction_Model;
import com.example.expensemanager.Utils.Constants;
import com.example.expensemanager.Utils.Helper;
import com.example.expensemanager.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        calendar = Calendar.getInstance();
        Constants.setCategories();
        updateDate();
        setListeners();
    }
    private void setListeners()
    {
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setTitle("Transactions");

          binding.floatingActionButton.setOnClickListener(view -> {
              new AddTransationFragment().show(getSupportFragmentManager(),null);
          });

          binding.nextDateBtn.setOnClickListener(view -> {
              calendar.add(Calendar.DATE,1);
              updateDate();
          });
        binding.previousDateBtn.setOnClickListener(view -> {
            calendar.add(Calendar.DATE,-1);
            updateDate();
        });
        ArrayList<Transaction_Model> transaction_models = new ArrayList<>();
        transaction_models.add(new Transaction_Model("Income","Business","Cash","Some hote",new Date(),500,2));
        transaction_models.add(new Transaction_Model("Expense","Investment","UPI","Some hote",new Date(),500,3));
        transaction_models.add(new Transaction_Model("Income","Rent","OTHER","Some hote",new Date(),500,4));
        transaction_models.add(new Transaction_Model("Expense","Business","PhonePe","Some hote",new Date(),500,5));

        TransactionsAdapter transactionsAdapter = new TransactionsAdapter(this,transaction_models);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleView.setAdapter(transactionsAdapter);
    }
    void updateDate()
    {


        binding.currentDate.setText(Helper.formatDate(calendar.getTime()));
    }
}