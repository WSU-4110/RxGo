package com.example.demorxgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    //display 1st page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //To patient log in
    public void pt(View view){
        startActivity(new Intent(getApplicationContext(),Patient.class));
        finish();
    }

    //To prescriber login
    public void dr(View view){
        startActivity(new Intent(getApplicationContext(),Prescriber.class));
        finish();
    }
}