package com.example.demorxgo;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Patient extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mLoginBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore, fStore2;
    ArrayList<String> patientsArrayList = new ArrayList<String> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.login);
        fStore = FirebaseFirestore.getInstance ();

        //login functions button
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //build patient list in array
                            fStore.collection ( "patients" ).get().addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> () {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful ()) {
                                        for (QueryDocumentSnapshot document : task.getResult ()) {
                                            patientsArrayList.add ( new String ( document.getId ().toString () ) );
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
                                }
                            } );
                        } else {
                            Toast.makeText(Patient.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });

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