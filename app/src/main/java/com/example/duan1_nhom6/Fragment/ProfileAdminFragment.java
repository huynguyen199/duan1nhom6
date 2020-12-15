package com.example.duan1_nhom6.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;

import com.example.duan1_nhom6.Adapter.ManagerFeedbackAdapter;
import com.example.duan1_nhom6.ChangePasswordActivity;
import com.example.duan1_nhom6.FeedBackActivity;
import com.example.duan1_nhom6.LoginActivity;
import com.example.duan1_nhom6.ManagerFeedBackActivity;
import com.example.duan1_nhom6.ManagerPhoneActivity;
import com.example.duan1_nhom6.ManagerUserActivity;
import com.example.duan1_nhom6.Model.User;
import com.example.duan1_nhom6.R;
import com.example.duan1_nhom6.StatisticalActivity;
import com.example.duan1_nhom6.TransactionHistoryActivity;
import com.facebook.login.LoginManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileAdminFragment extends Fragment {

    private String Uid;
    private ImageView imageUser;
    private TextView fullname,Email;

    private DatabaseReference firebaseUser;
    NavigationView navigationView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_admin, container, false);
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
                    case R.id.nav_managerPhone:
                        i = new Intent(getContext(), ManagerPhoneActivity.class);

                        break;
                    case R.id.nav_managerFeedback:
                        i = new Intent(getContext(), ManagerFeedBackActivity.class);

                        break;
                    case R.id.nav_managerUser:
                        i = new Intent(getContext(), ManagerUserActivity.class);
                        break;
                    case R.id.nav_statistical:
                        i = new Intent(getContext(), StatisticalActivity.class);

                        break;
                    case R.id.nav_logout:
                        firebaseUser.child(Uid).child("status").setValue("offline");
                        LoginManager.getInstance().logOut();
                        FirebaseAuth.getInstance().signOut();
                        i = new Intent(getContext(),LoginActivity.class);


                        break;
                }
                if(i!=null){
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                        if (user.getImageURL().equals("default")) {
                            Picasso.get().load(R.drawable.ic_user).into(imageUser);
                        }else {
                            Picasso.get().load(user.getImageURL()).into(imageUser);
                        }
                        if(user!=null) {

                            FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                            if (mUser != null) {
                                for (UserInfo profile : mUser.getProviderData()) {
                                    // Id of the provider (ex: google.com)

                                    String providerId = profile.getProviderId();

                                    // UID specific to the provider
                                    String uid = profile.getUid();

                                    // Name, email address, and profile photo Url
                                    String name = profile.getDisplayName();
                                    String email = profile.getEmail();
                                    Email.setText(email);
                                    fullname.setText(name);
                                }
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
