package com.example.buddycop;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFirFragment extends Fragment {


    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference firRef =db.collection("Fir");

    private FirAdapter adapter;

    private View mView;

    public MyFirFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.fragment_my_fir, container, false);

        setupRecyclerView();

        return mView;
    }

    private void setupRecyclerView(){

        Query query=firRef.orderBy("Status");

        FirestoreRecyclerOptions<Fir> options =new FirestoreRecyclerOptions.Builder<Fir>().setQuery(query,Fir.class).build();

        adapter=new FirAdapter(options);

        RecyclerView recyclerView = mView.findViewById(R.id.my_fir_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
