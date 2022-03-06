package com.example.demorxgo;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.viewpager.widget.ViewPager;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



import com.example.demorxgo.databinding.ActivityPatientHomeBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class PatientHome extends AppCompatActivity{
    TextView firstN,lastN;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    TabLayout tabLayout;





    private ActivityPatientHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        binding = ActivityPatientHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),this);
        ViewPager viewPager;
        viewPager = binding.viewpager;
        viewPager.setAdapter ( sectionsPagerAdapter );
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager ( viewPager );



        firstN = findViewById(R.id.fName);
        lastN = findViewById(R.id.lName);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();





/*AH's code
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);





        //retrieve patient data from fStore
        DocumentReference df = fStore.collection("patients").document(userID);
        df.addSnapshotListener(this, new EventListener<DocumentSnapshot>(){
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e){
                    firstN.setText(documentSnapshot.getString("First Name"));
                    lastN.setText(documentSnapshot.getString("Last Name"));
                }
        });

 */


    }


}