package com.tds.gihbookmarks.SellFragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.tds.gihbookmarks.R;

import java.util.Calendar;

public class ToolSellFragment extends Fragment {

    private Spinner spinner;
    //private View view;

    private ImageView imgTools;
    private TextInputEditText name, desc, price;
    private Button btn_sell,btn_Rent;
    private DatePickerDialog.OnDateSetListener mDataSetListner;

    public ToolSellFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_tool_sell, container, false);
        imgTools = view.findViewById(R.id.imgTool);
        //name = view.findViewById(R.id.tool_name);
        desc = view.findViewById(R.id.tool_desc);
        price = view.findViewById(R.id.tool_price);
        btn_sell = view.findViewById(R.id.btnSellTool);
        spinner=view.findViewById(R.id.toolList);
        btn_Rent=view.findViewById(R.id.btnRentTool);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(view.getContext(),R.array.tools,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

       btn_Rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(getContext().getApplicationContext(),BookRentDateActivity.class);
                startActivity(intent);*/
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(),
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
                Toast.makeText(getContext(),"Your book is added for rent till date: "+date.toString(),Toast.LENGTH_SHORT).show();
            }
        };

        //spinner.setOnItemClickListener(this);
        return view;
    }

    /*@Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getContext(),"clicked",Toast.LENGTH_SHORT).show();
    }*/
}
