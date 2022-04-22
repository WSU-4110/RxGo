package com.example.demorxgo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class Patient extends AppCompatActivity {

    //declare all variables
    EditText mEmail,mPassword;
    Button mLoginBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
  
    ArrayList<String> patientsArrayList = new ArrayList<> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        //assign variables to their views on layout file
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.login);
        fStore = FirebaseFirestore.getInstance ();

        //login functions button
        mLoginBtn.setOnClickListener( v -> {
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is Required.");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is Required.");
                return;
            }

            if (password.length() < 6) {
                mPassword.setError("Password Must be >= 6 Characters");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            // authenticate the user
            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener( task -> {
                if (task.isSuccessful()) {

                    //build patient list with an array
                    fStore.collection ( "patients" ).get().addOnCompleteListener ( task1 -> {
                        if (task1.isSuccessful ()) {
                            for (QueryDocumentSnapshot document : task1.getResult ()) {
                                patientsArrayList.add ( document.getId () );
                            }
                        }
                        //checking if user is a patient
                        for(int i = 0; i<patientsArrayList.size ();i++)
                        {
                            if(fAuth.getUid ().equals ( patientsArrayList.get(i) ))
                            {
                                Toast.makeText(Patient.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Patient.this, PatientHome.class);
                                startActivity(intent);
                            }
                        }
                    } );
                } else {
                    Toast.makeText(Patient.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            } );

        } );

    }

    //functions to open register and back buttons
    public void register(View view){
        startActivity(new Intent(getApplicationContext(),Register.class));
        finish();
    }
    public void backMain(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

}