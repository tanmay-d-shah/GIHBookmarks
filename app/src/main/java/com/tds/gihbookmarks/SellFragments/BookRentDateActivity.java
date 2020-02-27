package com.tds.gihbookmarks.SellFragments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.tds.gihbookmarks.R;

import java.util.Calendar;

public class BookRentDateActivity extends AppCompatActivity {

    private TextView from,to,frm_date,to_date;
    private Button confirm_btn;
    private DatePickerDialog.OnDateSetListener mDataSetListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_rent_date);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(BookRentDateActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDataSetListner,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        mDataSetListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                String date=day + "/" + month + "/" + year;
                Toast.makeText(getApplicationContext(),"Your book is added for rent till date: "+date.toString(),Toast.LENGTH_SHORT).show();
            }
        };
    }
}
