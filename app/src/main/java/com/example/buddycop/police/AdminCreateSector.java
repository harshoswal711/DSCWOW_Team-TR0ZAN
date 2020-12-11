package com.example.buddycop.police;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buddycop.R;
import com.example.buddycop.Uploads.UploadSector;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class AdminCreateSector extends AppCompatActivity {
    String sectorName;
    DatabaseReference reference;
    EditText mSectorName;
    LoadingDialog loadingDialog;
    ListView mListView;
    ArrayList<String> myArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_sector);
        reference = getInstance().getReference("sector");
        loadingDialog = new LoadingDialog(AdminCreateSector.this);

        mSectorName = findViewById(R.id.enterSectorName);
        mListView = findViewById(R.id.listview);

        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(AdminCreateSector.this, android.R.layout.simple_list_item_1, myArrayList);
        mListView.setAdapter(myArrayAdapter);

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(UploadSector.class).toString();
                myArrayList.add(value);
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                myArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

  /*      mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currentSerialNo = parent.getItemAtPosition(position).toString();
                Intent i = new Intent(SearchSerialNoVendor.this, SearchSerialNoVendor2.class);
                i.putExtra("serialNo",currentSerialNo);
                startActivity(i);
                finish();
            }
        });
*/
    }

    public void addSectorBtn(View view) {
        loadingDialog.startLoadingDialog();
        loadingDialog.setText("Creating Sector..");

        sectorName = mSectorName.getText().toString().trim();
        if(sectorName.equals("")){
            loadingDialog.dismissDialog();
            mSectorName.setError("Enter Sector Name First");
        }
        else {
            final UploadSector u = new UploadSector(sectorName, "-");

            reference.child(sectorName).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    loadingDialog.dismissDialog();
                    Toast.makeText(AdminCreateSector.this, "Sector Added Successfully..", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminCreateSector.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}