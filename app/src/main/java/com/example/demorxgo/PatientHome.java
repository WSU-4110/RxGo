package com.example.demorxgo;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.demorxgo.databinding.ActivityPatientHomeBinding;
import com.google.android.material.tabs.TabLayout;

public class PatientHome extends AppCompatActivity{

    private ActivityPatientHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

        binding = ActivityPatientHomeBinding.inflate ( getLayoutInflater () );
        setContentView ( binding.getRoot () );

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter ( getSupportFragmentManager (), this );
        ViewPager viewPager;
        viewPager = binding.viewpager;
        viewPager.setAdapter ( sectionsPagerAdapter );
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager ( viewPager );
    }

    public void a(){
        Log.d(TAG,"a");
    }

}