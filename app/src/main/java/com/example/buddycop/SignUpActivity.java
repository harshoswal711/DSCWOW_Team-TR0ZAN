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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText city;
    private EditText password;
    private EditText repassword;
    private EditText companyName;
    private EditText phone;
    private EditText email;
    private Button submitButton;
    private FirebaseAuth mAuth;
    private Toolbar signUpToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        signUpToolbar= findViewById(R.id.signUp_toolbar);
        setSupportActionBar(signUpToolbar);
        getSupportActionBar().setTitle("SIGN UP");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        signUpToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),LogInActivity.class));
            }
        });


        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        city = findViewById(R.id.city);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        companyName = findViewById(R.id.aadhar);
        phone = findViewById(R.id.phoneNo);
        repassword = findViewById(R.id.repassword);
        submitButton = findViewById(R.id.submit);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sFirstName = firstName.getText().toString();
                String sLastName = lastName.getText().toString();
                String sCity = city.getText().toString();
                String sPassword = password.getText().toString();
                String sPhoneNo = phone.getText().toString();
                String sRePassword = repassword.getText().toString();
                String sCompanyName = companyName.getText().toString();
                String sEmail = email.getText().toString();

                if (isValid(sEmail)) {

                    if (!TextUtils.isEmpty(sEmail) || !TextUtils.isEmpty(sPassword) || !TextUtils.isEmpty(sFirstName) || !TextUtils.isEmpty(sCompanyName) || !TextUtils.isEmpty(sPhoneNo) || !TextUtils.isEmpty(sRePassword) || !TextUtils.isEmpty(sCity)) {

                        if (sRePassword.equals(sPassword)) {
                            register_user(sFirstName, sLastName, sEmail, sPassword, sCity, sCompanyName, sPhoneNo);
                        } else {
                            Toast.makeText(getBaseContext(), "Passwords do not match.Enter again",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getBaseContext(), "Please Enter all the neccessary fields.",
                                Toast.LENGTH_SHORT).show();

                    }

                }else
                {
                    Toast.makeText(SignUpActivity.this, "Enter Correct Email", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)"+"(com|in|org)"+
                "$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public void register_user(final String sFirstName, final String sLastName, String sEmail, String sPassword, final String sCity, final String sCompanyName, final String sPhoneNo){
        mAuth.createUserWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            FirebaseUser current_user=FirebaseAuth.getInstance().getCurrentUser();
                            String uid=current_user.getUid();
                            String sName=sFirstName +" "+sLastName;

                            Map<String,Object> user =new HashMap<>();
                            user.put("Name",sName);
                            user.put("City",sCity);
                            user.put("Company Name",sCompanyName);
                            user.put("Phone no",sPhoneNo);
                            user.put("Image","default");

                            db.collection("users")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Intent mainintent=new Intent(getBaseContext(), CitizenHomePageActivity.class);
                                            startActivity(mainintent);
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });


                        } else {

                            // If sign in fails, display a message to the user.
                            Toast.makeText(getBaseContext(), "Cannot sign up.Please check that all the fields are filled correctly.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }

}
