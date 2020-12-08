package com.example.duan1_nhom6.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.duan1_nhom6.Adapter.PhoneAdapter;
import com.example.duan1_nhom6.Model.Phone;
import com.example.duan1_nhom6.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }
/*
*        phần này của layout fragment_home
*/
    private RecyclerView recyclerView;
    private PhoneAdapter adapter;
    private DatabaseReference myref;
    private ArrayList<Phone> list;
    private ProgressBar progressBar;


    EditText searchET;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_phone);
        searchET = view.findViewById(R.id.edit_search);
        progressBar = view.findViewById(R.id.progressBar2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), 2));
        list = new ArrayList<>();

        Log.d("home", "onCreateView: ");

        FirebaseDatabase.getInstance().getReference("Phone")
        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        Phone phone = dataSnapshot.getValue(Phone.class);
                        list.add(phone);
                    }

                    adapter = new PhoneAdapter(container.getContext(),list);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    filter(s.toString());
            }
        });



        ImageSlider imageSlider = view.findViewById(R.id.image_slider);

        List<SlideModel> slideModelList = new ArrayList<>();
        slideModelList.add(new SlideModel("https://cdn.tgdd.vn/2020/11/banner/iphone-12-800-300-800x300.png"));
        slideModelList.add(new SlideModel("https://cdn.tgdd.vn/2020/11/banner/800-300-800x300-6.png"));
        slideModelList.add(new SlideModel("https://cdn.tgdd.vn/2020/11/banner/A93-800-300-800x300.png"));
        slideModelList.add(new SlideModel("https://cdn.tgdd.vn/2020/11/banner/800-300-800x300-7.png"));
        slideModelList.add(new SlideModel("https://cdn.tgdd.vn/2020/11/banner/800-300-800x300-6.png"));

        imageSlider.setImageList(slideModelList,false);
        return view;
    }

    private void filter(String s) {
         ArrayList<Phone> filterlist = new ArrayList<>();

         for(Phone phone : list){
             if(phone.getPhonename().toLowerCase().contains(s.toLowerCase())){
                 filterlist.add(phone);

             }
         }
         adapter.filterList(filterlist);
    }


}