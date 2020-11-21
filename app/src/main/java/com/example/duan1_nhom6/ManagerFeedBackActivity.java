package com.example.duan1_nhom6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.duan1_nhom6.Adapter.ManagerFeedbackAdapter;
import com.example.duan1_nhom6.Model.FeedBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManagerFeedBackActivity extends AppCompatActivity {

    DatabaseReference databaseFeedback;
    RecyclerView recyclerView;

    ManagerFeedbackAdapter adapter;
    ArrayList<FeedBack> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_feed_back);


        recyclerView = findViewById(R.id.recyclerview_manager_feedback);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        databaseFeedback = FirebaseDatabase.getInstance().getReference("FeedBack");

        list = new ArrayList<>();


        databaseFeedback.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    if(snapshot.exists()){
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            FeedBack feedBack = dataSnapshot.getValue(FeedBack.class);
                            list.add(feedBack);
                        }
                        adapter = new ManagerFeedbackAdapter(list, ManagerFeedBackActivity.this);
                        recyclerView.setAdapter(adapter);

                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}