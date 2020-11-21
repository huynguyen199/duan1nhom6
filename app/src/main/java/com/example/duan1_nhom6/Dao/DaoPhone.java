package com.example.duan1_nhom6.Dao;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.duan1_nhom6.Adapter.CartsAdapter;
import com.example.duan1_nhom6.BuildConfig;
import com.example.duan1_nhom6.Model.Carts;
import com.example.duan1_nhom6.Model.Phone;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DaoPhone {

    private View view;
    private Context context;

    DatabaseReference firebaseDatabase;
    DatabaseReference firebaseCart;
    DatabaseReference firebasePhone;
    public DaoPhone() {
    }

    public DaoPhone(View view, Context context) {
        this.view = view;
        this.context = context;
        this.firebaseDatabase = FirebaseDatabase.getInstance().getReference("Phone");
    }
    public void getAll(){
        Log.d("get", "getAll: ");
        firebaseCart = FirebaseDatabase.getInstance().getReference().child("Cart");
        firebasePhone = FirebaseDatabase.getInstance().getReference("Phone");


        firebasePhone.child("phone2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Phone carts = null;
                if (snapshot.exists()) {
                    Log.d("gg", "gton tai");
                        carts = snapshot.getValue(Phone.class);
                        Log.d("cartsphone", "" + carts.getId());



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (BuildConfig.DEBUG) Log.d("DaoPhone", "error:" + error.toException());
                if (BuildConfig.DEBUG) Log.d("DaoPhone", "error:" + error.getMessage());
            }
        });
        
    }

    
}
