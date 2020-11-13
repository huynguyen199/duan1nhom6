package com.example.duan1_nhom6.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_nhom6.R;

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }
/*
*        phần này của layout fragment_home
*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_send_message, container, false);
    }
}