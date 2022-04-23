package com.example.demorxgo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

//class for prescription objects
public class Prescriptions extends Fragment {
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;
    RecyclerView rv;
    adapter ma;
    private ArrayList<prescription> ptHis = new ArrayList<> ();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        fStore.collection("patients").document(userID).collection("Prescriptions").get().addOnCompleteListener( task -> {
            if(task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    //creating drug list array
                    ptHis.add(new prescription( document.getId(), document.get("Drug").toString(), document.get("Drug Strength").toString(),document.get("Refills").toString (), document.get("Dr").toString (),document.get("dPhone").toString(),document.get("Date").toString (), document.get( "Sig" ).toString()));

                }

                //recycler view here so that full prescription list is passed to recycler
                rv=(RecyclerView)getView ().findViewById(R.id.recyclerView);
                ma=new adapter(ptHis);
                rv.setAdapter ( ma );
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                rv.setItemAnimator(new DefaultItemAnimator());
                rv.addItemDecoration(new DividerItemDecoration(getContext (),LinearLayoutManager.VERTICAL));
            }
        } );
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prescriptions, container, false);
    }
}