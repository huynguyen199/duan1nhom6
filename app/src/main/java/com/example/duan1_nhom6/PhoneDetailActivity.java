package com.example.duan1_nhom6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.duan1_nhom6.Model.Carts;
import com.example.duan1_nhom6.Model.ConfiGuration;
import com.example.duan1_nhom6.Model.Phone;
import com.example.duan1_nhom6.Model.PhoneDetail;
import com.example.duan1_nhom6.Model.PhonePictures;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PhoneDetailActivity extends AppCompatActivity {

    TextView namePhone , pricePhone , guegranteePhone , introducePhone ;

    TextView screenPhone , opersystemPhone , rearcameraPhone ,
    frontcameraPhone , gpuPhone , intermemoryPhone , ramPhone , simPhone , batteryPhone ;

    Button buttonOder;

    ImageSlider imageSlider;
    List<SlideModel> slideModelList;

    DatabaseReference firebasePhone;
    DatabaseReference firebaseCart;

    //dialogPlus
    ImageView imagecart;
    TextView TextName,TextPrice,TextAmount;
    Button btnPlus,btnMinus;

    Button btnAddCart;

    FirebaseUser firebaseUser;
    private static int TOTAL = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_detail);
        init();

        Toolbar toolbar = findViewById(R.id.toolbar_phone_details);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebasePhone = FirebaseDatabase.getInstance().getReference("Phone");
        firebaseCart = FirebaseDatabase.getInstance().getReference("Cart");
        Intent i = getIntent();
        final String phoneid = i.getStringExtra("phoneid");
        slideModelList = new ArrayList<>();

        buttonOder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TOTAL =0;
                final DialogPlus dialog = DialogPlus.newDialog(v.getContext())
                        .setGravity(Gravity.BOTTOM)
                        .setContentHolder(new ViewHolder(R.layout.dialog_order))
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                final View view = dialog.getHolderView();
                view.setTag(dialog);
                imagecart = view.findViewById(R.id.image_phone);
                TextName = view.findViewById(R.id.edit_idPhone);
                TextPrice = view.findViewById(R.id.pricePhone);
                TextAmount = view.findViewById(R.id.text_amount);
                btnPlus = view.findViewById(R.id.buttonPlus);
                btnMinus = view.findViewById(R.id.btnMinus);
                btnAddCart = view.findViewById(R.id.btnOrder);


                btnPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TOTAL++;
                        TextAmount.setText(String.valueOf(TOTAL));
                    }
                });
                btnMinus.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if(TOTAL>0) {
                            TOTAL--;
                            TextAmount.setText(String.valueOf(TOTAL));

                        }
                    }
                });




                firebasePhone.child(phoneid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Phone phone = snapshot.getValue(Phone.class);
                            if(phone.getImage()!=null){
                                Picasso.get().load(phone.getImage()).into(imagecart);
                                TextName.setText(phone.getPhonename());
                                TextPrice.setText(String.valueOf(phone.getGiatien()));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                firebaseCart.child(phoneid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.exists()){
                                btnAddCart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final String id_user = firebaseUser.getUid();
                                        final int amount = Integer.parseInt(TextAmount.getText().toString());
                                        Carts carts1 = new Carts(amount,phoneid,id_user);
                                        firebaseCart.child(phoneid).setValue(carts1.toMap());

                                    }
                                });
                            }else{
                                btnAddCart.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(PhoneDetailActivity.this, "đã có sản phẩm này", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                dialog.show();

            }
        });


        //set thong tin tu dien thoai
        FirebaseDatabase.getInstance().getReference("Phone")
                .child(phoneid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Phone phone = snapshot.getValue(Phone.class);
                                Log.d("phone", ""+phone.getPhonename());
                                Locale locale = new Locale("vi","VN");
                                DecimalFormat nf = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
                                DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
                                formatSymbols.setCurrencySymbol("");
                                nf.setDecimalFormatSymbols(formatSymbols);
                                namePhone.setText(phone.getPhonename());
                                pricePhone.setText("Giá : "+nf.format(phone.getGiatien())+" vnd");



                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        //set thong tin dien thoai chi tiet
        FirebaseDatabase.getInstance().getReference("PhoneDetail")
                .child(phoneid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    PhoneDetail phoneDetail = snapshot.getValue(PhoneDetail.class);

                    guegranteePhone.setText("Bảo hành : "+phoneDetail.getGuagrantee());
                    introducePhone.setText(phoneDetail.getIntroduce());

                    PhonePictures phonePictures = snapshot.child("image").getValue(PhonePictures.class);
                    slideModelList.add(new SlideModel(phonePictures.getImage1()));
                    slideModelList.add(new SlideModel(phonePictures.getImage2()));
                    slideModelList.add(new SlideModel(phonePictures.getImage3()));

                    imageSlider.setImageList(slideModelList,false);

                    Log.d("image1", ""+phonePictures.getImage1());

                    ConfiGuration configuration = snapshot.child("configuration").getValue(ConfiGuration.class);

                    screenPhone.setText("Màn hình : "+configuration.getScreen());
                    opersystemPhone.setText("Hệ điều hành : "+configuration.getOperatingsystem());
                    rearcameraPhone.setText("Camera sau : "+configuration.getRearcamera());
                    frontcameraPhone.setText("Camera trước : "+configuration.getFrontcamera());
                    gpuPhone.setText("Gpu : "+configuration.getGpu());
                    intermemoryPhone.setText("Bộ nhớ trong : "+configuration.getInternalmemory());
                    ramPhone.setText("Ram : "+configuration.getRam());
                    simPhone.setText("Sim : "+configuration.getSim());
                    batteryPhone.setText("Pin : "+configuration.getBatterycapacity());


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

    public void init(){
        namePhone = findViewById(R.id.text_title);
        pricePhone = findViewById(R.id.text_price);
        guegranteePhone = findViewById(R.id.text_guegrantee);
        introducePhone = findViewById(R.id.text_introduce);

        screenPhone = findViewById(R.id.text_manhinh);
        opersystemPhone = findViewById(R.id.text_hedieuhanh);
        rearcameraPhone = findViewById(R.id.text_camera_sau);
        frontcameraPhone = findViewById(R.id.text_camera_truoc);
        gpuPhone = findViewById(R.id.text_gpu);
        intermemoryPhone = findViewById(R.id.text_bonhotrong);
        ramPhone = findViewById(R.id.text_ram);
        simPhone = findViewById(R.id.text_thesim);
        batteryPhone = findViewById(R.id.text_dungluongpin);

        imageSlider = findViewById(R.id.image_phone);

        buttonOder = findViewById(R.id.add_carts);
    }
}