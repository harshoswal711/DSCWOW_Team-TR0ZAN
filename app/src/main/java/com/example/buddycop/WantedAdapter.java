package com.example.buddycop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class WantedAdapter extends FirestoreRecyclerAdapter<Wanted,WantedAdapter.WantedHolder>{

    public WantedAdapter(@NonNull FirestoreRecyclerOptions<Wanted> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull WantedHolder holder, int position, @NonNull Wanted model) {
        holder.name.setText(model.getName());
        holder.crime.setText(model.getCrime());
        holder.city.setText(model.getCity());
    }

    @NonNull
    @Override
    public WantedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.wanted_single_layout,parent,false);
        return new WantedHolder(v);
    }

    class WantedHolder extends RecyclerView.ViewHolder{


        TextView name;
        TextView crime;
        TextView city;

        View fview;

        public WantedHolder(@NonNull View itemView) {
            super(itemView);

            //fview=itemView.findViewById(R.id.wanted_layout);
            name =fview.findViewById(R.id.wanted_layout_name);
            crime =fview.findViewById(R.id.wanted_layout_crime);
            //city=fview.findViewById(R.id.wanted_layout_city);

        }
    }
}
