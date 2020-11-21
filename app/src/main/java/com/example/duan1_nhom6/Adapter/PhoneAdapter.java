package com.example.duan1_nhom6.Adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.HalfFloat;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom6.Model.Phone;
import com.example.duan1_nhom6.Model.User;
import com.example.duan1_nhom6.PhoneDetailActivity;
import com.example.duan1_nhom6.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneHolder>{

    private Context context;
    private List<Phone> mPhone;

    public PhoneAdapter(Context context, List<Phone> mPhone) {
        this.context = context;
        this.mPhone = mPhone;
    }

    @NonNull
    @Override
    public PhoneHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.phone_item, parent,false);

        return new PhoneAdapter.PhoneHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PhoneHolder holder, int position) {
        final Phone phone = mPhone.get(position);

        Picasso.get()
                .load(phone.getImage())
                .into(holder.image_phone);

        Locale locale = new Locale("vi","VN");
        DecimalFormat nf = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setCurrencySymbol("");
        nf.setDecimalFormatSymbols(formatSymbols);

        holder.namephone.setText(phone.getPhonename());
        holder.pricephone.setText(nf.format(phone.getGiatien())+" vnd");



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, PhoneDetailActivity.class);

                i.putExtra("phoneid",phone.getId());
                context.startActivity(i);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mPhone.size();
    }

    public void filterList(ArrayList<Phone> filtered){
        mPhone = filtered;
        notifyDataSetChanged();
    }


    public class PhoneHolder extends RecyclerView.ViewHolder{
        public TextView namephone,pricephone;
        public ImageView image_phone;


        public PhoneHolder(@NonNull View itemView) {
            super(itemView);
            namephone = itemView.findViewById(R.id.text_tendienthoai);
            pricephone = itemView.findViewById(R.id.text_giatien);
            image_phone = itemView.findViewById(R.id.image_dienthoai);


        }
    }


}
