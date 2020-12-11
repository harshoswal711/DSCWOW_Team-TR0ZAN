package com.example.buddycop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        /*Intent mainintent=new Intent(this, CitizenHomePageActivity.class);
        startActivity(mainintent);
        finish();*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser==null){
            sendToStart();
        }else{
            Intent mainintent=new Intent(getBaseContext(), CitizenHomePageActivity.class);
            startActivity(mainintent);
            finish();

        }
    }

    public void sendToStart(){
        Intent logIn = new Intent(getBaseContext(), StartUpActivity.class);
        startActivity(logIn);
        finish();
    }

}
