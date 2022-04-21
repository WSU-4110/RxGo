package com.example.demorxgo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class adapter5 extends RecyclerView.Adapter<adapter5.MyViewHolder> {
    ArrayList<refillrequest> a = new ArrayList<> ();

    public adapter5(ArrayList<refillrequest> a) {
        this.a = a;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //variables for text fields of drugs etc.
        TextView drug, first, last;
        Context context;


        public MyViewHolder(View itemView) {
            super ( itemView );

            context = itemView.getContext ();
            drug = itemView.findViewById ( R.id.DrugReq );
            first = itemView.findViewById ( R.id.PFirst );
            last = itemView.findViewById ( R.id.PLast );

        }
    }
    @Override
    public adapter5.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int   viewType) {
        Log.v("CreateViewHolder", "in onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item,parent,false);
        return new adapter5.MyViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter5.MyViewHolder holder, final int position) {
        // variable for current drug
        refillrequest d = a.get ( position );

        //filling in text fields with Rx data
        holder.drug.setText ( d.getDrug () );
        holder.first.setText ( d.getPtNamef () );
        holder.last.setText ( d.getPtNamel () );
    }
    @Override
    public int getItemCount() {
        return a.size();
    }
}