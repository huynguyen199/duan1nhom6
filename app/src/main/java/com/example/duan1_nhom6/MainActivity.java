package com.example.duan1_nhom6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.duan1_nhom6.Fragment.CartFragment;
import com.example.duan1_nhom6.Fragment.HomeFragment;
import com.example.duan1_nhom6.Fragment.MessageFragment;
import com.example.duan1_nhom6.Fragment.ProfileAdminFragment;
import com.example.duan1_nhom6.Fragment.ProfileFragment;
import com.example.duan1_nhom6.Model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static String ROLE;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    DatabaseReference myref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");




        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container,new HomeFragment())
                .commit();

        final BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);

        String userUid = firebaseUser.getUid();
        if (BuildConfig.DEBUG) Log.d("MainActivity", userUid);

        FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(userUid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            User user = snapshot.getValue(User.class);


                            ROLE = user.getRole();

                            final String finalRole = ROLE;
                            bottomnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                @Override
                                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                                    Fragment selectedFragment = null;

                                    switch (menuItem.getItemId()){
                                        case R.id.nav_Home:
                                            selectedFragment = new HomeFragment();break;

                                        case R.id.nav_user:
                                            Log.d("user", finalRole);

                                            if(finalRole.equals("user")){
                                                Log.d("user", "vao user "+finalRole);
                                                selectedFragment = new ProfileFragment();break;
                                            }else{
                                                Log.d("admin", "vao admin"+finalRole);

                                                selectedFragment = new ProfileAdminFragment();break;

                                            }


                                        case R.id.nav_message:
                                            selectedFragment = new MessageFragment();break;

                                        case R.id.nav_shopping:

                                            selectedFragment = new CartFragment();break;

                                    }
                                    if(selectedFragment!=null){
                                        getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.fragment_container,selectedFragment).commit();
                                    }
                                    return true;
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String uid = firebaseUser.getUid();
        databaseReference.child(uid).child("status").setValue("online");
    }
}
