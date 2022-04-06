package com.example.demorxgo;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter4 extends RecyclerView.Adapter<adapter4.MyViewHolder>{
    private ArrayList<prescription> arrayList = new ArrayList<>();

    public adapter4(ArrayList<prescription> arrayList) {
        this.arrayList = arrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //variables for text fields of drugs etc.
        TextView drug, strength, refill;
        Button addRBtn;
        Context context;

        public MyViewHolder(View itemView){
            super(itemView);

            context = itemView.getContext ();
            drug = itemView.findViewById(R.id.drugNameP);
            strength = itemView.findViewById(R.id.drugStrengthP);
            refill = itemView.findViewById(R.id.refillNP);
            addRBtn = itemView.findViewById ( R.id.refillBtnP );
        }
    }


    @Override
    public adapter4.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int   viewType) {
        Log.v("CreateViewHolder", "in onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pt_drugs_item,parent,false);
        return new adapter4.MyViewHolder (itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull adapter4.MyViewHolder holder, final int position) {
        // variable for current drug
        prescription d = arrayList.get(position);

        //filling in text fields with Rx data
        holder.drug.setText(d.getDrug());
        holder.strength.setText(d.getStrength());
        holder.refill.setText(d.getRefills());

        holder.addRBtn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //add refills
                holder.context.startActivity(new Intent (holder.context,PrescribingPage.class));
            }
        } );

        holder.drug.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder ( v.getContext () );
                builder.setTitle(d.getDrug ());
                builder.setMessage("Rx#: "+d.getId ()+"\n\n"+d.getDrug ()+ " "+d.getStrength ()+"\n"+"Sig:\n"+d.getSig ()+"\nWritten: "+d.getDate ()+"\n\n"+"Refills: "+d.getRefills ()+"\n\n"+"Dr: "+d.getDr ()+"\nPhone: "+d.getdPhone ());
                //buttons on the dialog box
                builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel ();
                    }
                });
                builder.show ();
            }
        } );

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
