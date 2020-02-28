package com.tds.gihbookmarks.SellFragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.tds.gihbookmarks.model.Book;
import com.tds.gihbookmarks.model.SaleItems;
import com.tds.gihbookmarks.model.Tool;

import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class ToolSellFragment extends Fragment {

    private static final int GALLERY_CODE = 1;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference collectionReference = db.collection("Books");
    private CollectionReference saleItemCollectionReference = db.collection("SaleItems");
    private CollectionReference userCollectionReference = db.collection("Users");

    String currentUserId;
    String userCity;

    private Uri img;

    private Spinner spinner;
    //private View view;

    private ImageView imgTools;
    private TextInputEditText name, desc, price;
    private Button btn_sell,btn_rent;
    private DatePickerDialog.OnDateSetListener mDataSetListner;

    public ToolSellFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        final View view = inflater.inflate(R.layout.fragment_tool_sell, container, false);






        //spinner.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        currentUserId = user.getUid();
        storageReference = FirebaseStorage.getInstance().getReference();
        imgTools = view.findViewById(R.id.imgTool);
        //name = view.findViewById(R.id.tool_name);
        desc = view.findViewById(R.id.tool_desc);
        price = view.findViewById(R.id.tool_price);
        btn_sell = view.findViewById(R.id.btnSellTool);
        spinner=view.findViewById(R.id.toolList);
        btn_rent=view.findViewById(R.id.btnRentTool);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(view.getContext(),R.array.tools,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        btn_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(getActivity(),BookRentDateActivity.class);
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
                Toast.makeText(getContext(),"Your tool is added for rent till date: "+date.toString(),Toast.LENGTH_SHORT).show();
            }
        };

        imgTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                GalleryIntent.setType("image/*");
                startActivityForResult(GalleryIntent, GALLERY_CODE);
            }
        });

        btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String itemName=spinner.getSelectedItem().toString();
                final String itemDesc= String.valueOf(desc.getText());
                final String itemPrice= String.valueOf(price.getText());

                if(!TextUtils.isEmpty(itemName)
                        &&!TextUtils.isEmpty(itemDesc)
                        && !TextUtils.isEmpty(itemPrice)
                        &&img!=null){
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
                            .child("image" + Timestamp.now().getSeconds());
                    filepath.putFile(img)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            final String imageUrl = uri.toString();
                                            Tool tool=new Tool();
                                            tool.setImageURL(imageUrl);
                                            tool.setTitle(itemName);
                                            tool.setPrice(itemPrice);

                                            tool.setUserId(currentUserId);
                                            tool.setDateAdded(new Timestamp(new Date()));


                                            collectionReference.add(tool)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {

                                                            SaleItems item = new SaleItems();
                                                            item.setCity(userCity);
                                                            item.setDateAdded(new Timestamp(new Date()));
                                                            item.setDesc(itemDesc);
                                                            item.setItem("Tool");
                                                            item.setPrice(itemPrice);
                                                            item.setImageUrl(imageUrl);
                                                            item.setSellerId(user.getUid());
                                                            item.setItemCode(documentReference.getId());
                                                            item.setStatus("Available");
                                                            item.setIntention("Intention");

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
                                                            Log.d("PostBookActivity", "onFailure: " + e.getMessage());
                                                        }
                                                    });


                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("PostBookActivity", "onFailure: " + e.getMessage());
                                }
                            });
                }
                else {
                    Snackbar.make(getView().findViewById(R.id.tool_sell_form), "Empty Fields", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                img = data.getData();
                imgTools.setImageURI(img);
            }
        }

    }
    /*@Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getContext(),"clicked",Toast.LENGTH_SHORT).show();
    }*/
}