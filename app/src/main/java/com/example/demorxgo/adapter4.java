package com.example.demorxgo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adapter4 extends RecyclerView.Adapter<adapter4.MyViewHolder>{
    private ArrayList<prescription> arrayList;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

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


    @NonNull
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
        fStore = FirebaseFirestore.getInstance ();
        fAuth = FirebaseAuth.getInstance ();

        holder.addRBtn.setOnClickListener ( v -> {
            //add refills

            DocumentReference df = fStore.collection ( "prescriber" ).document (fAuth.getUid ());
            Map<String,Object> refillN = new HashMap<> ();
            refillN.put("Refilling",d.getId ());
            df.update (refillN);

            holder.context.startActivity(new Intent (holder.context,PrescribingRefill.class));
        } );

        holder.drug.setOnClickListener ( v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder ( v.getContext () );
            builder.setTitle(d.getDrug ());
            builder.setMessage("Rx#: "+d.getId ()+"\n\n"+d.getDrug ()+ " "+d.getStrength ()+"\n"+"Sig:\n"+d.getSig ()+"\nWritten: "+d.getDate ()+"\n\n"+"Refills: "+d.getRefills ()+"\n\n"+"Dr: "+d.getDr ()+"\nPhone: "+d.getdPhone ());
            //buttons on the dialog box
            builder.setNegativeButton("Close", (dialogInterface, i) -> dialogInterface.cancel () );
            builder.show ();
        } );

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
