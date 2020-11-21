package com.example.duan1_nhom6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.duan1_nhom6.Adapter.TrasHistoryAdapter;
import com.example.duan1_nhom6.Model.TrasHistory;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class TransactionHistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    FirebaseRecyclerAdapter<TrasHistory, TrasHistoryAdapter.HistoryHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        recyclerView = findViewById(R.id.recyclerview_manager_feedback);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        floatingActionButton = findViewById(R.id.floating);
        final FirebaseRecyclerOptions<TrasHistory> options = new FirebaseRecyclerOptions.Builder<TrasHistory>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("TrasHistory"),TrasHistory.class)
                .build();


        adapter = new TrasHistoryAdapter(options,this,findViewById(android.R.id.content).getRootView());

        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}