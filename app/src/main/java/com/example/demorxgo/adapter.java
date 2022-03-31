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

//Adapter for prescription list on patient interface
public class adapter extends RecyclerView.Adapter<adapter.MyViewHolder> {
    private ArrayList<prescription> arrayList = new ArrayList<>();

    //Constructor takes in an arraylist of prescriptions
    public adapter(ArrayList<prescription> arrayList) {
        this.arrayList = arrayList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        //variables for text fields of drugs etc.
        TextView drug, strength, refill;

        public MyViewHolder(View itemView){
            super(itemView);
            //assigning the variable names to the tex fields
            //--------------------------------VVVVVVVV--these are id names on layout file
            drug = itemView.findViewById(R.id.drugName);
            strength = itemView.findViewById(R.id.drugStrength);
            refill = itemView.findViewById(R.id.refillN);
        }
    }

    //syntax that needs to be here idk..
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int   viewType) {
        Log.v("CreateViewHolder", "in onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override//this will cycle through array and puts them in the recycler view
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        // variable for current drug
        prescription d = arrayList.get(position);

        //filling in text fields with Rx data
        holder.drug.setText(d.getDrug());
        holder.strength.setText(d.getStrength());
        holder.refill.setText(d.getRefills());

        //clicking on drug name listener
        holder.drug.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Log.d(TAG,d.getDrug ());

                //designing a dialog to pop up when clicked
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

                builder.setPositiveButton ( "-1 Refill", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        d.decrementRefill ();
                        notifyDataSetChanged ();
                        //*********** Also update firebase data here (only updating array of prescriptions rn) ***********
                    }
                } );

                //show the dialog box
                builder.show();
            }
        } );
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
