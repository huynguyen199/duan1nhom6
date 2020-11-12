package com.example.duan1_nhom6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.duan1_nhom6.Fragment.CartFragment;
import com.example.duan1_nhom6.Fragment.HomeFragment;
import com.example.duan1_nhom6.Fragment.MessageFragment;
import com.example.duan1_nhom6.Fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container,new HomeFragment())
                .commit();
        BottomNavigationView bottomnav = findViewById(R.id.bottom_navigation);
        bottomnav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.nav_Home:
                            selectedFragment = new HomeFragment();break;

                        case R.id.nav_user:
                            selectedFragment = new ProfileFragment();break;

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
            };
}
