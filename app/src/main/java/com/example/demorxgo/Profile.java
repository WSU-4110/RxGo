package com.example.demorxgo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demorxgo.databinding.FragmentPatientHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends Fragment {

    //variables
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    public FragmentPatientHomeBinding binding;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }//syntax

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPatientHomeBinding.inflate(getLayoutInflater());//inflate layout file(b/c fragments)

        //assign variables to database
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        //finding user in database
        DocumentReference df = fStore.collection("patients").document(userID);
        //getting user info from database
        df.get().addOnCompleteListener( task -> {
            DocumentSnapshot document = task.getResult();
            if (document.exists()) {
                //calling functions to set info in text fields
                setTextF((String) document.get("First Name"));
                setTextL((String) document.get("Last Name"));
                setTextDOB((String) document.get("BirthDay"));
                setTextNUM( (String) document.get ( "Phone Number" ) );
                setTextE( (String) document.get ( "Email" ) );

            }
        } );
        return inflater.inflate(R.layout.fragment_patient_home, container, false);
    }

    //functions to set data in text fields
    public void setTextF(String text) {
        TextView textView1 = (TextView) getView().findViewById(R.id.fName1);
        textView1.setText(text);
    }

    public void setTextL(String text) {
        TextView textView = (TextView) getView().findViewById(R.id.lName);
        textView.setText(text);
    }

    public void setTextDOB(String text) {
        TextView textView = (TextView) getView().findViewById(R.id.DOB);
        textView.setText(text);
    }

    public void setTextNUM(String text) {
        TextView textView = (TextView) getView().findViewById(R.id.Phone);
        textView.setText(text);
    }

    public void setTextE(String text) {
        TextView textView = (TextView) getView().findViewById(R.id.email);
        textView.setText(text);
    }



}