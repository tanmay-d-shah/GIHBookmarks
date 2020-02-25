package com.tds.gihbookmarks.SellFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tds.gihbookmarks.MainActivity;
import com.tds.gihbookmarks.NavigationMenuFragments.HomeFragment;
import com.tds.gihbookmarks.R;
import com.tds.gihbookmarks.model.Book;
import com.tds.gihbookmarks.model.SaleItems;

import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class BookSellFragment extends Fragment {

    private static final int GALLERY_CODE = 1;

    private EditText bookSellTitle;
    private EditText bookSellAuthor;
    private EditText bookSellPublication;
    private EditText bookSellEdition;
    private EditText bookSellPrice;
    private ImageView bookSellImage;
    private Button bookSellButton;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference collectionReference=db.collection("Books");
    private CollectionReference saleItemCollectionReference=db.collection("SaleItems");
    private CollectionReference userCollectionReference=db.collection("Users");

    String currentUserId;
    String userCity;

    private Uri img1;



    public BookSellFragment(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_sell, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth= FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        currentUserId=user.getUid();
        storageReference= FirebaseStorage.getInstance().getReference();

        bookSellImage=(ImageView)getView().findViewById(R.id.img_book_sell);
        bookSellAuthor=(EditText) getView().findViewById(R.id.etAuthor);
        bookSellButton=(Button)getView().findViewById(R.id.btn_sell);
        bookSellEdition=(EditText)getView().findViewById(R.id.etEdition);
        bookSellPrice=(EditText)getView().findViewById(R.id.etExpectedPrice);
        bookSellPublication=(EditText)getView().findViewById(R.id.etPublication);
        bookSellTitle=(EditText)getView().findViewById(R.id.etTitle);

        bookSellImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GalleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
                GalleryIntent.setType("image/*");
                startActivityForResult(GalleryIntent,GALLERY_CODE);
            }
        });

        bookSellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title=bookSellTitle.getText().toString();
                final String author=bookSellAuthor.getText().toString();
                final String edition=bookSellEdition.getText().toString();
                final String price=bookSellPrice.getText().toString();
                final String publication=bookSellPublication.getText().toString();

                if(!TextUtils.isEmpty(title)
                        && !TextUtils.isEmpty(publication)
                        && !TextUtils.isEmpty(edition)
                        && !TextUtils.isEmpty(author)
                        && !TextUtils.isEmpty(price)
                        && img1!=null)
                {
                    userCollectionReference.whereEqualTo("UserId",user.getUid())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(QueryDocumentSnapshot document:task.getResult()){
                                            userCity= (String) document.get("City");
                                        }
                                    }
                                }
                            });



                    final StorageReference filepath=storageReference
                            .child("book_images")
                            .child("image"+ Timestamp.now().getSeconds());
                    filepath.putFile(img1)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            final String imageUrl=uri.toString();
                                            Book book=new Book();
                                            book.setImageUrl1(imageUrl);
                                            book.setTitle(title);
                                            book.setAuthor(author);
                                            book.setEdition(edition);
                                            book.setExpectedPrice(price);
                                            book.setPublication(publication);
                                            book.setUserId(currentUserId);
                                            book.setDateAdded(new Timestamp(new Date()));

                                            collectionReference.add(book)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {

                                                            SaleItems item=new SaleItems();
                                                            item.setCity(userCity);
                                                            item.setDateAdded(new Timestamp(new Date()));
                                                            item.setDesc(title+" "+author+" "+edition+" "+publication);
                                                            item.setItem("Book");
                                                            item.setPrice(price);
                                                            item.setImageUrl(imageUrl);
                                                            item.setSellerId(user.getUid());
                                                            item.setItemCode(documentReference.getId());
                                                            item.setStatus("Available");

                                                            saleItemCollectionReference.add(item)
                                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                        @Override
                                                                        public void onSuccess(DocumentReference documentReference) {
                                                                             startActivity(new Intent(getActivity(), MainActivity.class));
                                                                            // finish();//*
                                                                        }
                                                                    })
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Log.d("PostBookActivity", "onFailure: Failed to add in saleitems");
                                                                        }
                                                                    });



                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.d("PostBookActivity", "onFailure: "+e.getMessage());
                                                        }
                                                    });


                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("PostBookActivity", "onFailure: "+e.getMessage());
                                }
                            });
                }
                else{
                    Snackbar.make(getView().findViewById(R.id.book_sell_form),"Empty Fields",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                }
            }
        });


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_CODE && resultCode==RESULT_OK){
            if(data!=null){
                img1=data.getData();
                bookSellImage.setImageURI(img1);
            }
        }

    }
}
