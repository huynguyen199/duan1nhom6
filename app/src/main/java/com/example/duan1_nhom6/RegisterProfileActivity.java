package com.example.duan1_nhom6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.duan1_nhom6.Model.Phone;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterProfileActivity extends AppCompatActivity {

    DatabaseReference databaseUsers;
    Button btnregister;
    EditText nameET,numberPhoneET;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_profile);


        final RelativeLayout relativeLayout = findViewById(R.id.relative_register);
        relativeLayout.setVisibility(View.GONE);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        init();
        mAuth = FirebaseAuth.getInstance();
        databaseUsers.child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        Intent i = new Intent(RegisterProfileActivity.this,MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.setAction(Intent.ACTION_VIEW);
                        startActivity(i);
                        progressDialog.dismiss();
                    }else{
                        progressDialog.dismiss();
                        relativeLayout.setVisibility(View.VISIBLE);


                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mName = nameET.getText().toString();
                String mNumberPhone = numberPhoneET.getText().toString();
                if(TextUtils.isEmpty(mName) || TextUtils.isEmpty(mNumberPhone)){
                    Toast.makeText(RegisterProfileActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();

                }else{
                    if(account!=null){
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();

                        HashMap<String, String> map = new HashMap<>();
                        map.put("fullname",nameET.getText().toString());
                        map.put("numberphone",numberPhoneET.getText().toString());
                        map.put("role","user");
                        map.put("status","online");
                        map.put("id",mAuth.getUid());

                        if(account.getPhotoUrl()!=null) {
                            map.put("imageURL", account.getPhotoUrl().toString());
                        }else{
                            map.put("imageURL", "default");
                        }
                        databaseUsers.child(firebaseUser.getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Intent i = new Intent(RegisterProfileActivity.this,MainActivity.class);
                                    startActivity(i);

                                }
                            }
                        });
                    }

                    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

                    if(mUser != null){
                        HashMap<String, String> map = new HashMap<>();
                        map.put("fullname",mName);
                        map.put("numberphone",mNumberPhone);
                        map.put("role","user");
                        map.put("status","online");
                        map.put("id",mAuth.getUid());

                        if(mUser.getPhotoUrl()!=null) {
                            map.put("imageURL", mUser.getPhotoUrl().toString());
                        }else{
                            map.put("imageURL", "default");
                        }
                        databaseUsers.child(mUser.getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Intent i = new Intent(RegisterProfileActivity.this,MainActivity.class);
                                    startActivity(i);

                                }
                            }
                        });
                    }
                }




            }
        });

    }



    private void init(){
        btnregister = findViewById(R.id.btnregister);
        nameET = findViewById(R.id.edUserHoTen);
        numberPhoneET = findViewById(R.id.edSdt);

    }
}