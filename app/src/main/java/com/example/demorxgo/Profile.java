package com.example.demorxgo;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.example.demorxgo.databinding.FragmentPatientHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.Executor;

public class Profile extends Fragment {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    FragmentPatientHomeBinding binding;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPatientHomeBinding.inflate ( getLayoutInflater () );

        fAuth = FirebaseAuth.getInstance ();
        fStore = FirebaseFirestore.getInstance ();
        userID = fAuth.getCurrentUser ().getUid ();

        DocumentReference df = fStore.collection ( "patients" ).document ( userID );
        df.get ().addOnCompleteListener ( new OnCompleteListener<DocumentSnapshot> () {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult ();
                if (document.exists ()) {
                    setTextF ( (String) document.get ( "First Name" ) );
                    setTextL ( (String) document.get ( "Last Name" ) );
                }
            }
        } );

        return inflater.inflate ( R.layout.fragment_patient_home, container, false );
    }

    public void setTextF(String text) {
        TextView textView = (TextView) getView ().findViewById ( R.id.fName );
        textView.setText ( text );
    }

    public void setTextL(String text) {
        TextView textView = (TextView) getView ().findViewById ( R.id.lName );
        textView.setText ( text );
    }
}