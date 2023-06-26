package com.example.expensemanager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.Model.Category_Model;
import com.example.expensemanager.Model.Transaction_Model;
import com.example.expensemanager.R;
import com.example.expensemanager.Utils.Constants;
import com.example.expensemanager.Utils.Helper;
import com.example.expensemanager.databinding.ItemTransactionsBinding;

import java.util.ArrayList;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>{

    Context context;
    ArrayList<Transaction_Model>transaction_models;
    public TransactionsAdapter(Context context, ArrayList<Transaction_Model>transaction_models){
        this.context = context;
        this.transaction_models = transaction_models;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.item_transactions,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction_Model transaction_model = transaction_models.get(position);

        holder.binding.transactionAmount.setText(String.valueOf(transaction_model.getAmount()));
        holder.binding.transactionDate.setText(Helper.formatDate(transaction_model.getDate()));
        holder.binding.transactionCategory.setText(transaction_model.getCategory());
        holder.binding.accountLbl.setText(transaction_model.getAccount());

        Category_Model categoryModel = Constants.getCategoryDetails(transaction_model.getCategory());
        holder.binding.categoryIcon.setImageResource(categoryModel.getCategoryImage());
        holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(categoryModel.getCategoryColor()));

        holder.binding.accountLbl.setBackgroundTintList(context.getColorStateList(Constants.getAccountsColor(transaction_model.getAccount())));

       if(transaction_model.getType().equalsIgnoreCase("Income"))
       {
           holder.binding.transactionAmount.setTextColor(context.getColor(R.color.green));
       }else  if(transaction_model.getType().equalsIgnoreCase("Expense"))
       {  holder.binding.transactionAmount.setTextColor(context.getColor(R.color.red));}
    }

    @Override
    public int getItemCount() {
        return transaction_models.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder{

        ItemTransactionsBinding binding;


        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemTransactionsBinding.bind(itemView);

        }
    }
}
