package com.example.demorxgo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.example.demorxgo.databinding.ActivityPrescriberHomeBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class PrescriberHome extends AppCompatActivity {
    Button patientsB, prescribeB, messsagesB;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescriber_home);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        patientsB= findViewById ( R.id.patientBtn );
        prescribeB = findViewById ( R.id.prescribeBtn );
        messsagesB = findViewById ( R.id.messagesBtn );


        patientsB.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PatientList.class));
                finish();

            }
        } );

        prescribeB.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (getApplicationContext(),PrescribingPage.class));
                finish();

            }
        } );

        messsagesB.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Messaging.class));
                finish();
            }
        } );

    }


}