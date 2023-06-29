package com.example.expensemanager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.Model.Account_Model;
import com.example.expensemanager.R;
import com.example.expensemanager.databinding.ItemAccountsBinding;

import java.util.ArrayList;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccountViewHolder>{

    Context context;
    ArrayList<Account_Model> accountArrayList;

    public interface AccountsClickListener{
        void onAccountSelected(Account_Model account);
    }

    AccountsClickListener accountsClickListener;

    public AccountsAdapter(Context context, ArrayList<Account_Model> accountArrayList, AccountsClickListener accountsClickListener)
{
    this.context= context;
    this.accountArrayList = accountArrayList;
    this.accountsClickListener = accountsClickListener;
}
    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(context).inflate(R.layout.item_accounts,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
Account_Model account = accountArrayList.get(position);
holder.binding.accountName.setText(account.getAccountName());
holder.itemView.setOnClickListener(view -> accountsClickListener.onAccountSelected(account));

    }

    @Override
    public int getItemCount() {
        return accountArrayList.size();
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder{
        ItemAccountsBinding binding;
        public AccountViewHolder(@NonNull View itemView) {

            super(itemView);
            binding = ItemAccountsBinding.bind(itemView);
        }
    }
}
