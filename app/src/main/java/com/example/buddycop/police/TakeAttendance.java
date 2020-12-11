package com.example.wowhack.police;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.wowhack.R;
import com.example.wowhack.Uploads.PoliceRegestrationUpload;
import com.example.wowhack.Uploads.UploadAttendance;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class TakeAttendance extends AppCompatActivity {
    DatabaseReference reference, reference2, reference3;
    RecyclerView recyclerView;
    ArrayList<PoliceRegestrationUpload> arrayListHistory;
    FirebaseRecyclerOptions<PoliceRegestrationUpload> options;
    FirebaseRecyclerAdapter<PoliceRegestrationUpload, FireViewHoldOfficerList> adapterHistory;
    EditText searchOfficer;
    String sectorName, sectorHeadUid;
    String tempFirstName, tempLastName, tempDesignation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        reference = FirebaseDatabase.getInstance().getReference("credentials").child("police");
        reference2 = FirebaseDatabase.getInstance().getReference("duty");
        reference3 = FirebaseDatabase.getInstance().getReference("attendance");
        searchOfficer = findViewById(R.id.searchViewOfficer);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(TakeAttendance.this));
        recyclerView.setHasFixedSize(true);
        arrayListHistory = new ArrayList<PoliceRegestrationUpload>();

        sectorName = getIntent().getStringExtra("sectorName");
        sectorHeadUid = getIntent().getStringExtra("sectorHeadUid");

        LoadData(sectorName);
    }

    private void LoadData(final String data) {
        Query query1 = reference2.orderByChild("sectorName").startAt(data).endAt(data + "\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<PoliceRegestrationUpload>().setQuery(query1, PoliceRegestrationUpload.class).build();
        adapterHistory = new FirebaseRecyclerAdapter<PoliceRegestrationUpload, FireViewHoldOfficerList>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FireViewHoldOfficerList holder, int position, @NonNull final PoliceRegestrationUpload model) {

                reference.child(model.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        tempFirstName = snapshot.child("firstName").getValue().toString();
                        tempLastName = snapshot.child("lastName").getValue().toString();
                        tempDesignation = snapshot.child("designation").getValue().toString();

                        if(tempDesignation.equals("Constable")){
                            holder.itemView.setVisibility(View.VISIBLE);
                            holder.Name.setText(tempFirstName+" "+tempLastName);
                            holder.Designation.setText(tempDesignation);

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                                    popupMenu.inflate(R.menu.popup_menu);
                                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                        @Override
                                        public boolean onMenuItemClick(MenuItem item) {
                                            switch (item.getItemId()) {
                                                case R.id.open:
                                                    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                                                    Long tsLong = System.currentTimeMillis()/1000;
                                                    String ts = tsLong.toString();

                                                    final UploadAttendance u = new UploadAttendance(sectorHeadUid, model.getUid(), sectorName, sectorHeadUid,
                                                            date, ts, "-","-");

                                                    reference3.child(date).child(model.getUid()).setValue(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(TakeAttendance.this, "Attendance Marked successfully..", Toast.LENGTH_SHORT).show();
                                                        }

                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(TakeAttendance.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                    return true;
                                                case R.id.delete:

                                                    return true;
                                            }
                                            return true;
                                        }
                                    });
                                    popupMenu.show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


            @NonNull
            @Override
            public FireViewHoldOfficerList onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                return new FireViewHoldOfficerList(LayoutInflater.from(TakeAttendance.this).inflate(R.layout.row_officer, viewGroup, false));
            }
        };
        adapterHistory.startListening();
        recyclerView.setAdapter(adapterHistory);
    }

}