package com.example.buddycop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.buddycop.police.PoliceLogin;

public class StartUpActivity extends AppCompatActivity {
    String type = "";

    private Button citizenlog;
    private Button policelog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        citizenlog=findViewById(R.id.citizen_log_in);
        policelog=findViewById(R.id.police_log_in);
        checkUser();

        citizenlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent citizenLogin=new Intent(getBaseContext(),LogInActivity.class);
                startActivity(citizenLogin);
            }
        });

    }

    private void checkUser() {
        type =  new UserCurrent(StartUpActivity.this).getUsername();
        if(type.equals("police")){
            startActivity(new Intent(StartUpActivity.this, PoliceLogin.class));
            finish();
        }
        else if(type.equals("general")){
            //startActivity(new Intent(StartUpActivity.this, LogInActivity.class));
            //finish();
        }
    }
    public void sendToPoliceLogin(View view) {
        new UserCurrent(StartUpActivity.this).setUsername("police");
        startActivity(new Intent(StartUpActivity.this, PoliceLogin.class));
        finish();
    }
}
