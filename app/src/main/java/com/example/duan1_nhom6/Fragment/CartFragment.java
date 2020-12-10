package com.example.duan1_nhom6.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.duan1_nhom6.Adapter.CartsAdapter;
import com.example.duan1_nhom6.BuildConfig;
import com.example.duan1_nhom6.Dao.DaoFeedBack;
import com.example.duan1_nhom6.Dao.DaoPhone;
import com.example.duan1_nhom6.Model.Carts;
import com.example.duan1_nhom6.Model.ChatList;
import com.example.duan1_nhom6.Model.Phone;
import com.example.duan1_nhom6.Model.TrasHistory;
import com.example.duan1_nhom6.Model.User;
import com.example.duan1_nhom6.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class CartFragment extends Fragment {
    public SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseTrasHistory;
    private RecyclerView recyclerView;
    ArrayList<Carts> listCart;
    ArrayList<Phone> listPhone;

    CartsAdapter adapter;
    DatabaseReference firebaseCart;
    DatabaseReference firebasePhone;

    public static int TOTAL = 0;


    public CheckBox checkBox;
    Button btndel,btnPay;
    TextView texttotal;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("Main", "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Main", "onCreateView()");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_carts);
        btndel = view.findViewById(R.id.btnDel);
        btnPay = view.findViewById(R.id.btn_thanhtoan);
        texttotal = view.findViewById(R.id.textTotal);

        checkBox = view.findViewById(R.id.checkbox);
        Log.d("check", ""+checkBox.isChecked());


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        listCart = new ArrayList<>();

        firebaseCart = FirebaseDatabase.getInstance().getReference("Cart");
        firebasePhone = FirebaseDatabase.getInstance().getReference("Phone");



        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  handLingPay();
            }
        });


                firebaseCart.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listCart.clear();
                        Carts carts = null;
                        if(snapshot.exists()){
                            for(DataSnapshot dataSnapshot :snapshot.getChildren()) {
                                 carts = dataSnapshot.getValue(Carts.class);
                                 listCart.add(carts);

                            }
                            listPhone();


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        return view;
    }
    public void handLingPay(){
        databaseTrasHistory = FirebaseDatabase.getInstance().getReference("TrasHistory");



        firebaseCart.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TOTAL = 0;
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        final Carts carts1 = dataSnapshot.getValue(Carts.class);
                        if (BuildConfig.DEBUG) Log.d("CartFragment", "carts1:" + carts1.getId_phone());
                        firebasePhone.child(carts1.getId_phone()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()) {
                                        Phone phone = snapshot.getValue(Phone.class);
                                        TOTAL += phone.getGiatien()*carts1.getAmount();
                                        DecimalFormat nf = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.FRANCE);
                                        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
                                        formatSymbols.setCurrencySymbol("vnd");
                                        nf.setDecimalFormatSymbols(formatSymbols);
                                        texttotal.setText("Tổng cộng: "+nf.format(TOTAL));
                                        Date now = new Date();
                                        String date = sdf.format(now.getTime());
                                        TrasHistory trasHistory = new TrasHistory(date,firebaseUser.getUid(),TOTAL);
                                        databaseTrasHistory.push().setValue(trasHistory.toMap());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void listPhone(){
        listPhone = new ArrayList<>();
        firebasePhone.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPhone.clear();
                if(snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Phone phone = snapshot1.getValue(Phone.class);
                        Log.d("phone", "" + phone.getId());
                        for (Carts carts : listCart) {

                            if (phone.getId().equals(carts.getId_phone())) {
                                listPhone.add(phone);
                            }

                        }

                    }
                }
                adapter = new CartsAdapter(getContext(), listCart,listPhone);

                adapter.setBtnDel(btndel);


                //checkbox All
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            adapter.selectAll();
                        }
                        else {
                            adapter.unselectall();
                        }
                    }
                });
                adapter.setFragment(new CartFragment());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}