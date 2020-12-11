package com.example.buddycop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity {

    private EditText lEmail;
    private EditText lPassword;
    private Button logButton;
    private FirebaseAuth mAuth;
    private ProgressDialog progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        lEmail= findViewById(R.id.loginEmail);
        lPassword= findViewById(R.id.loginPassword);
        logButton= findViewById(R.id.login);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = lEmail.getText().toString();
                String password = lPassword.getText().toString();

                if (isValid(email)) {

                    if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                        logInUser(email, password);
                    }
                }else {

                    Toast.makeText(LogInActivity.this, "Enter Correct Email", Toast.LENGTH_SHORT).show();
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

    public void signup(View view) {
        Intent i = new Intent(getBaseContext(),SignUpActivity.class);
        startActivity(i);
        finish();
    }

    public void logInUser(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            String current_user_id=mAuth.getCurrentUser().getUid();
                            Intent mainintent=new Intent(getBaseContext(), CitizenHomePageActivity.class);
                            startActivity(mainintent);
                            finish();


                        } else {
                            progress.hide();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getBaseContext(), "Authentication failed.Please enter correct details",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}

