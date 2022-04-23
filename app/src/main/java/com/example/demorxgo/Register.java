package com.example.demorxgo;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {

    //variables
    EditText mFirstName,mLastName, mEmail, mPassword, mPhone, mBirthday;
    Button mRegisterBtn,mHomeBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    String userID;
    ProgressBar progressBar;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //set variables to views/database
        mFirstName = findViewById(R.id.firstName);
        mLastName = findViewById(R.id.lastName);
        mEmail = findViewById(R.id.rEmail);
        mPassword = findViewById(R.id.rPassword);
        mPhone = findViewById(R.id.phone);
        mRegisterBtn = findViewById(R.id.btnRegister);
        mLoginBtn = findViewById(R.id.Backlogin);
        mHomeBtn = findViewById(R.id.goHome);
        mBirthday = findViewById(R.id.bday);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        //click listener
        mRegisterBtn.setOnClickListener( v -> {//when clicked

            //getting String info that is entered in text fields
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            String fName = mFirstName.getText().toString().trim();
            String lName = mLastName.getText().toString().trim();
            String phoneNum = mPhone.getText().toString().trim();
            String BirthDay = mBirthday.getText().toString().trim();


            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is required.");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is required.");
                return;
            }
            if (password.length() < 6) {
                mPassword.setError("Password must be greater or equals to 6 characters.");
            }

            progressBar.setVisibility(View.VISIBLE);

            //logging patient log-in info to firebase authentication
            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener( task -> {
                if (task.isSuccessful()) {
                    //log patient profile data to fStore
                    userID = fAuth.getCurrentUser().getUid();//getting ID

                    //creating document with name matching ID
                    DocumentReference df = fStore.collection("patients").document(userID);

                    //build document contents (Patient info)
                    Map<String,Object> user = new HashMap<>();
                    user.put("First Name",fName);
                    user.put("Last Name",lName);
                    user.put("BirthDay",BirthDay);
                    user.put("Phone Number",phoneNum);
                    user.put("Email",email);
                    user.put("ID",userID);

                    //set contents to document
                    df.set(user).addOnSuccessListener( unused -> Log.d(TAG,"user profile is created for "+userID) );

                    Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();

                    //go back to home page after registering
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                } else {
                    Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            } );
        } );


        //back to home or login screen
        mHomeBtn.setOnClickListener( v -> startActivity(new Intent(getApplicationContext(),MainActivity.class)) );
        mLoginBtn.setOnClickListener( v -> startActivity(new Intent(getApplicationContext(),Patient.class)) );
    }
}