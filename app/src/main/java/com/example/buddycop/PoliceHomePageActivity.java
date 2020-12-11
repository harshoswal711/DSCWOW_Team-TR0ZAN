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

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class PoliceHomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView1;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_home_page);

        drawerLayout=findViewById(R.id.drawer1);
        toolbar= findViewById(R.id.toolbar1);
        navigationView1=findViewById(R.id.navigationView1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        navigationView1.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){
            case R.id.file:
                Intent citizenFir=new Intent(getApplicationContext(), CitizenFirActivity.class);
                startActivity(citizenFir);
                break;
            case R.id.addrecords:
                Intent citizenCrimeMap=new Intent(getApplicationContext(), CitizenCrimeMapActivity.class);
                startActivity(citizenCrimeMap);
                break;
            case R.id.criminalrecords:
                Intent citizenIdentity=new Intent(getApplicationContext(), CitizenIdentityActivity.class);
                startActivity(citizenIdentity);
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
