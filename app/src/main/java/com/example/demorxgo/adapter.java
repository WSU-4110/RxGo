package com.example.demorxgo;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.MyViewHolder> {
    private ArrayList<prescription> arrayList = new ArrayList<>();

    public adapter(ArrayList<prescription> arrayList) {
        this.arrayList = arrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView drug, strength, refill;

        public MyViewHolder(View itemView){
            super(itemView);
            drug = itemView.findViewById(R.id.drugName);
            strength = itemView.findViewById(R.id.drugStrength);
            refill = itemView.findViewById(R.id.refillN);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int   viewType) {
        Log.v("CreateViewHolder", "in onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        prescription d = arrayList.get(position);

        holder.drug.setText(d.getDrug());
        holder.strength.setText(d.getStrength());
        holder.refill.setText(d.getRefills());

        holder.drug.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Log.d(TAG,d.getDrug ());
                AlertDialog.Builder builder = new AlertDialog.Builder ( v.getContext () );
                builder.setTitle(d.getDrug ());
                builder.setMessage(d.getId ()+"\n"+d.getDrug ()+ " "+d.getStrength ()+"\n"+"Refills: "+d.getRefills ());

                builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel ();
                    }
                });

                builder.show();
            }
        } );
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
