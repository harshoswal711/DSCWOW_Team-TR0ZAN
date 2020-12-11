package com.example.buddycop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartUpActivity extends AppCompatActivity {

    private Button citizenlog;
    private Button policelog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        citizenlog=findViewById(R.id.citizen_log_in);
        policelog=findViewById(R.id.police_log_in);

        citizenlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent citizenLogin=new Intent(getBaseContext(),LogInActivity.class);
                startActivity(citizenLogin);
            }
        });

        policelog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent policeLogin=new Intent(getBaseContext(),PoliceHomePageActivity.class);
                startActivity(policeLogin);
            }
        });
    }
}
