package com.example.buddycop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class FirAdapter extends FirestoreRecyclerAdapter<Fir,FirAdapter.FirHolder> {


    public FirAdapter(@NonNull FirestoreRecyclerOptions<Fir> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FirHolder holder, int position, @NonNull Fir model) {

        holder.firNo.setText(model.getNo());
        holder.firStatus.setText(model.getStatus());
        holder.firDesc.setText(model.getDescription());

    }

    @NonNull
    @Override
    public FirHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.fir_schema,parent,false);
        return new FirHolder(v);
    }

    class FirHolder extends RecyclerView.ViewHolder{


        TextView firNo;
        TextView firStatus;
        TextView firDesc;

        View fview;

        public FirHolder(@NonNull View itemView) {
            super(itemView);

            fview=itemView.findViewById(R.id.scheme_fir);
            firNo=fview.findViewById(R.id.scheme_fir_no);
            firStatus=fview.findViewById(R.id.scheme_status);
            firDesc=fview.findViewById(R.id.scheme_description);

        }
    }
}
