package com.tds.gihbookmarks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");
    private FirebaseUser currentUser;

    private EditText name;
    private EditText email;
    private EditText password;
    private EditText phone;
    private EditText designation;
    private EditText college;
    private EditText branch;
    private EditText semester;
    private EditText enrollment;
    private EditText city;
    private Button signUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /*signUpButton=findViewById(R.id.signup_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });*/

        firebaseAuth=FirebaseAuth.getInstance();

        name=findViewById(R.id.name);
        password=findViewById(R.id.password);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone_number);
        designation=findViewById(R.id.designation);
        college=findViewById(R.id.college);
        branch=findViewById(R.id.branch);
        semester=findViewById(R.id.semester);
        enrollment=findViewById(R.id.enrollment);
        city=findViewById(R.id.address);
        signUpButton=findViewById(R.id.signup_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=name.getText().toString().trim();
                String userpassword=password.getText().toString().trim();
                String useremail=email.getText().toString().trim();
                String userphone=phone.getText().toString().trim();
                String userdesignation=designation.getText().toString().trim();
                String usercollege=college.getText().toString().trim();
                String userbranch=branch.getText().toString().trim();
                String usersemester=semester.getText().toString().trim();
                String userenrollment=enrollment.getText().toString().trim();
                String usercity=city.getText().toString().trim();


                if(!TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(useremail)
                        && !TextUtils.isEmpty(userphone)
                        && !TextUtils.isEmpty(userpassword)
                        && !TextUtils.isEmpty(userdesignation)
                        && !TextUtils.isEmpty(usercollege)
                        && !TextUtils.isEmpty(userbranch)
                        && !TextUtils.isEmpty(usersemester)
                        && !TextUtils.isEmpty(usercity)
                        && !TextUtils.isEmpty(userenrollment)
                ){
                    createUserAccount(username,userdesignation,useremail,userpassword,userphone,usercollege,usersemester,userbranch,userenrollment,usercity);
                }

                else{
                    Toast.makeText(SignUpActivity.this,"Empty Fields",Toast.LENGTH_LONG).show();
                }

            }
        });






    }

    private void createUserAccount(final String username, final String userdesignation, final String useremail,
                                   String userpassword, final String userphone, final String usercollege,
                                   final String usersemester, final String userbranch, final String userenrollment, final String usercity) {
        firebaseAuth.createUserWithEmailAndPassword(useremail,userpassword)

                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("SignUp", "onComplete: USer Created");
                            currentUser=firebaseAuth.getCurrentUser();
                            assert currentUser!=null;
                            final String currentUserId=currentUser.getUid();

                            Map<String,String> userObj=new HashMap<>();
                            userObj.put("UserId",currentUserId);
                            userObj.put("Name",username);
                            userObj.put("Mobile",userphone);
                            userObj.put("Designation",userdesignation);
                            userObj.put("College",usercollege);
                            userObj.put("Email",useremail);
                            userObj.put("Branch",userbranch);
                            userObj.put("Semester",usersemester);
                            userObj.put("City",usercity);
                            userObj.put("EnrollmentNumber",userenrollment);
                            userObj.put("BuyerRating","0");
                            userObj.put("SellerRating","0");
                            userObj.put("n","0");

                            collectionReference.add(userObj)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            currentUser.sendEmailVerification()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                Log.d("Mail ", "onComplete: Mail Sent");
                                                                Toast.makeText(SignUpActivity.this,"Verification Mail sent to "+currentUser.getEmail(),Toast.LENGTH_LONG).show();

                                                                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                            else{
                                                                Log.d("Verification Failure", "onComplete:Verification Failed ");
                                                            }
                                                        }
                                                    });

                                            Toast.makeText(SignUpActivity.this,"Data Saved Succesfully",Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("Failure", "onFailure:Failed to save Data ");
                                            Toast.makeText(SignUpActivity.this,"Failure to store data",Toast.LENGTH_LONG).show();
                                        }
                                    });


                        }
                        else{
                            Log.d("SignUp", "onComplete: User NOt created");
                        }
                    }
                });
    }
    }

