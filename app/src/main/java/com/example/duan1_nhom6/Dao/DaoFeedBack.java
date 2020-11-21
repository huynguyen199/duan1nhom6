package com.example.duan1_nhom6.Dao;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.duan1_nhom6.Model.FeedBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DaoFeedBack {
    Context context;
    DatabaseReference databaseFeedback;
    public DaoFeedBack(Context context) {
        this.databaseFeedback = FirebaseDatabase.getInstance().getReference("Feedback");
        this.context = context;
    }

    public DaoFeedBack() {
    }

    public void sendFeedback(FeedBack feedBack){
        databaseFeedback.push().setValue(feedBack.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Gửi thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
