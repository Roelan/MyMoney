package com.example.mymoney.ui.transactions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoney.R;
import com.example.mymoney.data.ExpensesData;

import java.util.ArrayList;
import java.util.List;

public class TranscriptionAdapter extends RecyclerView.Adapter<TranscriptionAdapter.TranscriptionHolder> {

    List<ExpensesData> expensesData = new ArrayList<>();

    @NonNull
    @Override
    public TranscriptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_transactions, parent, false);
        return new TranscriptionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TranscriptionHolder holder, int position) {
        ExpensesData currentExpenses = expensesData.get(position);
        holder.tvName.setText(currentExpenses.getName());
        holder.tvDate.setText(currentExpenses.getDate());
        holder.tvType.setText(currentExpenses.getType());
        holder.tvPrise.setText("€ " + currentExpenses.getPrise());

        switch (currentExpenses.getType()) {
            case "Transport":
                holder.imageType.setImageResource(R.drawable.ic_round_directions_car_24);
                break;
            case "Food":
                holder.imageType.setImageResource(R.drawable.ic_baseline_restaurant_menu_24);
                break;
            case "Bills":
                holder.imageType.setImageResource(R.drawable.ic_baseline_attach_money_24);
                break;
            case "Shopping":
                holder.imageType.setImageResource(R.drawable.ic_baseline_shopping_cart_24);
                break;
        }
    }

    public ExpensesData getPosition(int position) {
        return expensesData.get(position);
    }

    @Override
    public int getItemCount() {
        return expensesData.size();
    }

    public void setExpensesData(List<ExpensesData> expensesData) {
        this.expensesData = expensesData;
        notifyDataSetChanged();
    }

    public class TranscriptionHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvDate;
        TextView tvPrise;
        TextView tvType;
        ImageView imageType;

        public TranscriptionHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.text_transcription_name);
            tvDate = itemView.findViewById(R.id.text_transcription_date);
            tvPrise = itemView.findViewById(R.id.text_transcription_prise);
            tvType = itemView.findViewById(R.id.text_transcription_type);
            imageType = itemView.findViewById(R.id.image_transaction_icon);
        }
    }
}
