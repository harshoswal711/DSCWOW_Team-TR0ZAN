package com.example.buddycop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class CitizenCrimeMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    public GoogleMap mMap;
    DatabaseReference reference;

    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();
    ArrayList<String> arrayList2 = new ArrayList<String>();

    LatLng sydney = new LatLng(-34,151);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_map);
        reference = getInstance().getReference("fir");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    Double tempLat, tempLan;
                    String tempPlace;

                    tempLat = Double.valueOf(ds.child("lat").getValue().toString());
                    tempLan = Double.valueOf(ds.child("lan").getValue().toString());

                    tempPlace = ds.child("place").getValue().toString();

                    LatLng name = new LatLng(tempLat, tempLan);
                    arrayList.add(name);
                    arrayList2.add(tempPlace);
                }

                Toast.makeText(CitizenCrimeMapActivity.this, ""+arrayList, Toast.LENGTH_SHORT).show();
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(CitizenCrimeMapActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for(int i=0; i<arrayList.size(); i++){
            mMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title(arrayList2.get(i))
                    .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.thief)));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        }
    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId){
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0,0, vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());

        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
