package com.tds.gihbookmarks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tds.gihbookmarks.model.LabCoat;
import com.tds.gihbookmarks.util.UserApi;

import java.util.Date;

public class PostLabCoatActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int GALLERY_CODE =1 ;
    private EditText sizeText;
    private EditText priceText;
    private ImageView imgView;
    private Button addCoatButton;

    private String currentUserId;

    private Uri coatimgUri;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference collectionReference=db.collection("LabCoat");



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_lab_coat);

        sizeText=findViewById(R.id.size_coat);
        priceText=findViewById(R.id.price_coat);
        imgView=findViewById(R.id.coat_img);
        addCoatButton=findViewById(R.id.add_coat);

        firebaseAuth=FirebaseAuth.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();

        addCoatButton.setOnClickListener(this);
        imgView.setOnClickListener(this);

        if(UserApi.getInstance()!=null){
            currentUserId=UserApi.getInstance().getUserId();

        }
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user=firebaseAuth.getCurrentUser();
            }
        };





    }
    @Override
    protected void onStart() {
        super.onStart();
        user=firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firebaseAuth!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.add_coat:
                addCoat();
                break;

            case R.id.coat_img:
                Intent GalleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
                GalleryIntent.setType("image/*");
                startActivityForResult(GalleryIntent,GALLERY_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_CODE && resultCode==RESULT_OK){
            if(data!=null){
                coatimgUri=data.getData();
                imgView.setImageURI(coatimgUri);
            }
        }
    }

    private void addCoat() {
        final String coatSize=sizeText.getText().toString().trim();
        final String coatPrice=priceText.getText().toString().trim();

        if(!TextUtils.isEmpty(coatPrice)
        && !TextUtils.isEmpty(coatSize)
        && coatimgUri!=null){
            final StorageReference filepath=storageReference
                    .child("coatimages")
                    .child("coat"+ Timestamp.now().getSeconds());
            filepath.putFile(coatimgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                  String imageUrl=uri.toString();
                                  LabCoat labCoat=new LabCoat();
                                  labCoat.setImageURL(imageUrl);
                                  labCoat.setPrice(coatPrice);
                                  labCoat.setSize(coatSize);
                                  labCoat.setUserId(currentUserId);
                                  labCoat.setDateAdded(new Timestamp(new Date()));

                                  collectionReference.add(labCoat)
                                          .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                              @Override
                                              public void onSuccess(DocumentReference documentReference) {
                                                  startActivity(new Intent(PostLabCoatActivity.this,HomepageActivity.class));
                                                  finish();

                                              }
                                          })
                                          .addOnFailureListener(new OnFailureListener() {
                                              @Override
                                              public void onFailure(@NonNull Exception e) {
                                                  Log.d("LabCoatActivity", "onFailure: Failed to add coat");
                                              }
                                          });

                                }
                            });


                        }
                    });

        }
        else{
            Snackbar.make(findViewById(R.id.post_coat_layout),"Empty Fields",Snackbar.LENGTH_LONG)
                    .setAction("Action",null).show();
        }
    }


}
