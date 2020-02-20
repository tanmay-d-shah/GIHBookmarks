package com.tds.gihbookmarks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.type.TimeOfDayOrBuilder;
import com.squareup.picasso.Picasso;
import com.tds.gihbookmarks.model.Book;
import com.tds.gihbookmarks.model.RequetedItem;
import com.tds.gihbookmarks.model.SaleItems;

import java.util.Date;

public class SaleItemDetailActivity extends AppCompatActivity {
    private ImageView bookImage;
    private Button requestButton;
    private TextView bookName;
    private TextView bookPrice;
    private TextView bookEdition;
    private TextView bookPublication;
    private TextView bookAuthor;
    private TextView sellerName;
    private TextView sellerMobileNo;
    private TextView statusValue;
    private TextView numRequests;

    private FirebaseAuth firebaseAuth;
    private String currentUserId;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference collectionReference=db.collection("Books");
    private CollectionReference saleItemCollectionReference=db.collection("SaleItems");
    private CollectionReference requestedItemCollectionReference=db.collection("RequestedItems");





    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_item_detail);

        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        currentUserId=user.getUid();
        storageReference= FirebaseStorage.getInstance().getReference();

        bookImage=findViewById(R.id.book_image);

        requestButton=findViewById(R.id.book_request_button);

        bookAuthor=findViewById(R.id.book_writer);
        bookName=findViewById(R.id.book_name);
        bookEdition=findViewById(R.id.book_edition);
        bookPublication=findViewById(R.id.book_publication);
        bookPrice=findViewById(R.id.book_price);
        sellerName=findViewById(R.id.seller_name);
        sellerMobileNo=findViewById(R.id.seller_number);
        statusValue=findViewById(R.id.status_value);
        numRequests=findViewById(R.id.count_request);


        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user=firebaseAuth.getCurrentUser();
            }
        };



        final Book book=(Book) getIntent().getParcelableExtra("book_parcel");
        final SaleItems saleItems =(SaleItems)getIntent().getParcelableExtra("sale_item_parcel");



        String imageUrl;
        imageUrl=book.getImageUrl1();
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .into(bookImage);

        bookName.setText(book.getTitle());
        bookPrice.setText(book.getExpectedPrice());
        bookEdition.setText(book.getEdition());
        bookPublication.setText(book.getPublication());
        bookAuthor.setText(book.getAuthor());
        sellerMobileNo.setText(getIntent().getStringExtra("seller_mobile"));
        sellerName.setText(getIntent().getStringExtra("seller_name"));

        statusValue.setText(saleItems.getStatus());

        //set the value of numRequests Later

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequetedItem requetedItem=new RequetedItem();
                requetedItem.setBuyerId(currentUserId);
                requetedItem.setItem(saleItems.getItem());
                requetedItem.setItemCode(saleItems.getItemCode());
                requetedItem.setSellerId(saleItems.getSellerId());
                requetedItem.setDateRequested(new Timestamp(new Date()));

                requestedItemCollectionReference.add(requetedItem)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(getApplicationContext(),"Item Requested to User",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        Log.d("Book Obj Transfered", "onCreate: "+book.getTitle());
    }
}
