package com.example.demorxgo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.demorxgo.databinding.ActivityMessagehomeBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


public class ChatMessage extends AppCompatActivity {

    //CircleImageView profile_image;
    TextView username;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private ActivityMessagehomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMessagehomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        ViewPager viewPager;
        viewPager = binding.viewPager;
        viewPager.setAdapter( viewPagerAdapter );
        TabLayout tabs = binding.tabLayout;
        tabs.setupWithViewPager(viewPager);

    }
}
