package com.tds.gihbookmarks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.tds.gihbookmarks.util.UserApi;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference collectionReference=db.collection("Users");
    private EditText loginEmail;
    private EditText loginPass;
    private Button loginButton;
    private Button signUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth=FirebaseAuth.getInstance();

        loginEmail=findViewById(R.id.login_email_text);
        loginPass=findViewById(R.id.login_pass_text);
        loginButton=findViewById(R.id.login_button);
        signUpButton=findViewById(R.id.signup_button);

        loginButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);









    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signup_button:
                startActivity(new Intent(SignInActivity.this,CreateAccountActivity.class));
                break;
            case R.id.login_button:
                login(loginEmail.getText().toString().trim(),loginPass.getText().toString().trim());
        }
    }

    private void login(String email, String pass) {
        if(!TextUtils.isEmpty(email)
        && !TextUtils.isEmpty(pass)){
            firebaseAuth.signInWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
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

                                                        startActivity(new Intent(SignInActivity.this,HomepageActivity.class));
                                                    }



                                                }

                                        }
                                    });
                            }
                            else{

                                Snackbar.make(findViewById(R.id.LinearLayout),"Please verify your Email",Snackbar.LENGTH_LONG).setAction("Action",null).show();

                            }

                    }
            });
        }
        else{

        }

    }
}
