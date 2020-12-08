package com.example.duan1_nhom6.Dao;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.duan1_nhom6.Model.FeedBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DaoFeedBack {
    Context context;
    View view;
    DatabaseReference databaseFeedback;
    public DaoFeedBack(Context context,View view) {
        this.databaseFeedback = FirebaseDatabase.getInstance().getReference("FeedBack");
        this.context = context;
        this.view = view;
    }


    public DaoFeedBack() {
    }

    public void sendFeedback(FeedBack feedBack){
        String id = databaseFeedback.push().getKey();
        feedBack.setId(id);
        databaseFeedback.child(id).setValue(feedBack.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Gửi thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void insertFeedBack(FeedBack feedBack){
        databaseFeedback.child(feedBack.getId()).setValue(feedBack.toMap());
    }

}
