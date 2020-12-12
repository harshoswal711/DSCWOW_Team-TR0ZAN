package com.example.buddycop.police;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.buddycop.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PoliceHomeScreen extends AppCompatActivity {
    public DatabaseReference reference, referenceMain;
    FirebaseAuth fAuth;
    String uid;
    String role;
    String sectorName;
    String latitude = "-";
    String longitude = "-", isInside = "false";
    TextView mRole, mSectorName;
    LinearLayout linearLayout;
    RelativeLayout relativeLayout;
    FusedLocationProviderClient fusedLocationProviderClient;
    LatLng latLng;
    double circle_x = 18.529492, circle_y = 73.881504, radius = 0.01, lat, lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_home_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home Screen Police");
        setSupportActionBar(toolbar);

        fAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("duty");
        uid = fAuth.getCurrentUser().getUid();

        mRole = findViewById(R.id.roleName);
        mSectorName = findViewById(R.id.sectorName);
        linearLayout = findViewById(R.id.linearLayout1);
        relativeLayout = findViewById(R.id.relativeLayout1);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

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
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(PoliceHomeScreen.this);
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

    public void onSelfAttendance(View view) {
        if(ContextCompat.checkSelfPermission(PoliceHomeScreen.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PoliceHomeScreen.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
        else {
            getCurrentLoacation();
        }

    }


    @SuppressLint("MissingPermission")
    private void getCurrentLoacation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(PoliceHomeScreen.this, Locale.getDefault());

                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                                location.getLongitude(), 1);
                        latLng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());

                        latitude = String.valueOf(addresses.get(0).getLatitude());
                        longitude = String.valueOf(addresses.get(0).getLongitude());

                        lat = Double.parseDouble(latitude);
                        lon = Double.parseDouble(longitude);
                        isInside(circle_x, circle_y, radius, lat, lon);
                        Toast.makeText(PoliceHomeScreen.this, ""+latitude+""+longitude, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                }
            }
        });
    }

    public void isInside(double circle_x, double circle_y, double radius, double x, double y){
        if((x - circle_x) * (x - circle_x) + (y- circle_y) * (y - circle_y) <= radius * radius){
            isInside = "true";
            Toast.makeText(this, "is inside "+isInside, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "is inside"+isInside, Toast.LENGTH_SHORT).show();
        }
    }

    public void onCreateFir(View view) {
        startActivity(new Intent(PoliceHomeScreen.this, PoliceCreateFir.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), PoliceLogin.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}