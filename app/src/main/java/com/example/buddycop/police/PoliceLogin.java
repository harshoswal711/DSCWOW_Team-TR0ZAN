package com.example.wowhack.police;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wowhack.R;
import com.example.wowhack.SelectUserType;
import com.example.wowhack.UserCurrent;
import com.example.wowhack.UserCurrentAdmin;
import com.example.wowhack.general.GeneralLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PoliceLogin extends AppCompatActivity {
    FirebaseAuth fAuth;
    DatabaseReference reference;
    EditText mEmail, mPassword;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_login);
        fAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("credentials").child("police");

        loadingDialog = new LoadingDialog(PoliceLogin.this);
        checkUser();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(PoliceLogin.this, PoliceHomeScreen.class));
            finish();
        }

        mEmail = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);


    }

    private void checkUser() {
        String type = new UserCurrentAdmin(PoliceLogin.this).getLoginid();
        if(type.equals("admin")){
            startActivity(new Intent(PoliceLogin.this, AdminHomeScreen.class));
            finish();
        }

    }
    public void sendToPoliceRegestration(View view) {
        startActivity(new Intent(PoliceLogin.this, PoliceRegestration.class));
        finish();
    }

    public void sendToSelecUserType(View view) {
        new UserCurrent(PoliceLogin.this).removeUser();
        startActivity(new Intent(PoliceLogin.this, SelectUserType.class));
        finish();
    }

    public void policeLogIn(View view) {
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

        if(email.equals("admin") && password.equals("admin@123"))
        {
            loadingDialog.dismissDialog();
            new UserCurrentAdmin(PoliceLogin.this).setLoginid("admin");
            Toast.makeText(PoliceLogin.this, "Login Succesful!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), AdminHomeScreen.class));
            finish();
        }
        else {

            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        reference.child(fAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    loadingDialog.dismissDialog();
                                    Toast.makeText(PoliceLogin.this, "Login Succesful!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), PoliceHomeScreen.class));
                                    finish();
                                }
                                else {
                                    loadingDialog.dismissDialog();
                                    Toast.makeText(PoliceLogin.this, "You are not police..", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        loadingDialog.dismissDialog();
                        Toast.makeText(PoliceLogin.this, "Error! Unable To Login", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}