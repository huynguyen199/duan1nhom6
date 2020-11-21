package com.example.duan1_nhom6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.duan1_nhom6.Dao.DaoFeedBack;
import com.example.duan1_nhom6.Model.FeedBack;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FeedBackActivity extends AppCompatActivity {

    EditText reasonET,describeET;
    Button sendBtn;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        init();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String reason = reasonET.getText().toString();
                String describe = describeET.getText().toString();
                String id_user = firebaseUser.getUid();

                FeedBack feedBack = new FeedBack(reason,describe,id_user);
                DaoFeedBack daoFeedBack = new DaoFeedBack(FeedBackActivity.this);

                daoFeedBack.sendFeedback(feedBack);


            }
        });

    }

    private void init() {
        reasonET = findViewById(R.id.edit_reason);
        describeET = findViewById(R.id.tenhang);
        sendBtn = findViewById(R.id.btn_send);
    }


}