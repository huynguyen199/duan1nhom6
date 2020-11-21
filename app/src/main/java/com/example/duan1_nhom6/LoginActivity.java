package com.example.duan1_nhom6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    EditText userETLogin,passETLogin;
    Button Loginbtn,RegisterBtn;
    CheckBox checkBox;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userETLogin = findViewById(R.id.edUserName);
        passETLogin = findViewById(R.id.edPassword);
        Loginbtn = findViewById(R.id.btnLogin);
        RegisterBtn = findViewById(R.id.btnregister);
        checkBox = findViewById(R.id.chkRememberPass);
        auth = FirebaseAuth.getInstance();

        fillnhomatkhau();


        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email_text = userETLogin.getText().toString();
                final String password_text = passETLogin.getText().toString();
                final boolean check = checkBox.isChecked();
                if(TextUtils.isEmpty(email_text) || TextUtils.isEmpty(password_text)){
                    Toast.makeText(LoginActivity.this,"Please fill the Fields",Toast.LENGTH_SHORT).show();
                }else{
                    auth.signInWithEmailAndPassword(email_text,password_text)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        nhotaikhoan(email_text,password_text,check);
                                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        i.setAction(Intent.ACTION_VIEW);
                                        startActivity(i);
                                    }
                                }
                            });
                }
            }
        });


        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);

            }
        });

    }

    private void nhotaikhoan(String user, String pass, boolean checked){
        SharedPreferences pref = getSharedPreferences("rememberpass.dat",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if(checked) {
            editor.putString("taikhoan", user);
            editor.putString("matkhau",pass);
            editor.putBoolean("check",checked);
        }else{
            editor.clear();
        }
        editor.commit();
    }
    //fill mat khau
    private  void fillnhomatkhau(){
        SharedPreferences pref =getSharedPreferences("rememberpass.dat",MODE_PRIVATE);
        boolean checked =pref.getBoolean("check",false);
        if(checked){
            userETLogin.setText(pref.getString("taikhoan",""));
            passETLogin.setText(pref.getString("matkhau",""));
            checkBox.setChecked(checked);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

//        checking for users Existance : saving the current user
        if(firebaseUser != null){
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }else{

        }
    }
}