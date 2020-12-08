package com.example.duan1_nhom6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText editEmail;
    Button sendmyEmail;
    ProgressBar probar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();

        firebaseAuth = firebaseAuth.getInstance();
        sendmyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                probar.setVisibility(View.VISIBLE);
                    if(!TextUtils.isEmpty(editEmail.getText().toString())) {
                        firebaseAuth.sendPasswordResetEmail(editEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    probar.setVisibility(View.GONE);
                                    Toast.makeText(ForgotPasswordActivity.this, "Gửi thành công ,bạn vui lòng kiểm tra email", Toast.LENGTH_SHORT).show();
                                } else {
                                    probar.setVisibility(View.GONE);
                                    Toast.makeText(ForgotPasswordActivity.this, "Vui lòng nhập đúng thông tin", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(ForgotPasswordActivity.this, "Bạn chưa điền thông tin", Toast.LENGTH_SHORT).show();

                    }
            }
        });

    }
    public void init(){
        editEmail = findViewById(R.id.ed_Email);
        sendmyEmail = findViewById(R.id.btn_send_email);
        probar = findViewById(R.id.progressBar);

    }
}