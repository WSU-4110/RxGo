package com.example.demorxgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.TextView;
import android.text.format.DateFormat;


import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


//Prescriber login screen
public class Prescriber extends AppCompatActivity {

    //variable declarations
    EditText mEmail,mPassword;
    Button mLoginBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescriber);

        //assign variable names to views
        mEmail = findViewById(R.id.email2);
        mPassword = findViewById(R.id.password2);
        progressBar = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.login2);

        //login button click listener
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getting Strings that have been typed into the email and password fields
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                //checking for errors
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

                // authenticating the user with database
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //loging in
                            Toast.makeText(Prescriber.this, "Successfully Logged In", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Prescriber.this, PrescriberHome.class);
                            startActivity(intent);

                            //displayChatMessage();

                        } else {
                            //failed login
                            Toast.makeText(Prescriber.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });

    }

    //functions to open register and back buttons
    public void prescriber_register(View view){
        startActivity(new Intent(getApplicationContext(),PrescriberRegister.class));
        finish();
    }

    public void backMain(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}