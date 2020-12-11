package com.example.wowhack.police;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wowhack.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PoliceHomeScreen extends AppCompatActivity {
    public DatabaseReference reference, referenceMain;
    FirebaseAuth fAuth;
    String uid, role, sectorName;
    TextView mRole, mSectorName;
    LinearLayout linearLayout;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_home_screen);

        fAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("duty");
        uid = fAuth.getCurrentUser().getUid();

        mRole = findViewById(R.id.roleName);
        mSectorName = findViewById(R.id.sectorName);
        linearLayout = findViewById(R.id.linearLayout1);
        relativeLayout = findViewById(R.id.relativeLayout1);

        reference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    role = snapshot.child("role").getValue().toString();
                    sectorName = snapshot.child("sectorName").getValue().toString();

                    mRole.setText(role);
                    mSectorName.setText(sectorName);

                    linearLayout.setVisibility(View.VISIBLE);
                }
                else {
                    relativeLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), PoliceLogin.class));
        finish();
    }

    public void onTakeAttendance(View view) {
        if(role.equals("Sector Head")){
            Intent intent = new Intent(PoliceHomeScreen.this, TakeAttendance.class);
            intent.putExtra("sectorName", sectorName);
            intent.putExtra("sectorHeadUid", uid);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, "You don't have authority to take others officers attendance..", Toast.LENGTH_SHORT).show();
        }
    }
}