package com.example.duan1_nhom6.Dao;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.duan1_nhom6.BuildConfig;
import com.example.duan1_nhom6.Model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DaoUsers {
    Context context;
    View view;
    DatabaseReference databaseUsers;

    public DaoUsers() {
        this.databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

    }

    public DaoUsers(Context context, View view) {
        this.context = context;
        this.view = view;
        this.databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

    }

    public void getAll() {

    }

    public void Update(User user) {
        if (BuildConfig.DEBUG) Log.d("DaoUsers", "user:" + user.getId());
        databaseUsers.child(user.getId()).updateChildren(user.updateUser());

    }
}