package com.example.demorxgo;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class PageAdapter extends FragmentPagerAdapter {

    int tabCount;

    //retrieve patient data from fStore
    /*
    df.addSnapshotListener(this, new EventListener<DocumentSnapshot>(){
        @Override
        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e){
            firstN.setText(documentSnapshot.getString("First Name"));
            lastN.setText(documentSnapshot.getString("Last Name"));
        }
    });*/

    public PageAdapter(@NonNull FragmentManager fm, int tabCount){
        super(fm);
        this.tabCount=tabCount;
    }

    public PageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Profile profile = new Profile();
                return profile;
            case 1:
                Prescriptions prescriptions = new Prescriptions();
                return prescriptions;
            case 2:
                Info info = new Info();
                return info;
            case 3:
                ContactD contactD = new ContactD();
                return contactD;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
