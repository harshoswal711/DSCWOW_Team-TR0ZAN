package com.example.buddycop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class CitizenHomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    ImageButton sosBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_home_page);

        drawerLayout=findViewById(R.id.drawer);
        toolbar= findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.navigationView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        navigationView.setNavigationItemSelectedListener(this);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){
            case R.id.fir:
                Intent citizenFir=new Intent(getApplicationContext(), CitizenFirActivity.class);
                startActivity(citizenFir);
                break;
            case R.id.noc:
                Intent Noc =new Intent(getApplicationContext(), NOCActivity.class);
                startActivity(Noc);
                break;
            case R.id.crimeMap:
                Intent citizenCrimeMap=new Intent(getApplicationContext(), CitizenCrimeMapActivity.class);
                startActivity(citizenCrimeMap);
                break;
            case R.id.Cid:
                Intent citizenIdentity=new Intent(getApplicationContext(), CitizenIdentityActivity.class);
                startActivity(citizenIdentity);
                break;
            case R.id.wanted:
                Intent citizenWanted=new Intent(getApplicationContext(), CitizenWantedListActivity.class);
                startActivity(citizenWanted);
                break;
            case R.id.emergency:
                Intent citizenEmergency=new Intent(getApplicationContext(), CitizenEmergencyActivity.class);
                startActivity(citizenEmergency);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent Logout=new Intent(getApplicationContext(), StartUpActivity.class);
                startActivity(Logout);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

}
