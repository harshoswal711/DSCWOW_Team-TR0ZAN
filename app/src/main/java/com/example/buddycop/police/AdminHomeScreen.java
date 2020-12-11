package com.example.wowhack.police;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wowhack.R;
import com.example.wowhack.UserCurrentAdmin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminHomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_screen);
    }

    public void logout(View view) {
        new UserCurrentAdmin(AdminHomeScreen.this).removeUser();
        startActivity(new Intent(AdminHomeScreen.this, PoliceLogin.class));
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