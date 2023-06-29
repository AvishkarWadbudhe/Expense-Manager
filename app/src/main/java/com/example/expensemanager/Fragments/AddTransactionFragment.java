package com.example.expensemanager.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.expensemanager.Activities.MainActivity;
import com.example.expensemanager.Adapters.AccountsAdapter;
import com.example.expensemanager.Adapters.CategoryAdapter;
import com.example.expensemanager.Model.Account_Model;
import com.example.expensemanager.Model.Category_Model;
import com.example.expensemanager.Model.Transaction_Model;
import com.example.expensemanager.R;
import com.example.expensemanager.Utils.Constants;
import com.example.expensemanager.databinding.CategoryListDialogBinding;
import com.example.expensemanager.databinding.FragmentAddTransationBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddTransactionFragment extends BottomSheetDialogFragment {


    public AddTransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
FragmentAddTransationBinding binding;


    Transaction_Model transaction_model ;
    boolean isIncomeSelected = false;
    boolean isExpenseSelected = false;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddTransationBinding.inflate(inflater);
        transaction_model = new Transaction_Model();

        binding.incomeBtn.setOnClickListener(view -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.textcolor));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.green));
            transaction_model.setType(Constants.INCOME);
            isIncomeSelected = true;
            isExpenseSelected = false;
        });
        binding.expenseBtn.setOnClickListener(view -> {
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.textcolor));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.red));
            transaction_model.setType(Constants.EXPENSE);
            isIncomeSelected = false;
            isExpenseSelected = true;
        });

        binding.date.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
            datePickerDialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                calendar.set(Calendar.MONTH, datePicker.getMonth());
                calendar.set(Calendar.YEAR, datePicker.getYear());

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");

                String dateToShow = dateFormat.format(calendar.getTime());
                binding.date.setText(dateToShow);

                transaction_model.setDate(calendar.getTime());
                transaction_model.setId(calendar.getTime().getTime());

            });
            datePickerDialog.show();
        });


        binding.category.setOnClickListener(c -> {
            CategoryListDialogBinding dialogBinding = CategoryListDialogBinding.inflate(inflater);
            AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();
            categoryDialog.setView(dialogBinding.getRoot());


            CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), Constants.categories, category -> {
                binding.category.setText(category.getCategoryName());
                transaction_model.setCategory(category.getCategoryName());
                categoryDialog.dismiss();
            });
            dialogBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            dialogBinding.recyclerView.setAdapter(categoryAdapter);

            categoryDialog.show();


        });


        binding.account.setOnClickListener(c -> {
            CategoryListDialogBinding dialogBinding = CategoryListDialogBinding.inflate(inflater);
            AlertDialog accountsDialog = new AlertDialog.Builder(getContext()).create();
            accountsDialog.setView(dialogBinding.getRoot());

            ArrayList<Account_Model> accountArrayList = new ArrayList<>();
            accountArrayList.add(new Account_Model(0, "Cash"));
            accountArrayList.add(new Account_Model(0, "Bank"));
            accountArrayList.add(new Account_Model(0, "Paytm"));
            accountArrayList.add(new Account_Model(0, "UPI"));
            accountArrayList.add(new Account_Model(0, "PhonePe"));

            AccountsAdapter accountsAdapter = new AccountsAdapter(getContext(), accountArrayList, account -> {
                binding.account.setText(account.getAccountName());
                transaction_model.setAccount(account.getAccountName());
                accountsDialog.dismiss();
            });

            dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            dialogBinding.recyclerView.setAdapter(accountsAdapter);

            accountsDialog.show();
        });
        binding.saveTransactionBtn.setOnClickListener(view -> {
            if (validateFields()) {
                double amount = Double.parseDouble(binding.amount.getText().toString());
                String note = binding.note.getText().toString();

                if (transaction_model.getType().equalsIgnoreCase(Constants.EXPENSE)) {
                    transaction_model.setAmount(amount * -1);
                } else {
                    transaction_model.setAmount(amount);
                }
                transaction_model.setNote(note);

                ((MainActivity) getActivity()).viewModel.addTransaction(transaction_model);
                ((MainActivity) getActivity()).getTransaction();
                dismiss();
            }
        });

        return binding.getRoot();
    }
    private boolean validateFields() {
        if (binding.category.getText().toString().isEmpty()) {
            showToast("Please select Category");
            return false;
        } else if (!isIncomeSelected && !isExpenseSelected) {
            showToast("Select Type");
            return false;
        } else if (binding.amount.getText().toString().isEmpty()) {
            showToast("Enter Amount");
            return false;
        } else if (binding.account.getText().toString().isEmpty()) {
            showToast("Enter Account");
            return false;
        } else if (binding.date.getText().toString().isEmpty()) {
            showToast("Select Date");
            return false;
        } else {
            if(binding.note.getText().toString().trim().isEmpty())
            {
                binding.note.setText("Null");
            }
            return true;
        }
    }
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}