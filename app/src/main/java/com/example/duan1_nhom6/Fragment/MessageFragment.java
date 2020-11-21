package com.example.duan1_nhom6.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.denzcoskun.imageslider.adapters.ViewPagerAdapter;
import com.example.duan1_nhom6.Model.User;
import com.example.duan1_nhom6.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {


    DatabaseReference databaseUser;
    FirebaseUser firebaseUser;


    TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.ic_icon_message_4,
            R.drawable.ic_baseline_supervised_user_circle_24,
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("chat", "onCreateView: ");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        View view = inflater.inflate(R.layout.fragment_message, container, false);
         tabLayout = view.findViewById(R.id.tablayout_message);
        final ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setCurrentItem(2);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        viewPagerAdapter.addfragment(new ChatsFragment(),"Nhắn tin");
        viewPagerAdapter.addfragment(new UsersFragment(),"Danh sách");


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        setupTabIcons();
        Log.d("chat", "toi day2: ");

        return view;
    }


//    private void CheckStatus(String status){
//        myref = FirebaseDatabase.getInstance().getReference("Users")
//                .child(firebaseUser.getUid());
//
//        HashMap<String , Object> hashMap = new HashMap<>();
//        hashMap.put("status",status);
//
//        myref.updateChildren(hashMap);
//
//    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        CheckStatus("online");
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        CheckStatus("Offline");
//    }


    @Override
    public void onResume() {
        super.onResume();

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }


    //class ViewPagerAdapter
    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Log.d("pos", ""+position);
            return fragments.get(position);
        }


        @Override
        public int getCount() {
            return fragments.size();
        }
        public void addfragment(Fragment fragment,String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }



}