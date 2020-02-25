package com.tds.gihbookmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class report_bug extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_bug);
    }

    public  void bugs (View view)
    {
        Snackbar.make(findViewById(R.id.login_button),"Hello I'm bug report",Snackbar.LENGTH_LONG).setAction("Action",null).show();
    }
}
