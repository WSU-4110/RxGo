package com.example.demorxgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Prescriber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescriber);



    }
    public void backMain(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }


}