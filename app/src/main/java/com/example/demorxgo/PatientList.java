package com.example.demorxgo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PatientList extends AppCompatActivity {

    SearchView searchMe;
    RecyclerView searchL, savedL;
    adapter2 pa;
    ArrayList<patients> ptSearch =new ArrayList<patients>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_patient_list );

        fillPtList ( ptSearch );
    }

    private void fillPtList(ArrayList<patients>ptSearch)
    {
        FirebaseFirestore fStore = FirebaseFirestore.getInstance ();

        //building array of all patients
        fStore.collection ( "patients" ).get().addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> () {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful ()) {
                    for (QueryDocumentSnapshot document : task.getResult ()) {
                        ptSearch.add ( new patients ( document.get ( "First Name" ).toString (), document.get ( "Last Name" ).toString (), document.get ( "BirthDay" ).toString (), document.getId () ) );
                    }

                }

                //recyclerview setup with Search View filter
                //---------------------------------------------//

                searchL = findViewById ( R.id.recyclerViewSearch );
                searchL.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                pa = new adapter2 ( ptSearch );
                searchL.setItemAnimator(new DefaultItemAnimator ());
                searchL.addItemDecoration(new DividerItemDecoration (PatientList.this,LinearLayoutManager.VERTICAL));
                searchL.setAdapter(pa);

                    //Search View setup
                SearchView searchView = (SearchView) findViewById ( R.id.searchPt );

                searchView.setImeOptions( EditorInfo.IME_ACTION_DONE);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        pa.getFilter().filter(newText);
                        return false;
                    }
                });
                //---------------------------------------------//
            }

        } );
    }

}