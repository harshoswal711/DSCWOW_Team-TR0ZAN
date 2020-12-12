package com.example.buddycop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.buddycop.Uploads.GeneralRegestrationUpload;
import com.example.buddycop.police.PoliceLogin;
import com.example.buddycop.police.PoliceRegestration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    DatabaseReference reference;
    EditText mFirstName, mLastName, mPassword, mConfirmPassword, mMobileNo, mEmail;
    String firstName, lastName, mobileNo,  password, confirmPassword,
            email;
    Button mBtnSignUp;
    LoadingDialog loadingDialog;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        loadingDialog = new LoadingDialog(SignUpActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Citizen Registration");
        setSupportActionBar(toolbar);

        mFirstName = findViewById(R.id.first_name);
        mLastName = findViewById(R.id.last_name);
        mPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.confirmpassword);
        mBtnSignUp = findViewById(R.id.btnSignUp);
        mMobileNo = findViewById(R.id.mobile_no);
        mEmail = findViewById(R.id.email);

        reference = FirebaseDatabase.getInstance().getReference("credentials").child("general");
        fAuth = FirebaseAuth.getInstance();


        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                loadingDialog.setText("Creating Account..");
                firstName = mFirstName.getText().toString();
                lastName = mLastName.getText().toString();
                mobileNo = mMobileNo.getText().toString();
                email = mEmail.getText().toString();
                password = mPassword.getText().toString();
                confirmPassword = mConfirmPassword.getText().toString();

                if (firstName.equals("") || lastName.equals("") || mobileNo.equals("")
                        || password.equals("") || confirmPassword.equals("")
                        || password.length() < 6 || email.equals("")) {
                    if (firstName.equals("")) {
                        mFirstName.setError("First name is required.");
                        loadingDialog.dismissDialog();
                    } else if (lastName.equals("")) {
                        mLastName.setError("Last name is required.");
                        loadingDialog.dismissDialog();
                    } else if (password.equals("")) {
                        mPassword.setError("Password is required.");
                        loadingDialog.dismissDialog();
                    } else if (confirmPassword.equals("")) {
                        mConfirmPassword.setError("Confirmation of password is required.");
                        loadingDialog.dismissDialog();
                    } else if (password.length() < 6) {
                        mPassword.setError("Password minimum length should be 6.");
                        loadingDialog.dismissDialog();
                    } else if (email.equals("")) {
                        mEmail.setError("Enter your email");
                        loadingDialog.dismissDialog();
                    }
                } else {
                    if (confirmPassword.equals(password)) {
                        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    final GeneralRegestrationUpload u = new GeneralRegestrationUpload(firstName, lastName,mobileNo, email,
                                            confirmPassword, fAuth.getCurrentUser().getUid());

                                    reference.child(fAuth.getCurrentUser().getUid()).setValue(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            startActivity(new Intent(SignUpActivity.this, CitizenHomePageActivity.class));
                                            finish();
                                            loadingDialog.dismissDialog();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            loadingDialog.dismissDialog();
                                            Toast.makeText(SignUpActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Error..", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else {
                        loadingDialog.dismissDialog();
                        mConfirmPassword.setError("Password doesn't match.");
                    }
                }

                ////////////////////////////////////////////////////////////////////////////////////////////


            }
        });
    }

    public void sendToGeneralLogin(View view) {
        startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
        finish();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
        finish();
        //here exit app alert close............................................
    }
}