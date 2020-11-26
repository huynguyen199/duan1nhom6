package com.example.duan1_nhom6.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1_nhom6.ChangePasswordActivity;
import com.example.duan1_nhom6.FeedBackActivity;
import com.example.duan1_nhom6.LoginActivity;
import com.example.duan1_nhom6.MainActivity;
import com.example.duan1_nhom6.Model.User;
import com.example.duan1_nhom6.PhoneDetailActivity;
import com.example.duan1_nhom6.R;
import com.example.duan1_nhom6.TransactionHistoryActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private String Uid;
    private ImageView imageUser;
    private TextView fullname,Email;

    private DatabaseReference firebaseUser;

    NavigationView navigationView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        navigationView = view.findViewById(R.id.navigation_options);


        Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseUser = FirebaseDatabase.getInstance().getReference("Users");

        NavigationView navigationView = view.findViewById(R.id.navigation_options);
        View header = navigationView.getHeaderView(0) ;
        imageUser = header.findViewById(R.id.image_user);
        fullname = header.findViewById(R.id.text_fullname);

        Email = header.findViewById(R.id.text_Email);

        getProfile();

        clickItemNavigation();
        return view;
    }
    public void clickItemNavigation(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent i = null;
                switch (menuItem.getItemId()){
                        case R.id.nav_transaction_history:
                            i = new Intent(getContext(), TransactionHistoryActivity.class);
                            break;
                        case R.id.nav_feedback:
                            i = new Intent(getContext(), FeedBackActivity.class);

                            break;
                        case R.id.nav_change_password:
                            i = new Intent(getContext(), ChangePasswordActivity.class);
                            break;
                        case R.id.nav_logout:
                            firebaseUser.child(Uid).child("status").setValue("offline");
                            FirebaseAuth.getInstance().signOut();
                            i = new Intent(getContext(),LoginActivity.class);
                            break;
                    }
                    if(i!=null){
                        getContext().startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    }

                return true;
            }
        });
    }


    public void getProfile(){
        firebaseUser.child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                        User user = snapshot.getValue(User.class);
                    Log.d("user", ""+user.getFullname());
                    if (user.getImageURL().equals("default")) {
                        Picasso.get().load(R.drawable.ic_user).into(imageUser);
                    }else {
                        Picasso.get().load(user.getImageURL()).into(imageUser);
                    }
                    if(user!=null){
                        try {
                            fullname.setText(user.getFullname());

                            Email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        }catch (Exception e){

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}