package com.example.demorxgo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PatientList extends AppCompatActivity {

    SearchView searchMe;
    RecyclerView searchL, savedL;
    adapter2 pa;
    adapter3 spa;
    ArrayList<patients> ptSearch =new ArrayList<patients>();
    ArrayList<patients> ptSaved =new ArrayList<patients>();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance ();
    FirebaseFirestore fStore2 = FirebaseFirestore.getInstance ();
    FirebaseAuth fAuth = FirebaseAuth.getInstance ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_patient_list );
        //decided to do everything inside another function.. no reason i think
        fillPtList ( ptSearch );
    }

    private void fillPtList(ArrayList<patients>ptSearch)
    {
        //building array of all patients
        fStore.collection ( "patients" ).get().addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> () {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful ()) {
                    for (QueryDocumentSnapshot document : task.getResult ()) {
                        ptSearch.add ( new patients ( document.get ( "First Name" ).toString (),
                                document.get ( "Last Name" ).toString (), document.get ( "BirthDay" ).toString (),
                                document.getId () ) );
                    }
                }

                //recyclerview setup with Search View filter
                //---------------------------------------------//

                searchL = findViewById ( R.id.recyclerViewSearch );//assign variable of recycler to patient search recycler
                searchL.setLayoutManager(new LinearLayoutManager(getApplicationContext()));//syntax for vertical scrolling i think
                pa = new adapter2 ( ptSearch );//pass through array of all patients into adapter
                searchL.setItemAnimator(new DefaultItemAnimator ());//syntax for lines between items
                searchL.addItemDecoration(new DividerItemDecoration (PatientList.this,LinearLayoutManager.VERTICAL));//syntax for vertical scrolling i think
                searchL.setAdapter(pa);//set up adapter with recycler

                //Search View setup
                searchMe = (SearchView) findViewById ( R.id.searchPt );//assign variable name

                searchMe.setImeOptions( EditorInfo.IME_ACTION_DONE);//syntax no idea what this one does

                searchMe.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        pa.getFilter().filter(newText);//filtering adapter to show what is in search voew
                        return false;
                    }
                });
                //---------------------------------------------/"
                //setting up second Recycler view with only saved patients
                //---------------------------------------------//

                //---------------------------------------------//
                //building array of saved patient IDs
                fStore2.collection ( "prescriber" ).document (fAuth.getUid ()).collection
                        ( "Patients" ).get().addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> () {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful ())
                        {
                            for(QueryDocumentSnapshot document : task.getResult ()) {
                                ptSaved.add ( new patients ( document.get ( "First Name" ).toString (), document.get ( "Last Name" ).toString (),
                                        document.get ( "BirthDay" ).toString (), document.getId () ) );
                            }
                        }

                        //all the same stuff but passing through array of saved patients
                        savedL=findViewById(R.id.RecyclerSavedList);//assign variable
                        savedL.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        spa = new adapter3(ptSaved);
                        savedL.setItemAnimator(new DefaultItemAnimator ());
                        savedL.addItemDecoration(new DividerItemDecoration (PatientList.this,LinearLayoutManager.VERTICAL));
                        savedL.setAdapter ( spa );
                    }
                });
            }
        });
    }

    //back button function to go back to home screen
    public void backToPrescriberHome(View view){
        startActivity(new Intent(getApplicationContext(),PrescriberHome.class));
        finish();
    }
}