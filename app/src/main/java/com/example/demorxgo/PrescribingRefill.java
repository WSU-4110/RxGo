package com.example.demorxgo;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PrescribingRefill extends AppCompatActivity {

    EditText firstNm , lastNm, birthday, date, drug, mg, quan, refills, dir;
    String DName, DPhone, pt,refillN;
    Button send;
    FirebaseFirestore fStore,fStore2,fStore3,fStore4,fStore5;
    FirebaseAuth fAuth;
    ArrayList<patients> patientsArrayList = new ArrayList<patients> ();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_prescribing_refill );

        firstNm = findViewById ( R.id.fNameR );
        lastNm = findViewById ( R.id.lNameR );
        birthday = findViewById ( R.id.BirthdayR );
        date = findViewById ( R.id.editTextDateR );
        drug= findViewById ( R.id.DrugR );
        mg = findViewById ( R.id.StrengthR );
        quan = findViewById ( R.id.QuantityR );
        refills=findViewById ( R.id.RefillsR );
        dir=findViewById ( R.id.SigR );
        send=findViewById ( R.id.sendRefillBtn );

        fAuth= FirebaseAuth.getInstance ();
        fStore=FirebaseFirestore.getInstance ();
        fStore2=FirebaseFirestore.getInstance ();
        fStore3=FirebaseFirestore.getInstance ();
        fStore4=FirebaseFirestore.getInstance ();
        fStore5=FirebaseFirestore.getInstance ();

        //setting default date to today
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date curdate = new Date();
        date.setText ( formatter.format(curdate) );

        //auto-filling pt info
        DocumentReference df = fStore.collection ( "prescriber" ).document (fAuth.getUid ());
        df.get ().addOnCompleteListener ( new OnCompleteListener<DocumentSnapshot> () {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult ();
                if (document.exists ()) {
                    pt = document.get ( "Viewing" ).toString ();
                    refillN = document.get("Refilling").toString ();
                }

                DocumentReference df2 = fStore5.collection ( "patients" ).document ( pt );
                df2.get ().addOnCompleteListener ( new OnCompleteListener<DocumentSnapshot> () {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document2 = task.getResult ();
                        if (document2.exists ()) {
                            firstNm.setText ( document2.get ( "First Name" ).toString () );
                            lastNm.setText ( document2.get ( "Last Name" ).toString () );
                            birthday.setText ( document2.get ( "BirthDay" ).toString () );
                        }

                        DocumentReference df3 = fStore5.collection ( "patients" ).document ( pt ).collection ( "Prescriptions" ).document (refillN);
                        df3.get().addOnCompleteListener ( new OnCompleteListener<DocumentSnapshot> () {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot document3 = task.getResult ();
                                if (document3.exists ()) {
                                    drug.setText ( document3.get ( "Drug" ).toString () );
                                    mg.setText ( document3.get ( "Drug Strength" ).toString () );
                                    quan.setText ( document3.get ( "Quantity" ).toString () );
                                    dir.setText ( document3.get("Sig").toString () );
                                }
                            }
                        } );
                    }
                });
            }
        });


        //send button listener
        send.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //getting String Rx info that is entered
                String FName = firstNm.getText().toString().trim();
                String LName = lastNm.getText().toString().trim();
                String Bday = birthday.getText().toString().trim();
                String Date = date.getText().toString().trim();
                String Drug = drug.getText().toString().trim();
                String Mg = mg.getText().toString().trim();
                String Quantity = quan.getText().toString().trim();
                String Refills = refills.getText().toString().trim();
                String Sig = dir.getText().toString().trim();

                //building a list of all patients to look through and find our patient
                fStore.collection ( "patients" ).get().addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> () {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                patientsArrayList.add(new patients(document.get("First Name").toString(),document.get("Last Name").toString(),document.get("BirthDay").toString(), document.getId()));
                            }
                        }

                        //looping through array of patients
                        for(int i = 0; i<patientsArrayList.size ();i++) {
                            Log.d ( TAG, patientsArrayList.get ( i ).getLastName () + " " + LName );

                            //if we find the patient*
                            if (patientsArrayList.get ( i ).getLastName ().toString ().equals ( LName )&&patientsArrayList.get ( i ).getFirstName ().toString ().equals ( FName )&&patientsArrayList.get ( i ).getBirthday ().toString ().equals ( Bday )) {

                                int o = i; //variable for patient position in array

                                //getting Dr info to add to Rx info
                                fAuth = FirebaseAuth.getInstance ();
                                DocumentReference df = fStore3.collection ( "prescriber" ).document ( fAuth.getUid () );
                                df.get ().addOnCompleteListener ( new OnCompleteListener<DocumentSnapshot> () {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot document = task.getResult ();
                                        if (document.exists ()) {

                                            //geting String Dr info
                                            DName = document.get ( "Last Name" ).toString ();
                                            DPhone = document.get ( "Phone Number" ).toString ();

                                            //found patient so adding rx

                                            //creating document in patient's prescription collection
                                            DocumentReference df2 = fStore2.collection ( "patients" ).document ( patientsArrayList.get ( o ).getId ().toString () ).collection ( "Prescriptions" ).document ( (int) (Math.random () * 10000000) + "" );//setting user Id from auth to match doc Id in store for new user
                                            //building contents
                                            Map<String, Object> user = new HashMap<> ();
                                            user.put ( "Date", Date );
                                            user.put ( "Dr", DName );
                                            user.put ( "Drug", Drug );
                                            user.put ( "Drug Strength", Mg );
                                            user.put ( "Quantity", Quantity );
                                            user.put ( "Refills", Refills );
                                            user.put ( "Sig", Sig );
                                            user.put ( "dPhone", DPhone );

                                            //setting contents to created document
                                            df2.set ( user ).addOnCompleteListener ( new OnCompleteListener<Void> () {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Log.d ( TAG, "added" );
                                                    startActivity ( new Intent ( getApplicationContext (), PrescriberHome.class ) );
                                                    finish ();
                                                }
                                            } );
                                        }
                                    }
                                } );
                            }
                            else {
                                Log.d ( TAG, "didn't find patient" );
                            }
                        }
                    }
                } );

                //alertdialog box if patient not found
                AlertDialog.Builder builder = new AlertDialog.Builder ( v.getContext () );
                builder.setTitle("Whoops!");
                builder.setMessage("Didnt find patient with that Name/Birthday");
                builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel ();
                    }
                });
                //missing build.show() not sure where i can put it to only show on patient find failures
            }
        } );

    }

    //button function for back home
    public void backToPatientProfile(View view){
        startActivity(new Intent(getApplicationContext(),ViewPtProfile.class));
        finish();
    }
}