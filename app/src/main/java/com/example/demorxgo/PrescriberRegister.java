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


public class PrescriberRegister extends AppCompatActivity {

    //variable declarations
    EditText mFirstName,mLastName, mEmail, mPassword, mPhone, mNPI;
    Button mRegisterBtn,mHomeBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    String userID;
    ProgressBar progressBar;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescriber_register);

        //assigning variables -- views
        mFirstName = findViewById(R.id.firstName3);
        mLastName = findViewById(R.id.lastName3);
        mEmail = findViewById(R.id.rEmail3);
        mPassword = findViewById(R.id.rPassword3);
        mPhone = findViewById(R.id.phone3);
        mRegisterBtn = findViewById(R.id.btnRegister3);
        mLoginBtn = findViewById(R.id.Backlogin3);
        mHomeBtn = findViewById(R.id.goHome3);
        mNPI = findViewById(R.id.npi);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar3);


        mRegisterBtn.setOnClickListener( v -> {

            //getting Strings typed in fields
            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            String fName = mFirstName.getText().toString().trim();
            String lName = mLastName.getText().toString().trim();
            String phoneNum = mPhone.getText().toString().trim();
            String Npi = mNPI.getText().toString().trim();

            //checking inputs
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

                    //log prescriber profile data to fStore
                    userID = fAuth.getCurrentUser().getUid();//getting pt ID that was made
                    DocumentReference df = fStore.collection("prescriber").document(userID);//creating document with a name matching patient ID

                    //building document contents
                    Map<String,Object> user = new HashMap<>();
                    user.put("First Name",fName);
                    user.put("Last Name",lName);
                    user.put("NPI",Npi);
                    user.put("Phone Number",phoneNum);
                    user.put("Email",email);
                    user.put("ID",userID);

                    //set document contents inside the document
                    df.set(user).addOnSuccessListener( unused -> Log.d(TAG,"user profile is created for "+userID) );

                    Toast.makeText(PrescriberRegister.this, "User Created.", Toast.LENGTH_SHORT).show();

                    //back to home after registering
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                } else {
                    //registering fail
                    Toast.makeText(PrescriberRegister.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            } );
        } );


        //back to home or login screen
        mHomeBtn.setOnClickListener( v -> startActivity(new Intent(getApplicationContext(),MainActivity.class)) );
        mLoginBtn.setOnClickListener( v -> startActivity(new Intent(getApplicationContext(),Prescriber.class)) );
    }
}
