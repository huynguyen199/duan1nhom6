package com.example.duan1_nhom6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.duan1_nhom6.Adapter.ManagerPhoneAdapter;
import com.example.duan1_nhom6.Dao.DaoPhone;
import com.example.duan1_nhom6.Model.Phone;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;

public class ManagerPhoneActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databasePhone;
    ArrayList<Phone> list;
    ManagerPhoneAdapter adapter;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_phone);

        Toolbar toolbar = findViewById(R.id.toolbar_transaction_history);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });


        recyclerView = findViewById(R.id.recyclerview_manager_phone);
        floatingActionButton = findViewById(R.id.floating);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        databasePhone = FirebaseDatabase.getInstance().getReference("Phone");
        list = new ArrayList<>();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialog = DialogPlus.newDialog(ManagerPhoneActivity.this)
                        .setGravity(Gravity.CENTER)
                        .setContentHolder(new ViewHolder(R.layout.dialog_insert_manager_phone))
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                final View view = dialog.getHolderView();
                final EditText idphoneET = view.findViewById(R.id.edit_idPhone);
                final EditText priceET = view.findViewById(R.id.edit_price);
                final EditText imageET = view.findViewById(R.id.edit_image);
                final EditText namePhoneET = view.findViewById(R.id.edit_phoneName);
                final EditText producerET = view.findViewById(R.id.edit_producer);
                Button addBtn = view.findViewById(R.id.updatePhone);

                idphoneET.setText("phone100");
                priceET.setText("20000");
                imageET.setText("ko co");
                namePhoneET.setText("iphone 123");
                producerET.setText("iphone");
                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String id = idphoneET.getText().toString();
                            int price = Integer.parseInt(priceET.getText().toString());
                            String image = imageET.getText().toString();
                            String namephone = namePhoneET.getText().toString();
                            String producer = producerET.getText().toString();
                            Phone phone = new Phone(price, image, namephone, producer,id);
                            DaoPhone daoPhone = new DaoPhone(view, ManagerPhoneActivity.this);
                            daoPhone.insert(phone);
                        }catch (Exception e){
                            Toast.makeText(ManagerPhoneActivity.this, "Sai giá tiền", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });

                dialog.show();
            }
        });

        databasePhone.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        Phone phone = dataSnapshot.getValue(Phone.class);
                        list.add(phone);
                    }

                    adapter = new ManagerPhoneAdapter(ManagerPhoneActivity.this,list);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}