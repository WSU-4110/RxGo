package com.example.demorxgo;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demorxgo.databinding.FragmentPrescriptionsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Prescriptions extends Fragment {
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;
    private RecyclerView rv;
    private adapter ma;
    private ArrayList<prescription> ptHis = new ArrayList<prescription>();
    private FragmentPrescriptionsBinding binding;
    public Prescriptions() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=FragmentPrescriptionsBinding.inflate ( getLayoutInflater () );

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        fStore.collection("patients").document(userID).collection("Prescriptions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot> () {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //creating drug list array
                        ptHis.add(new prescription(document.getId().toString(), document.get("Drug").toString(), document.get("Drug Strength").toString()));
                        Log.d(TAG, ptHis.toString());
                    }


                    //recycler view here so that full prescription list is pass to recycler
                    rv=binding.recyclerView;
                    ma=new adapter(ptHis);
                    Log.d(TAG,"setting adapter");
                    rv.setAdapter(ma);
                    Log.d(TAG,"adapter set");
                    LinearLayoutManager llm = new LinearLayoutManager(getContext ());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    rv.setLayoutManager(llm);
                    rv.setItemAnimator(new DefaultItemAnimator ());
                    rv.addItemDecoration(new DividerItemDecoration (getContext (),LinearLayoutManager.VERTICAL));

                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prescriptions, container, false);
    }
}