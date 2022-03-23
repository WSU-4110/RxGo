package com.example.demorxgo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.example.demorxgo.databinding.ActivityPrescriberHomeBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class PrescriberHome extends AppCompatActivity {
    TextView firstN,lastN;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    TabLayout tabLayout;





    private ActivityPrescriberHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityPrescriberHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        firstN = findViewById(R.id.fName);
        lastN = findViewById(R.id.lName);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();








    }


}