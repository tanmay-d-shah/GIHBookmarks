package com.tds.gihbookmarks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tds.gihbookmarks.HomePageFragments.BooksFragment;
import com.tds.gihbookmarks.NavigationMenuFragments.HomeFragment;
import com.tds.gihbookmarks.util.UserApi;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference collectionReference=db.collection("Users");
    private EditText loginEmail;
    private EditText loginPass;
    private Button loginButton;
    private TextView loginSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail=findViewById(R.id.login_emailid);
        loginPass=findViewById(R.id.login_password);
        loginButton=findViewById(R.id.login_button);
        loginSignUp=findViewById(R.id.login_sign_up);

        loginButton.setOnClickListener(this);
        loginSignUp.setOnClickListener(this);

        firebaseAuth  = FirebaseAuth.getInstance();
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_button:
                login(loginEmail.getText().toString().trim(),loginPass.getText().toString().trim());
                break;
            case R.id.login_sign_up:
                Intent intent=new Intent(this,SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void login(String email, String pass) {
        if(!TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(pass)){
            firebaseAuth.signInWithEmailAndPassword(email,pass)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            final FirebaseUser user=firebaseAuth.getCurrentUser();
                            assert user!=null;

                            if(user.isEmailVerified()){
                                final String currentUserId=user.getUid();
                                collectionReference
                                        .whereEqualTo("UserId",currentUserId)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                                assert queryDocumentSnapshots!=null;
                                                if(!queryDocumentSnapshots.isEmpty()){
                                                    for(QueryDocumentSnapshot snapshot:queryDocumentSnapshots){
                                                        UserApi userApi=UserApi.getInstance();
                                                        userApi.setUserId(snapshot.getString("UserId"));
                                                        userApi.setUsername(snapshot.getString("Name"));
                                                        userApi.setUserMobile(snapshot.getString("Mobile"));

                                                        startActivity(new Intent(LoginActivity.this, PostBookActivity.class));
                                                        Log.d("Login", "onEvent: "+userApi.getUserMobile());
                                                    }



                                                }

                                            }
                                        });
                            }
                            else{

                                Snackbar.make(findViewById(R.id.login_layout),"Please verify your Email",Snackbar.LENGTH_LONG).setAction("Action",null).show();

                            }

                        }
                    })



                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(findViewById(R.id.login_layout),"Incorrect Email or password",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        }
                    });


        }
        else{

        }

    }
}
