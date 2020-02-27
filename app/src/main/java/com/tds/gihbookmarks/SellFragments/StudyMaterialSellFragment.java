package com.tds.gihbookmarks.SellFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
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
import com.tds.gihbookmarks.R;
import com.tds.gihbookmarks.model.SaleItems;
import com.tds.gihbookmarks.model.StudyMaterial;

import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class StudyMaterialSellFragment extends Fragment {

    private ImageView studyMaterialImageSell;
    private TextInputEditText studyMaterialTitle, studyMaterialDesc;
    private Button studyMaterialSubmitButton;

    private static final int GALLERY_CODE = 1;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference collectionReference = db.collection("StudyMaterial");
    private CollectionReference saleItemCollectionReference = db.collection("SaleItems");
    private CollectionReference userCollectionReference = db.collection("Users");

    String currentUserId;
    String userCity;

    private Uri img;


    public StudyMaterialSellFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_study_material_sell, container, false);
//        imgSM = view.findViewById(R.id.imgStudyMaterial);
//        title = view.findViewById(R.id.material_desc);
//        btn_submit = view.findViewById(R.id.btnSubmit);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        currentUserId = user.getUid();
        storageReference = FirebaseStorage.getInstance().getReference();
        studyMaterialTitle=getView().findViewById(R.id.studyMaterial_title);
        studyMaterialImageSell=getView().findViewById(R.id.imgStudyMaterial);
        studyMaterialDesc=getView().findViewById(R.id.studyMaterial_desc);
        studyMaterialSubmitButton=getView().findViewById(R.id.btnSubmit);

        studyMaterialImageSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                GalleryIntent.setType("image/*");
                startActivityForResult(GalleryIntent, GALLERY_CODE);
            }
        });

        studyMaterialSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title=studyMaterialTitle.getText().toString();
                final String desc=studyMaterialDesc.getText().toString();

                if(!TextUtils.isEmpty(title)
                && !TextUtils.isEmpty(desc)
                        && img!=null
                ){
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
                            .child("studyMaterial_images")
                            .child("image" + Timestamp.now().getSeconds());

                    filepath.putFile(img)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            final String imageUrl = uri.toString();
                                            StudyMaterial studyMaterial=new StudyMaterial();
                                            studyMaterial.setTitle(title);
                                            studyMaterial.setDesc(desc);
                                            studyMaterial.setImgUrl(imageUrl);
                                            studyMaterial.setDateAdded(new Timestamp(new Date()));

                                            collectionReference.add(studyMaterial)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {

                                                            SaleItems item = new SaleItems();
                                                            item.setCity(userCity);
                                                            item.setDateAdded(new Timestamp(new Date()));
                                                            item.setDesc(desc);
                                                            item.setItem("Study Material");
                                                            item.setPrice("0");
                                                            item.setImageUrl(imageUrl);
                                                            item.setSellerId(user.getUid());
                                                            item.setItemCode(documentReference.getId());
                                                            item.setStatus("Available");

                                                            saleItemCollectionReference.add(item)
                                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                        @Override
                                                                        public void onSuccess(DocumentReference documentReference) {
                                                                            startActivity(new Intent(getActivity(), MainActivity.class));


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

                                                        }
                                                    });
                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                }
                else {
                    Snackbar.make(getView().findViewById(R.id.book_sell_form), "Empty Fields", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }

            }
        });







    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                img = data.getData();
                studyMaterialImageSell.setImageURI(img);
            }
        }

    }
}
