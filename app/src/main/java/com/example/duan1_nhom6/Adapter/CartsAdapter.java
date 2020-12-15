package com.example.duan1_nhom6.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom6.BuildConfig;
import com.example.duan1_nhom6.Dao.DaoPhone;
import com.example.duan1_nhom6.Fragment.CartFragment;
import com.example.duan1_nhom6.Fragment.MessageFragment;
import com.example.duan1_nhom6.Model.Carts;
import com.example.duan1_nhom6.Model.Chat;
import com.example.duan1_nhom6.Model.ChatList;
import com.example.duan1_nhom6.Model.Phone;
import com.example.duan1_nhom6.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CartsAdapter extends RecyclerView.Adapter<CartsAdapter.CardsHolder> {

    private Context context;
     ArrayList<Carts> mCards = new ArrayList<>();
     ArrayList<Phone> mPhone;

    boolean selecALL;
    Button btnDel;

    Fragment fragment;
    DatabaseReference firebaseCart;
    DatabaseReference firebasePhone;
    FirebaseAuth firebaseUser;
    private boolean isSelectedAll;

    private static  int TONG = 0;
    public CartsAdapter(Context context, ArrayList<Carts> mCarts,ArrayList<Phone> mphone) {
        this.context = context;
        this.mCards = mCarts;
        this.firebaseCart = FirebaseDatabase.getInstance().getReference("Cart");
        this.firebasePhone = FirebaseDatabase.getInstance().getReference("Phone");
        this.firebaseUser = FirebaseAuth.getInstance();

        this.mPhone = mphone;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public CardsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.carts_item, parent,false);

        
        return new CartsAdapter.CardsHolder(view);
    }
    public void selectAll(){
        isSelectedAll=true;
        notifyDataSetChanged();
    }
    public void unselectall(){
        isSelectedAll=false;
        notifyDataSetChanged();
    }

    public void setBtnDel(Button btnDel) {
        this.btnDel = btnDel;
    }

    @Override
    public void onBindViewHolder(@NonNull final CardsHolder holder, final int position) {

        final Carts carts = mCards.get(position);
        final Phone phone = mPhone.get(position);
        holder.namephone.setText(phone.getPhonename());
        Picasso.get().load(phone.getImage()).into(holder.image_carts);

        Locale locale = new Locale("vi","VN");
        DecimalFormat nf = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setCurrencySymbol("");
        nf.setDecimalFormatSymbols(formatSymbols);

        holder.pricephone.setText(nf.format(phone.getGiatien())+" vnd");
        if (BuildConfig.DEBUG) Log.d("CartsAdapter", "mPhone:" + mPhone.size());
        holder.amountTxt.setText(String.valueOf(carts.getAmount()));
        Log.d("cart", "cart::: "+carts.getAmount());



        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mCards.get(position).setCheck(isChecked);
                }else{
                    mCards.get(position).setCheck(isChecked);
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("phone", carts.getId_phone());
                for(Carts carts1:mCards){
                    if(carts1.isCheck()){
                        firebaseCart.child(firebaseUser.getUid()).child(carts1.getId_phone()).removeValue();
                    }
                }


                ((FragmentActivity)context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new CartFragment())
                        .commit();




            }
        });


        firebaseCart.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        final Carts carts1 = dataSnapshot.getValue(Carts.class);


                        if (carts1.getId_phone().equals(carts.getId_phone())) {
                            holder.amountTxt.setText(String.valueOf(carts1.getAmount()));

                            holder.plusBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int total = carts1.getAmount() + 1;

                                    firebaseCart.child(firebaseUser.getUid())
                                            .child(carts.getId_phone())
                                            .child("amount").setValue(total);
                                    carts.setAmount(total);

                                    notifyDataSetChanged();

                                }
                            });

                            holder.minusBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(carts1.getAmount() > 1) {
                                        int total = carts1.getAmount() - 1;
                                            firebaseCart.child(firebaseUser.getUid())
                                                    .child(carts.getId_phone())
                                                    .child("amount").setValue(total);
                                        carts.setAmount(total);

                                        notifyDataSetChanged();
                                    }

                                }
                            });
                        }
                    }
                }else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



/*        final Phone phones = mPhone.get(position);


        holder.amountTxt.setText(String.valueOf(carts.getAmount()));
        holder.namephone.setText(phones.getPhonename());
        holder.pricephone.setText(String.valueOf(phones.getGiatien()));*/
        Log.d("checkbox", ""+selecALL);

        if (isSelectedAll){
            holder.checkBox.setChecked(true);
        }
        else {
            holder.checkBox.setChecked(false);
        }

        //++++



    }


    @Override
    public int getItemCount() {
        return mCards.size();
    }


    public class CardsHolder extends RecyclerView.ViewHolder{
        public TextView namephone,pricephone;
        public ImageView image_carts;

        CheckBox checkBox;

        ImageButton plusBtn,minusBtn;
        TextView amountTxt;

        public CardsHolder(@NonNull View itemView) {
            super(itemView);
            namephone = itemView.findViewById(R.id.namephone);
            pricephone = itemView.findViewById(R.id.giadienthoai);
            image_carts = itemView.findViewById(R.id.image_carts);
            checkBox = itemView.findViewById(R.id.checkbox_item);


            plusBtn = itemView.findViewById(R.id.plus_btn);
            amountTxt = itemView.findViewById(R.id.text_amount);
            minusBtn = itemView.findViewById(R.id.minus_btn);
        }

    }


}
