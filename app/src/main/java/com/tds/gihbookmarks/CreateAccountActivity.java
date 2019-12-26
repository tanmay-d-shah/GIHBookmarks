package com.tds.gihbookmarks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference collectionReference=db.collection("Users");

    private EditText emailEditText;
    private EditText nameText;
    private EditText mobileText;
    private EditText designationText;
    private EditText collegeText;
    private EditText courseText;
    private EditText branchText;
    private EditText semesterText;
    private EditText cityText;
    private EditText passwordText;
    private Button createAccountButton;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        emailEditText=findViewById(R.id.email_text);
        nameText=findViewById(R.id.name_text);
        mobileText=findViewById(R.id.mobileno_text);
        designationText=findViewById(R.id.designation_text);
        collegeText=findViewById(R.id.college_text);
        courseText=findViewById(R.id.course_text);
        branchText=findViewById(R.id.branch_text);
        semesterText=findViewById(R.id.semester_text);
        cityText=findViewById(R.id.city_text);
        passwordText=findViewById(R.id.password_text);
        createAccountButton=findViewById(R.id.create_account_button);
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progress_bar);

        authStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser=firebaseAuth.getCurrentUser();
            }
        };
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(emailEditText.getText().toString())
                &&!TextUtils.isEmpty(nameText.getText().toString())
                &&!TextUtils.isEmpty(mobileText.getText().toString())
                &&!TextUtils.isEmpty(designationText.getText().toString())
                &&!TextUtils.isEmpty(collegeText.getText().toString())
                && !TextUtils.isEmpty(courseText.getText().toString())
                && !TextUtils.isEmpty(branchText.getText().toString())
                && !TextUtils.isEmpty(semesterText.getText().toString())
                && !TextUtils.isEmpty(cityText.getText().toString())
                && !TextUtils.isEmpty(passwordText.getText().toString())){

                    String email=emailEditText.getText().toString().trim();
                    String name=nameText.getText().toString().trim();
                    String mobile=mobileText.getText().toString().trim();
                    String designation=designationText.getText().toString().trim();
                    String college=collegeText.getText().toString().trim();
                    String course=courseText.getText().toString().trim();
                    String branch=branchText.getText().toString().trim();
                    String semester=semesterText.getText().toString().trim();
                    String city=cityText.getText().toString().trim();
                    String password=passwordText.getText().toString().trim();

                    createUserEmailAccount(email,name,mobile,designation,college,course,
                            branch,semester,city,password);

                }
                else{
                    Toast.makeText(CreateAccountActivity.this,"Empty Fields",Toast.LENGTH_LONG).show();
                }
            }
        });







    }

    private void createUserEmailAccount(String email, final String name, final String mobile, final String designation, final String college, final String course, final String branch, final String semester, final String city, String password) {
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            currentUser=firebaseAuth.getCurrentUser();
                            assert currentUser!=null;
                            final String currentUserId=currentUser.getUid();

                            Map<String,String> userObj=new HashMap<>();
                            userObj.put("UserId",currentUserId);
                            userObj.put("Name",name);
                            userObj.put("Mobile",mobile);
                            userObj.put("Designation",designation);
                            userObj.put("College",college);
                            userObj.put("Course",course);
                            userObj.put("Branch",branch);
                            userObj.put("Semester",semester);
                            userObj.put("City",city);

                            collectionReference.add(userObj)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
//                                            currentUser.sendEmailVerification()
//                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<Void> task) {
//                                                            if(task.isSuccessful()){
//                                                                Toast.makeText(CreateAccountActivity.this,"Verification Mail sent to "+currentUser.getEmail(),Toast.LENGTH_LONG).show();
//                                                            }
//                                                            else{
//                                                                Log.d("Verification Failure", "onComplete:Verification Failed ");
//                                                            }
//                                                        }
//                                                    });

                                            Toast.makeText(CreateAccountActivity.this,"Data Saved Succesfully",Toast.LENGTH_LONG).show();
                                        }
                                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Failure", "onFailure:Failed to save Data ");
                                    Toast.makeText(CreateAccountActivity.this,"Failure to store data",Toast.LENGTH_LONG).show();
                                }
                            });


                        }
                    }
                });
    }
}
