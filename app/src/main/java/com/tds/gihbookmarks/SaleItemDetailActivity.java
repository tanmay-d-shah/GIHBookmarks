package com.tds.gihbookmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.tds.gihbookmarks.model.Book;

public class SaleItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_item_detail);

        Book book=(Book) getIntent().getParcelableExtra("parcel_data");

        Log.d("Book Obj Transfered", "onCreate: "+book.getTitle());
    }
}
