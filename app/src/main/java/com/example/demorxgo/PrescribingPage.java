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

public class PrescribingPage extends AppCompatActivity {
    //variables
    EditText firstNm , lastNm, birthday, date, drug, mg, quan, refills, dir;
    String DName, DPhone;
    Button send;
    FirebaseFirestore fStore,fStore2,fStore3;
    FirebaseAuth fAuth;
    ArrayList<patients> patientsArrayList = new ArrayList<patients> ();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_prescribing_page );

        //assigning variables to views/database
        firstNm = findViewById ( R.id.fNameP );
        lastNm = findViewById ( R.id.lNameP );
        birthday = findViewById ( R.id.BirthdayP );
        date = findViewById ( R.id.editTextDate );
        drug= findViewById ( R.id.DrugP );
        mg = findViewById ( R.id.StrengthP );
        quan = findViewById ( R.id.QuantityP );
        refills=findViewById ( R.id.RefillsP );
        dir=findViewById ( R.id.SigP );
        send=findViewById ( R.id.sendScriptBtn );
        fStore=FirebaseFirestore.getInstance ();
        fStore2=FirebaseFirestore.getInstance ();
        fStore3=FirebaseFirestore.getInstance ();

        //setting default date to today
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date curdate = new Date();
        date.setText ( formatter.format(curdate) );

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
                                            Log.d ( TAG, "found patient.. adding rx" );

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

                            } else {
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
    public void backPMain(View view){
        startActivity(new Intent(getApplicationContext(),PrescriberHome.class));
        finish();
    }

}