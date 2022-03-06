package com.example.demorxgo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demorxgo.databinding.ActivityPatientHomeBinding;
import com.example.demorxgo.databinding.FragmentPatientHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile extends Fragment {
    TextView firstN,lastN;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    FragmentPatientHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_patient_home,container,false);



        //fAuth = FirebaseAuth.getInstance();
        //fStore = FirebaseFirestore.getInstance();
        //userID = fAuth.getCurrentUser().getUid();
        //retrieve patient data from fStore
        //DocumentReference df = fStore.collection("patients").document(userID);
        /*
        df.addSnapshotListener(this, new EventListener<DocumentSnapshot>(){
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e){
                firstN.setText(documentSnapshot.getString("First Name"));
                lastN.setText(documentSnapshot.getString("Last Name"));
            }
        });*/
    }
}
