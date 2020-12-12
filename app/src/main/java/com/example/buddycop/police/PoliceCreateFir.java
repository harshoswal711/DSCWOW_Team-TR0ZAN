package com.example.buddycop.police;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.buddycop.R;
import com.example.buddycop.Uploads.UploadFir;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class PoliceCreateFir extends AppCompatActivity {
    EditText mFirNo, mVictimName, mDescription, mPlace, mLat, mLan, mWitness;
    String firNo, victimName, description, place, lat, lan, witness;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_create_fir);
        reference = getInstance().getReference("fir");

        mFirNo = findViewById(R.id.firno);
        mVictimName = findViewById(R.id.victim_name);
        mDescription = findViewById(R.id.offencedesc);
        mPlace = findViewById(R.id.offenceplace);
        mLat = findViewById(R.id.latitude);
        mLan = findViewById(R.id.longitude);
        mWitness = findViewById(R.id.witness);
    }

    public void onFileFir(View view) {
        firNo = mFirNo.getText().toString();
        victimName = mVictimName.getText().toString();
        description = mDescription.getText().toString();
        place = mPlace.getText().toString();
        lat = mLat.getText().toString();
        lan = mLan.getText().toString();
        witness = mWitness.getText().toString();

        final UploadFir u = new UploadFir(firNo, victimName, description, place, lat, lan, witness);

        reference.child(firNo).setValue(u).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(PoliceCreateFir.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });
    }
}