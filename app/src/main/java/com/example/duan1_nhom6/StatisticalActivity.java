package com.example.duan1_nhom6;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.duan1_nhom6.Model.TrasHistory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StatisticalActivity extends AppCompatActivity {
    private static final String TAG = "StatisticalActivity";

    private static int TOTAL_TODAY = 0;
    private static int TOTAL_MONTH = 0;
    private static int TOTAL_YEAR = 0;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss:");

    DatabaseReference databaseReference;
    Date fromdate,todate;
    Date fromyear,toyear;

    TextView txtToday,txtMonth,txtYear;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical2);

        Toolbar toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });

        txtToday = findViewById(R.id.text_today);
        txtMonth = findViewById(R.id.text_month);
        txtYear = findViewById(R.id.text_year);


        databaseReference = FirebaseDatabase.getInstance().getReference("TrasHistory");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TOTAL_TODAY = 0;
                TOTAL_MONTH = 0;
                TOTAL_YEAR = 0;
                if(snapshot.exists()){
                    Log.d(TAG, "onDataChange: ");
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        TrasHistory trasHistory = dataSnapshot.getValue(TrasHistory.class);
                        if (BuildConfig.DEBUG) Log.d(TAG, "trasHistory:" + trasHistory);

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DATE,1);
                        fromdate = calendar.getTime();
                        calendar.set(Calendar.DATE,1);
                        calendar.add(Calendar.MONTH,1);
                        calendar.add(Calendar.DATE,-1);
                        todate = calendar.getTime();

                        Calendar calendarYear = Calendar.getInstance();
                        calendarYear.set(Calendar.MONTH,12);
                        calendarYear.set(Calendar.DATE,1);
                        calendarYear.add(Calendar.DATE,-1);

                        toyear = calendarYear.getTime();
                        calendarYear.set(Calendar.DATE,1);
                        calendarYear.set(Calendar.MONTH,0);
                        fromyear = calendarYear.getTime();

                        Log.d(TAG, "year from "+ sdf.format(fromyear));
                        Log.d(TAG, "year to "+ sdf.format(toyear));

                        Log.d(TAG, "month from " +sdf.format(fromdate));
                        Log.d(TAG, "month to " +sdf.format(todate));

                        try {
                            Date today = sdf.parse(trasHistory.getDate());
                            Calendar calendarToday = Calendar.getInstance();

                            if(today.getTime() >= fromdate.getTime() && today.getTime() <= todate.getTime()){
                                TOTAL_MONTH += trasHistory.getTotal();
                                if (BuildConfig.DEBUG) Log.d(TAG, "TOTAL_MONTH:" + TOTAL_MONTH);
                            }

                            String mToday = sdf.format(calendarToday.getTime());
                            if(mToday.equals(trasHistory.getDate())){
                                TOTAL_TODAY += trasHistory.getTotal();
                                if (BuildConfig.DEBUG) Log.d(TAG, "TOTAL_TODAY:" + TOTAL_TODAY);
                            }

                            if(today.getTime() >= fromyear.getTime() && today.getTime() <= toyear.getTime()){
                                TOTAL_YEAR += trasHistory.getTotal();
                                if (BuildConfig.DEBUG) Log.d(TAG, "TOTAL_YEAR:" + TOTAL_YEAR);
                            }



                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    DecimalFormat nf = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.FRANCE);
                    DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
                    formatSymbols.setCurrencySymbol("vnd");
                    nf.setDecimalFormatSymbols(formatSymbols);
                    txtToday.setText("Hôm nay : "+nf.format(TOTAL_TODAY));
                    txtMonth.setText("Tháng này : "+nf.format(TOTAL_MONTH));
                    txtYear.setText("Năm này : "+nf.format(TOTAL_YEAR));
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