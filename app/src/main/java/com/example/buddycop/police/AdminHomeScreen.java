package com.example.buddycop.police;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buddycop.R;
import com.google.firebase.auth.FirebaseAuth;


public class AdminHomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_screen);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), PoliceLogin.class));
        finish();
    }

    public void onCreateSector(View view) {
        startActivity(new Intent(AdminHomeScreen.this, AdminCreateSector.class));
    }

    public void onAllocateOfficers(View view) {
        startActivity(new Intent(AdminHomeScreen.this, AdminOfficerAllocate.class));
    }

    public void onAllocateHeadOfficer(View view) {
        startActivity(new Intent(AdminHomeScreen.this, AdminHeadAllocate.class));
    }
}