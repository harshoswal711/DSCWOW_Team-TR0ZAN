package com.example.buddycop.police;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.buddycop.R;
import com.example.buddycop.Uploads.UploadDuty;
import com.example.buddycop.Uploads.UploadSector;
import com.example.buddycop.Uploads.UploadWanted;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PoliceWantedDB extends AppCompatActivity {
    DatabaseReference reference, reference2;
    RecyclerView recyclerView;
    ArrayList<UploadWanted> arrayListHistory;
    FirebaseRecyclerOptions<UploadWanted> options;
    FirebaseRecyclerAdapter<UploadWanted, FireViewHoldWantedList> adapterHistory;
    EditText searchOfficer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_wanted_d_b);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Wanted Database");
        setSupportActionBar(toolbar);

        reference = FirebaseDatabase.getInstance().getReference("wanted");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(PoliceWantedDB.this));
        recyclerView.setHasFixedSize(true);
        arrayListHistory = new ArrayList<UploadWanted>();


        LoadData("");

    }
    private void LoadData(final String data) {
        Query query1 = reference.orderByChild("sectorName").startAt(data).endAt(data + "\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<UploadWanted>().setQuery(reference, UploadWanted.class).build();
        adapterHistory = new FirebaseRecyclerAdapter<UploadWanted, FireViewHoldWantedList>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FireViewHoldWantedList holder, int position, @NonNull final UploadWanted model) {
                holder.name.setText(model.getName());
                holder.crime.setText(model.getCrime());
                holder.place.setText(model.getPlace());
                Picasso.get().load(model.getImg()).into(holder.img);
            }


            @NonNull
            @Override
            public FireViewHoldWantedList onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                return new FireViewHoldWantedList(LayoutInflater.from(PoliceWantedDB.this).inflate(R.layout.wanted_single_layout, viewGroup, false));
            }
        };
        adapterHistory.startListening();
        recyclerView.setAdapter(adapterHistory);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PoliceWantedDB.this, PoliceHomeScreen.class));
        finish();
    }
}