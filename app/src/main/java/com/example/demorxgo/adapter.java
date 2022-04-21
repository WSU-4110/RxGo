package com.example.demorxgo;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Adapter for prescription list on patient interface
public class adapter extends RecyclerView.Adapter<adapter.MyViewHolder> {
    private ArrayList<prescription> arrayList = new ArrayList<>();
    FirebaseFirestore fStore,fStore2,fStore3,fStore4;
    FirebaseAuth fAuth;
    adapter adapters;
    private ArrayList<doctors> Drs = new ArrayList<doctors>();



    //Constructor takes in an arraylist of prescriptions
    public adapter(ArrayList<prescription> arrayList) {
        this.arrayList = arrayList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
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
        fStore=FirebaseFirestore.getInstance ();
        fAuth = FirebaseAuth.getInstance ();
        adapters=this;

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
                builder.setNegativeButton("Close Prescription", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        fStore.collection ( "patients" ).document ( fAuth.getUid () ).collection ( "Prescriptions" ).document ( d.getId () ).delete ();
                        arrayList.remove ( holder.getAdapterPosition () );
                        Toast.makeText( v.getContext (), "Prescription removed", Toast.LENGTH_LONG).show();
                        adapters.notifyItemRemoved ( holder.getAdapterPosition ());
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

        holder.request.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //building array of all patients
                fStore2=FirebaseFirestore.getInstance ();
                fStore3=FirebaseFirestore.getInstance ();
                fStore4=FirebaseFirestore.getInstance ();
                DocumentReference df2 = fStore4.collection("patients").document( fAuth.getUid ());
                df2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        String PF,PL,BD;

                        DocumentSnapshot document = task.getResult ();
                        if (document.exists ()) {
                            //calling functions to set info in text fields

                            PF = (String)document.get ( "First Name" );
                            PL = (String)document.get ( "Last Name" );
                            BD = (String)document.get ( "BirthDay" );

                            fStore2.collection ( "prescriber" ).get ().addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> () {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful ()) {
                                        for (QueryDocumentSnapshot document : task.getResult ()) {
                                            Drs.add ( new doctors ( document.get ( "First Name" ).toString (), document.get ( "Last Name" ).toString (), document.get ( "NPI" ).toString (), document.getId () ) );
                                        }
                                    }

                                    for (int i = 0; i < Drs.size (); i++) {
                                        if (Drs.get ( i ).getLastName ().toString ().equals ( d.getDr () )) {
                                            DocumentReference df = fStore3.collection ( "prescriber" ).document ( Drs.get ( i ).getId () ).collection ( "Refill Requests" ).document ( (int) (Math.random () * 10000000) + "" );
                                            Map<String, Object> user = new HashMap<> ();
                                            user.put ( "Patient First", PF );
                                            user.put ( "Patient Last", PL );
                                            user.put ( "Drug", d.getDrug () );
                                            user.put ( "Patient BirthDay", BD );
                                            df.set(user);
                                        }
                                    }
                                }
                            } );
                        }

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
