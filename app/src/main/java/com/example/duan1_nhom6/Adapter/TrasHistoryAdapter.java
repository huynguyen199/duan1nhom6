package com.example.duan1_nhom6.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom6.Model.TrasHistory;
import com.example.duan1_nhom6.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class TrasHistoryAdapter extends FirebaseRecyclerAdapter<TrasHistory, TrasHistoryAdapter.HistoryHolder> {
    Context context;
    View view;

    public TrasHistoryAdapter(@NonNull FirebaseRecyclerOptions<TrasHistory> options, Context context, View view) {
        super(options);
        this.context = context;
        this.view = view;
    }

    @Override
    protected void onBindViewHolder(@NonNull HistoryHolder holder, int position, @NonNull TrasHistory model) {
        Locale locale = new Locale("vi","VN");
        DecimalFormat nf = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setCurrencySymbol("");

        nf.setDecimalFormatSymbols(formatSymbols);
            holder.text_date.setText(model.getDate());
            holder.text_total.setText(nf.format(model.getTotal())+" vnd");

            Log.d("test",""+model.getDate() );
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.transactionhistory_item, parent, false);
        return new HistoryHolder(view);
    }

    public static class HistoryHolder extends RecyclerView.ViewHolder{
        TextView text_date,text_total;


        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            text_date = itemView.findViewById(R.id.textvDate);
            text_total = itemView.findViewById(R.id.textvTotal);
        }
    }
}
