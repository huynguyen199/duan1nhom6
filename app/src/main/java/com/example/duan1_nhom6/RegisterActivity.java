package com.example.duan1_nhom6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText hotenET,passET,emailET,sdtET,confirmpassET;

    FirebaseAuth auth;
    DatabaseReference myref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });


        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24));

        hotenET = findViewById(R.id.edUserHoTen);
        passET = findViewById(R.id.edMk);
        emailET = findViewById(R.id.edEmail);
        sdtET = findViewById(R.id.edSdt);
        confirmpassET = findViewById(R.id.ednhaplai);
        Button regis = findViewById(R.id.btnregister);

        auth = FirebaseAuth.getInstance();


        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String hoten_text = hotenET.getText().toString();
                String sdt_text = sdtET.getText().toString();
                String Email_text = emailET.getText().toString();
                String pass_text = passET.getText().toString();
                String cofirmpass_text = confirmpassET.getText().toString();

                if(TextUtils.isEmpty(hoten_text) || TextUtils.isEmpty(Email_text)
                        ||TextUtils.isEmpty(pass_text) || TextUtils.isEmpty(sdt_text)
                        || TextUtils.isEmpty(cofirmpass_text)){
                    Toast.makeText(RegisterActivity.this,"Please Fill All Fields",Toast.LENGTH_SHORT).show();
                }else if(!cofirmpass_text.equals(pass_text)){
                    Toast.makeText(RegisterActivity.this,"sai thông tin",Toast.LENGTH_SHORT).show();
                }else{
                    RegisterNow(hoten_text,sdt_text,Email_text,pass_text);
                }


            }
        });

    }

    public void setSupportActionBar(Toolbar toolbar) {
    }


    private void RegisterNow(final String hoten, final String sodienthoai, String email, String password){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            myref = FirebaseDatabase.getInstance().getReference("Users")
                                    .child(userid);

                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("fullname",hoten);
                            hashMap.put("role","user");
                            hashMap.put("numberphone",sodienthoai);
                            hashMap.put("imageURL", "default");
                            hashMap.put("status", "offline");

                            //opening the main activity after sucess registration
                            myref.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Intent i = new Intent(RegisterActivity.this,MainActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                        startActivity(i);
                                        finish();
                                    }

                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this,"Invalid Email or password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}