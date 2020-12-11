package com.example.buddycop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CitizenWantedListActivity extends AppCompatActivity {
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference wantedRef =db.collection("Wanted");

    private WantedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wanted_list);

        setupRecyclerView();
    }
    private void setupRecyclerView(){

        Query query=wantedRef.orderBy("Name");

        FirestoreRecyclerOptions<Wanted> options =new FirestoreRecyclerOptions.Builder<Wanted>().setQuery(query,Wanted.class).build();

        adapter=new WantedAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.wanted_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onStart() {
        super.onStart();

        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();

        adapter.stopListening();
    }

}
