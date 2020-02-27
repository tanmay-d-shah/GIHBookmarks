package com.tds.gihbookmarks;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.tds.gihbookmarks.model.SaleItems;
import com.tds.gihbookmarks.model.Tool;
import com.tds.gihbookmarks.util.UserApi;

import java.util.Date;

public class ToolsPostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private static final int GALLERY_CODE = 1;
    Spinner toolSpinner;
    String[] users = {"Drafter", "LabCoat", "Calculator"};
    private EditText priceText;
    private EditText toolDescText;
    private Button posttoolButton;
    private String stationarySelected;
    private ImageView toolImgView;

    private String currentUserId;

    private Uri toolImgUri;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference collectionReference = db.collection("Tools");
    private CollectionReference saleItemCollectionReference = db.collection("SaleItems");
    private CollectionReference userCollectionReference = db.collection("Users");
    private String userCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_post);

        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        toolSpinner = findViewById(R.id.stationary_spinner);

        priceText = findViewById(R.id.item_price);
        toolDescText = findViewById(R.id.item_description);
        posttoolButton = findViewById(R.id.post_item_button);
        toolImgView = findViewById(R.id.item_img);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toolSpinner.setAdapter(adapter);

        toolSpinner.setOnItemSelectedListener(this);
        posttoolButton.setOnClickListener(this);
        toolImgView.setOnClickListener(this);

        if (UserApi.getInstance() != null) {
            currentUserId = UserApi.getInstance().getUserId();

        }
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        user = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuth != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                toolImgUri = data.getData();
                toolImgView.setImageURI(toolImgUri);
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        stationarySelected = users[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        stationarySelected = users[0];

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_item_button:
                postItem();
                break;

            case R.id.item_img:
                Intent GalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                GalleryIntent.setType("image/*");
                startActivityForResult(GalleryIntent, GALLERY_CODE);
        }


    }

    private void postItem() {

        final String stationaryPrice = priceText.getText().toString().trim();
        final String stationaryDesc = toolDescText.getText().toString().trim();

        if (!TextUtils.isEmpty(stationaryPrice)
                && !TextUtils.isEmpty(stationaryDesc)
                && toolImgUri != null) {

            userCollectionReference.whereEqualTo("UserId", user.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    userCity = (String) document.get("City");
                                }
                            }
                        }
                    });
            final StorageReference filepath = storageReference
                    .child("tool_images")
                    .child("tool" + Timestamp.now().getSeconds());
            filepath.putFile(toolImgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final String imageUrl = uri.toString();
                                    Tool stationary = new Tool();
                                    stationary.setImageURL(imageUrl);
                                    stationary.setPrice(stationaryPrice);
                                    stationary.setDesc(stationaryDesc);
                                    stationary.setUserId(currentUserId);
                                    stationary.setDateAdded(new Timestamp(new Date()));

                                    collectionReference.add(stationary)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    /* startActivity(new Intent(ToolsPostActivity.this,HomepageActivity.class));



                                                     */
                                                    SaleItems item = new SaleItems();
                                                    item.setCity(userCity);
                                                    item.setDateAdded(new Timestamp(new Date()));
                                                    item.setDesc(stationaryDesc);
                                                    item.setItem("Tool");
                                                    item.setPrice(stationaryPrice);
                                                    item.setImageUrl(imageUrl);
                                                    item.setSellerId(user.getUid());
                                                    item.setItemCode(documentReference.getId());
                                                    item.setStatus("Available");

                                                    saleItemCollectionReference.add(item)
                                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                @Override
                                                                public void onSuccess(DocumentReference documentReference) {
                                                                    //* startActivity(new Intent(PostBookActivity.this,HomepageActivity.class));
                                                                    finish();//*
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.d("PostToolActivity", "onFailure: Failed to add in saleitems");
                                                                }
                                                            });
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("StatioanryActivity", "onFailure: Failed to add stationary");
                                                }
                                            });

                                }
                            });


                        }
                    });

        } else {
            Snackbar.make(findViewById(R.id.post_stationary_layout), "Empty Fields", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
