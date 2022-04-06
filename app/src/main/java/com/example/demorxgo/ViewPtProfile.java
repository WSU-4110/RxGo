package com.example.demorxgo;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewPtProfile extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore,fStore2, fStore3;
    TextView firstN, lastN,birthD;
    String PtID;
    RecyclerView ptRxList;
    adapter4 pha;
    private ArrayList<prescription> ptHis = new ArrayList<prescription>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_view_pt_profile );

        fStore = FirebaseFirestore.getInstance ();
        fStore2 = FirebaseFirestore.getInstance ();
        fStore3 = FirebaseFirestore.getInstance ();
        fAuth = FirebaseAuth.getInstance ();

        firstN=findViewById ( R.id.ptFirst );
        lastN = findViewById ( R.id.ptLast );
        ptRxList = findViewById ( R.id.ptrxL );
        birthD = findViewById ( R.id.ptBirth );

        DocumentReference df = fStore.collection ( "prescriber" ).document (fAuth.getUid ());
        df.get ().addOnCompleteListener ( new OnCompleteListener<DocumentSnapshot> () {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult ();
                if (document.exists ()) {
                    PtID = document.get ( "Viewing" ).toString ();
                    Log.d(TAG,PtID);
                }


                //---------------------------------------------------//
                DocumentReference df2 = fStore2.collection ( "patients" ).document ( PtID );
                df2.get ().addOnCompleteListener ( new OnCompleteListener<DocumentSnapshot> () {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document2 = task.getResult ();
                        if (document.exists ()) {
                            firstN.setText ( document2.get ( "First Name" ).toString () );
                            lastN.setText ( document2.get ( "Last Name" ).toString () );
                            birthD.setText ( document2.get("BirthDay").toString () );
                        }
                        Log.d(TAG,PtID);
                        fStore3.collection ( "patients" ).document ( PtID ).collection ( "Prescriptions" ).get ().addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> () {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful ()) {
                                    for (QueryDocumentSnapshot document : task.getResult ()) {
                                        //creating drug list array
                                        ptHis.add ( new prescription ( document.getId ().toString (), document.get ( "Drug" ).toString (), document.get ( "Drug Strength" ).toString (), document.get ( "Refills" ).toString (), document.get ( "Dr" ).toString (), document.get ( "dPhone" ).toString (), document.get ( "Date" ).toString (), document.get ( "Sig" ).toString () ) );
                                        Log.d ( TAG, ptHis.toString () );
                                    }
                                    pha = new adapter4(ptHis);
                                    Log.d(TAG,"got adapter");

                                    Log.d(TAG,ptHis.toString ());
                                    ptRxList.setLayoutManager(new LinearLayoutManager (getApplicationContext ()));
                                    ptRxList.setItemAnimator(new DefaultItemAnimator ());
                                    ptRxList.addItemDecoration(new DividerItemDecoration (getApplicationContext (),LinearLayoutManager.VERTICAL));
                                    ptRxList.setAdapter ( pha );
                                    Log.d(TAG,"set adapter");
                                }
                            }
                        } );
                    }
                });
                    //---------------------------------------------------//

            }
        });
    }


    public void backToPatientList(View view){
        startActivity(new Intent (getApplicationContext(),PatientList.class));
        finish();
    }
}