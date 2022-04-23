package com.example.demorxgo;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Adapter for prescription list on patient interface
public class adapter extends RecyclerView.Adapter<adapter.MyViewHolder> {
    private final ArrayList<prescription> arrayList;
    FirebaseFirestore fStore,fStore2,fStore3,fStore4;
    FirebaseAuth fAuth;
    adapter adapters;
    private final ArrayList<doctors> Drs = new ArrayList<> ();



    //Constructor takes in an arraylist of prescriptions
    public adapter(ArrayList<prescription> arrayList) {
        this.arrayList = arrayList;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //variables for text fields of drugs etc.
        TextView drug, strength, refill;
        Button request;

        public MyViewHolder(View itemView){
            super(itemView);
            //assigning the variable names to the tex fields
            //--------------------------------VVVVVVVV--these are id names on layout file
            drug = itemView.findViewById(R.id.drugName);
            strength = itemView.findViewById(R.id.drugStrength);
            refill = itemView.findViewById(R.id.refillN);
            request = itemView.findViewById ( R.id.refillBtn );
        }
    }

    //syntax that needs to be here idk..
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int   viewType) {
        Log.v("CreateViewHolder", "in onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder ( itemView );
    }

    @Override//this will cycle through array and puts them in the recycler view
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        // variable for current drug
        prescription d = arrayList.get(position);

        //filling in text fields with Rx data
        holder.drug.setText(d.getDrug());
        holder.strength.setText(d.getStrength());
        holder.refill.setText(d.getRefills());
        fStore=FirebaseFirestore.getInstance ();
        fAuth = FirebaseAuth.getInstance ();
        adapters=this;

        //clicking on drug name listener
        holder.drug.setOnClickListener ( v -> {

            Log.d(TAG,d.getDrug ());

            //designing a dialog to pop up when clicked
            AlertDialog.Builder builder = new AlertDialog.Builder ( v.getContext () );
            builder.setTitle(d.getDrug ());
            builder.setMessage("Rx#: "+d.getId ()+"\n\n"+d.getDrug ()+ " "+d.getStrength ()+"\n"+"Sig:\n"+d.getSig ()+
                    "\nWritten: "+d.getDate ()+"\n\n"+"Refills: "+d.getRefills ()+"\n\n"+"Dr: "+d.getDr ()+
                    "\nPhone: "+d.getdPhone ());

            //buttons on the dialog box
            builder.setNegativeButton("Close Prescription", (dialogInterface, i) -> {
                fStore.collection ( "patients" ).document ( fAuth.getUid () ).collection ( "Prescriptions" ).document ( d.getId () ).delete ();
                arrayList.remove ( holder.getAdapterPosition () );
                Toast.makeText( v.getContext (), "Prescription removed", Toast.LENGTH_LONG).show();
                adapters.notifyItemRemoved ( holder.getAdapterPosition ());
            } );

            builder.setPositiveButton ( "-1 Refill", (dialog, which) -> {
                d.decrementRefill ();
                notifyDataSetChanged ();
                //*********** Also update firebase data here (only updating array of prescriptions rn) ***********
            } );

            //show the dialog box
            builder.show();
        } );

        holder.request.setOnClickListener ( v -> {
            //building array of all patients
            fStore2=FirebaseFirestore.getInstance ();
            fStore3=FirebaseFirestore.getInstance ();
            fStore4=FirebaseFirestore.getInstance ();
            DocumentReference df2 = fStore4.collection("patients").document( fAuth.getUid ());
            df2.get().addOnCompleteListener( task -> {
                String PF,PL,BD;

                DocumentSnapshot document = task.getResult ();
                if (document.exists ()) {
                    //calling functions to set info in text fields

                    PF = (String)document.get ( "First Name" );
                    PL = (String)document.get ( "Last Name" );
                    BD = (String)document.get ( "BirthDay" );

                    fStore2.collection ( "prescriber" ).get ().addOnCompleteListener ( task1 -> {
                        if (task1.isSuccessful ()) {
                            for (QueryDocumentSnapshot document1 : task1.getResult ()) {
                                Drs.add ( new doctors ( document1.get ( "First Name" ).toString (), document1.get ( "Last Name" ).toString (), document1.get ( "NPI" ).toString (), document1.getId () ) );
                            }
                        }

                        for (int i = 0; i < Drs.size (); i++) {
                            if (Drs.get ( i ).getLastName ().equals ( d.getDr () )) {
                                DocumentReference df = fStore3.collection ( "prescriber" ).document ( Drs.get ( i ).getId () ).collection ( "Refill Requests" ).document ( (int) (Math.random () * 10000000) + "" );
                                Map<String, Object> user = new HashMap<> ();
                                user.put ( "Patient First", PF );
                                user.put ( "Patient Last", PL );
                                user.put ( "Drug", d.getDrug () );
                                user.put ( "Patient BirthDay", BD );
                                df.set(user);
                            }
                        }
                    } );
                }

            } );
        } );
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
