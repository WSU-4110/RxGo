package com.example.demorxgo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class PrescriberHome extends AppCompatActivity {

    //variable declarations

    Button patientsB, prescribeB, messsagesB, refillRequestsB;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescriber_home);

        //assigning variable names to views/database variable names to database
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        patientsB = findViewById(R.id.patientBtn);
        prescribeB = findViewById(R.id.prescribeBtn);
        messsagesB = findViewById(R.id.messagesBtn);


        refillRequestsB = findViewById(R.id.rRequestBtn);


        //button listeners and starting new pages
        patientsB.setOnClickListener( v -> {
            startActivity(new Intent(getApplicationContext(), PatientList.class));
            finish();

        } );

        prescribeB.setOnClickListener( v -> {
            startActivity(new Intent(getApplicationContext(), PrescribingPage.class));
            finish();

        } );

        messsagesB.setOnClickListener( v -> {
            startActivity(new Intent(getApplicationContext(), ChatMessage.class));
            finish();
        } );

        refillRequestsB.setOnClickListener( v -> startActivity(new Intent(getApplicationContext(), refillRequestList.class)) );


    }


}