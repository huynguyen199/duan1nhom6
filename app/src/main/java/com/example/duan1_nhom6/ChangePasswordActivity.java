package com.example.duan1_nhom6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText oldpassET,  newpassET,  againnewpassET;
    private FirebaseUser user;
    Button changepassBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();

        changepassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String oldpass = oldpassET.getText().toString();
                final String newpass = newpassET.getText().toString();
                String enternewpass = againnewpassET.getText().toString();

                if(TextUtils.isEmpty(oldpass) || TextUtils.isEmpty(newpass) || TextUtils.isEmpty(enternewpass)){
                    Toast.makeText(ChangePasswordActivity.this,"Please fill the Fields",Toast.LENGTH_SHORT).show();
                }else{
                     if(!newpass.equals(enternewpass)){
                         Toast.makeText(ChangePasswordActivity.this,"sai thông tin",Toast.LENGTH_SHORT).show();

                     }else{
                         user = FirebaseAuth.getInstance().getCurrentUser();
                         final String email = user.getEmail();
                         AuthCredential credential = EmailAuthProvider.getCredential(email,oldpass);

                         user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 if(task.isSuccessful()){
                                     user.updatePassword(newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                         public void onComplete(@NonNull Task<Void> task) {
                                             if(!task.isSuccessful()){
                                                 Snackbar snackbar_fail = Snackbar
                                                         .make(v, "Something went wrong. Please try again later", Snackbar.LENGTH_LONG);
                                                 snackbar_fail.show();
                                             }else {
                                                 Snackbar snackbar_su = Snackbar
                                                         .make(v, "Password Successfully Modified", Snackbar.LENGTH_LONG);
                                                 snackbar_su.show();
                                             }
                                         }
                                     });
                                 }else {
                                     Snackbar snackbar_su = Snackbar
                                             .make(v, "Authentication Failed", Snackbar.LENGTH_LONG);
                                     snackbar_su.show();
                                 }
                             }
                         });
                     }
                }

            }
        });

        String id =FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("id", ""+id);
    }

    private void init() {
        oldpassET = findViewById(R.id.edit_oldpass);
        newpassET = findViewById(R.id.edit_newpass);
        againnewpassET = findViewById(R.id.edit_newpass_again);
        changepassBtn = findViewById(R.id.btnChange);
    }
}