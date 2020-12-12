package com.example.buddycop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.buddycop.police.PoliceLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    DatabaseReference reference;
    EditText mEmail, mPassword;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Citizen Login");
        setSupportActionBar(toolbar);

        fAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("credentials").child("general");

        loadingDialog = new LoadingDialog(LogInActivity.this);

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(LogInActivity.this, CitizenHomePageActivity.class));
            finish();
        }

        mEmail = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);

    }

    public void sendToSelecUserType(View view) {
        new UserCurrent(LogInActivity.this).removeUser();
        startActivity(new Intent(LogInActivity.this, StartUpActivity.class));
        finish();
    }

    public void sendToGeneralRegestration(View view) {
        startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
        finish();
    }

    public void generalLogIn(View view) {
        loadingDialog.startLoadingDialog();
        loadingDialog.setText("Logging in.. Please wait");

        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Email Is Required!");
            loadingDialog.dismissDialog();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Password Is Required!");
            loadingDialog.dismissDialog();
            return;
        }

        //Authenticate the user
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    reference.child(fAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                loadingDialog.dismissDialog();
                                Toast.makeText(LogInActivity.this, "Login Succesful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), CitizenHomePageActivity.class));
                                finish();
                            } else {
                                loadingDialog.dismissDialog();
                                Toast.makeText(LogInActivity.this, "You are police.. you have different login", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    loadingDialog.dismissDialog();
                    Toast.makeText(LogInActivity.this, "Error! Unable To Login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(LogInActivity.this);
        builder.setMessage("Are you sure want to exit from app?");
        builder.setCancelable(false);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        //here exit app alert close............................................
    }
}