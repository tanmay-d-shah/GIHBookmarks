package com.tds.gihbookmarks.SellFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.tds.gihbookmarks.R;

public class BookSellFragment extends Fragment {

    private ImageView imgBook;
    private TextInputEditText name,author,price,edition,publication;
    private Button btnSell;
    public BookSellFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_book_sell, container, false);
        imgBook=view.findViewById(R.id.imgBook);
        name=view.findViewById(R.id.book_name);
        author=view.findViewById(R.id.book_writer);
        price=view.findViewById(R.id.book_price);
        edition=view.findViewById(R.id.book_edition);
        publication=view.findViewById(R.id.book_publication);
        btnSell=view.findViewById(R.id.btnSell);
        return view;
    }

}
