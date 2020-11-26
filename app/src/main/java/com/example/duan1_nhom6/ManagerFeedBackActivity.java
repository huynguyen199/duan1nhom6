package com.example.duan1_nhom6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import com.example.duan1_nhom6.Adapter.ManagerFeedbackAdapter;
import com.example.duan1_nhom6.Dao.DaoFeedBack;
import com.example.duan1_nhom6.Model.FeedBack;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;

public class ManagerFeedBackActivity extends AppCompatActivity {

    DatabaseReference databaseFeedback;
    RecyclerView recyclerView;

    ManagerFeedbackAdapter adapter;
    ArrayList<FeedBack> list;
    FloatingActionButton floatingActionButton;

    EditText idFeedbackET,reasonET,describeET,senderET;
    Button insertBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_feed_back);

        Toolbar toolbar = findViewById(R.id.toolbar_transaction_history);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });

        floatingActionButton = findViewById(R.id.floating);
        recyclerView = findViewById(R.id.recyclerview_manager_phone);
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

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialog = DialogPlus.newDialog(v.getContext())
                        .setGravity(Gravity.CENTER)
                        .setContentHolder(new ViewHolder(R.layout.dialog_feedback))
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                final View view = dialog.getHolderView();
                view.setTag(dialog);
                idFeedbackET = view.findViewById(R.id.edit_idPhone);
                reasonET = view.findViewById(R.id.edit_price);
                describeET = view.findViewById(R.id.edit_producer);
                senderET = view.findViewById(R.id.edit_phoneName);
                insertBtn = view.findViewById(R.id.updatePhone);

                idFeedbackET.setText("feedback1");
                reasonET.setText("ly do abc");
                describeET.setText("mieu ta xyz");
                senderET.setText("yau7fcv90WaGw31TbK3dMh5H81s1");
                insertBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = idFeedbackET.getText().toString();
                        String reason = reasonET.getText().toString();
                        String describe = describeET.getText().toString();
                        String sender = senderET.getText().toString();
                        FeedBack feedBack = new FeedBack(reason,describe,sender,id);
                        DaoFeedBack daoFeedBack = new DaoFeedBack(ManagerFeedBackActivity.this,v);
                        daoFeedBack.insertFeedBack(feedBack);
                        adapter.notifyDataSetChanged();
                    }
                });

                dialog.show();

            }
        });

    }

    private void setSupportActionBar(Toolbar toolbar) {
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}