package com.example.demorxgo;

import static android.content.ContentValues.TAG;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class PrescriberRegister extends AppCompatActivity {

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

        /*
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }*/


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String fName = mFirstName.getText().toString().trim();
                String lName = mLastName.getText().toString().trim();
                String phoneNum = mPhone.getText().toString().trim();
                String Npi = mNPI.getText().toString().trim();


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

                //log patient log-in info to firebase authentication
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //log prescriber profile data to fStore
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference df = fStore.collection("prescriber").document(userID);//setting user Id from auth to match doc Id in store for new user
                            Map<String,Object> user = new HashMap<>();
                            user.put("First Name",fName);
                            user.put("Last Name",lName);
                            user.put("NPI",Npi);
                            user.put("Phone Number",phoneNum);


                            df.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"user profile is created for "+userID);
                                }
                            });

                            Toast.makeText(PrescriberRegister.this, "User Created.", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } else {
                            Toast.makeText(PrescriberRegister.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


        //back to home or login screen
        mHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Prescriber.class));
            }
        });
    }
}
