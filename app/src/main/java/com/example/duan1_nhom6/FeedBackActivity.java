package com.example.duan1_nhom6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

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
        Toolbar toolbar = findViewById(R.id.toolbar_feedback);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String reason = reasonET.getText().toString();
                String describe = describeET.getText().toString();
                String id_user = firebaseUser.getUid();

                FeedBack feedBack = new FeedBack(reason,describe,id_user);
                DaoFeedBack daoFeedBack = new DaoFeedBack(FeedBackActivity.this,v);

                daoFeedBack.sendFeedback(feedBack);


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


    private void init() {
        reasonET = findViewById(R.id.edit_price);
        describeET = findViewById(R.id.edit_producer);
        sendBtn = findViewById(R.id.btn_send);
    }



}