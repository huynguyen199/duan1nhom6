package com.example.duan1_nhom6.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom6.Dao.DaoPhone;
import com.example.duan1_nhom6.Model.Phone;
import com.example.duan1_nhom6.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ManagerPhoneAdapter extends RecyclerView.Adapter<ManagerPhoneAdapter.ManagerPhoneHolder>{
    DaoPhone daoPhone;
    private Context context;
    private ArrayList<Phone> mPhone;
    DatabaseReference databasePhone;
    public ManagerPhoneAdapter(Context context, ArrayList<Phone> mPhone) {
        this.context = context;
        this.mPhone = mPhone;
        this.databasePhone = FirebaseDatabase.getInstance().getReference("Phone");
    }

    @NonNull
    @Override
    public ManagerPhoneHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.manager_phone_item, parent,false);

        return new ManagerPhoneAdapter.ManagerPhoneHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerPhoneHolder holder, final int position) {
            final Phone phone = mPhone.get(position);
             daoPhone = new DaoPhone(holder.itemView,context);

        holder.namephone.setText(phone.getPhonename());
            holder.pricephone.setText(String.valueOf(phone.getGiatien()));
            holder.producer.setText(phone.getTenhang());

            if(phone.getImage()!=null) {
                Picasso.get().load(phone.getImage()).into(holder.image_phone);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateProfile(position);
                }
            });
            holder.image_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    daoPhone.delete(phone);
                }
            });

    }
    public void updateProfile(int position){
        final DialogPlus dialog = DialogPlus.newDialog(context)
                .setGravity(Gravity.CENTER)
                .setContentHolder(new ViewHolder(R.layout.dialog_update_phone))
                .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
        final View view = dialog.getHolderView();
        final EditText nameET = view.findViewById(R.id.edit_idPhone);
        final EditText priceET =view.findViewById(R.id.edit_price);
        final EditText producerET =view.findViewById(R.id.edit_producer);
        final EditText imageET =view.findViewById(R.id.imagePhone);
        Button updateBtn =view.findViewById(R.id.updatePhone);
        final Phone phone = mPhone.get(position);
        view.setTag(dialog);
        nameET.setText(phone.getPhonename());
        priceET.setText(String.valueOf(phone.getGiatien()));
        producerET.setText(phone.getTenhang());
        imageET.setText(phone.getImage());

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                int price = Integer.parseInt(priceET.getText().toString());
                String producer = producerET.getText().toString();
                String image = imageET.getText().toString();
                Phone phone1 = new Phone(price,image,name,producer);
                phone1.setId(phone.getId());
                daoPhone.update(phone1);
            }
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return mPhone.size();
    }

    public class ManagerPhoneHolder extends RecyclerView.ViewHolder{
        public TextView namephone,pricephone,producer;
        public ImageView image_phone,image_del;


        public ManagerPhoneHolder(@NonNull View itemView) {
            super(itemView);
            namephone = itemView.findViewById(R.id.edit_idPhone);
            pricephone = itemView.findViewById(R.id.pricePhone);
            producer = itemView.findViewById(R.id.edit_producer);
            image_phone = itemView.findViewById(R.id.image_managerPhone);
            image_del = itemView.findViewById(R.id.image_del);


        }
    }
}
