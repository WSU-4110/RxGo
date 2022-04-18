package com.example.demorxgo;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;




import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.demorxgo.databinding.ActivityContactdBinding;
import com.example.demorxgo.databinding.ActivityMessageBinding;
import com.example.demorxgo.databinding.ActivityMessagingBinding;
import com.example.demorxgo.databinding.ActivityPatientHomeBinding;
import com.google.android.material.tabs.TabLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.demorxgo.UserAdapter;
import com.example.demorxgo.User;
import com.example.demorxgo.R;

import java.util.ArrayList;


public class ChatMessage extends AppCompatActivity {

    //CircleImageView profile_image;
    TextView username;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private ActivityContactdBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityContactdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        ViewPager viewPager;
        viewPager = binding.viewPager;
        viewPager.setAdapter( viewPagerAdapter );
        TabLayout tabs = binding.tabLayout;
        tabs.setupWithViewPager(viewPager);

    }
}
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactd);

        Toolbar toolbar = findViewById(R.id.toolbar);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        //profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                //if (user.getImageURL().equals("default")){
                //    profile_image.setImageResource(R.mipmap.ic_launcher);
                //} else {
//
                //    //change this
                //    Glide.with(getApplicationContext()).load(user.getImageURL()).into(profile_image);
                //}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //TabLayout tabLayout = findViewById(R.id.tab_layout);
        //ViewPager viewPager = findViewById(R.id.view_pager);


        ////reference = FirebaseDatabase.getInstance().getReference("Chats");
        ////reference.addValueEventListener(new ValueEventListener() {
        //    //@Override
        //   //public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        //        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        //       //ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        //       //int unread = 0;
        //      // for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
        //           //Chat chat = snapshot.getValue(Chat.class);
        //           //if (chat.getReceiver().equals(firebaseUser.getUid()) && !chat.isIsseen()) {
        //           //    unread++;
        //           //}
        //       //}

        //      // if (unread == 0) {
        //           viewPagerAdapter.addFragment(new ChatsFragment(), "Chats");
        //       //} else {
        //           //viewPagerAdapter.addFragment(new ChatsFragment(), "(" + unread + ") Chats");
        //       //}

        //       viewPagerAdapter.addFragment(new UserFragment(), "Users");
        //       //viewPagerAdapter.addFragment(new ProfileFragment(), "Profile");

        //       viewPager.setAdapter(viewPagerAdapter);

        //       tabLayout.setupWithViewPager(viewPager);

        //   }

           //@Override
           //public void onCancelled(@NonNull DatabaseError databaseError) {

            //}
       // });

    }
    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
    //    getMenuInflater().inflate(R.menu.menu, menu);
    //    return true;
    //}

    //@Override
    //public boolean onOptionsItemSelected(MenuItem item) {
    //    switch (item.getItemId()){
//
    //        case  R.id.logout:
    //            FirebaseAuth.getInstance().signOut();
    //            // change this code beacuse your app will crash
    //            startActivity(new Intent(ChatMessage.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    //            return true;
    //    }
//
    //    return false;
    //}

    //class ViewPagerAdapter extends FragmentPagerAdapter {
//
    //    private ArrayList<Fragment> fragments;
    //    private ArrayList<String> titles;
//
    //    ViewPagerAdapter(FragmentManager fm){
    //        super(fm);
    //        this.fragments = new ArrayList<>();
    //        this.titles = new ArrayList<>();
    //    }
//
    //    @Override
    //    public Fragment getItem(int position) {
    //        return fragments.get(position);
    //    }
//
    //    @Override
    //    public int getCount() {
    //        return fragments.size();
    //    }
//
    //    public void addFragment(Fragment fragment, String title){
    //        fragments.add(fragment);
    //        titles.add(title);
    //    }
//
    //    // Ctrl + O
//
    //    @Nullable
    //    @Override
    //    public CharSequence getPageTitle(int position) {
    //        return titles.get(position);
    //    }
    //}

/*
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ChatMessage extends AppCompatActivity {

    private FirebaseListAdapter<Chat> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        //displayChatMessage();



        FloatingActionButton fab =
                (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                //FirebaseDatabase.getInstance()
                //        .getReference()
                //        .push()
                //        .setValue(new Chat(input.getText().toString(),
                //                FirebaseAuth.getInstance()
                //                        .getCurrentUser()
                //                        .getDisplayName())
                //        );

                // Clear the input
                input.setText("");
            }
        });
    }


        //private void displayChatMessage() {
//
        //    ListView listOfMessages = (ListView) findViewById(R.id.list_of_messages);
//
        //    adapter = new FirebaseListAdapter<Chat>(this, Chat.class,
        //            R.layout.message, FirebaseDatabase.getInstance().getReference()) {
        //        @Override
        //        protected void populateView(View v, Chat model, int position) {
        //            // Get references to the views of message.xml
        //            TextView messageText = (TextView) v.findViewById(R.id.message_text);
        //            TextView messageUser = (TextView) v.findViewById(R.id.message_user);
        //            TextView messageTime = (TextView) v.findViewById(R.id.message_time);
//
        //            // Set their text
        //            messageText.setText(model.getMessageText());
        //            messageUser.setText(model.getMessageUser());
//
        //            // Format the date before showing it
        //            messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
        //                    model.getMessageTime()));
        //        }
        //    };
//
        //    listOfMessages.setAdapter(adapter);
//
        //}

    //back button function to go back to home screen
    public void backToPrescriberHome(View view){
        startActivity(new Intent(getApplicationContext(),PrescriberHome.class));
        finish();
    }
    }
    /*
 */
