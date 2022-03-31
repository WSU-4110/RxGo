package com.example.demorxgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewPtProfile extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore,fStore2;
    TextView firstN, lastN;
    String PtID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_view_pt_profile );

        fStore = FirebaseFirestore.getInstance ();
        fStore2 = FirebaseFirestore.getInstance ();
        fAuth = FirebaseAuth.getInstance ();

        firstN=findViewById ( R.id.ptFirst );
        lastN = findViewById ( R.id.ptLast );

        DocumentReference df = fStore.collection ( "prescriber" ).document (fAuth.getUid ());
        df.get ().addOnCompleteListener ( new OnCompleteListener<DocumentSnapshot> () {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult ();
                if (document.exists ()) {
                    PtID = document.get ( "Viewing" ).toString ();
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
                        }
                    }
                    //---------------------------------------------------//

                } );
            }
        });
    }


    public void backToPatientList(View view){
        startActivity(new Intent (getApplicationContext(),PatientList.class));
        finish();
    }
}