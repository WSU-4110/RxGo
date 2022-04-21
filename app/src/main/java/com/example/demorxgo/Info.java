package com.example.demorxgo;
import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.QuickContactBadge;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demorxgo.databinding.FragmentInfoBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Objects;



public class Info extends Fragment {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    String uses, side;
    String drug;
    EditText UserInput;
    Activity activity = getActivity();


    FragmentInfoBinding binding;

    public Info() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentInfoBinding.inflate(getLayoutInflater());
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        DocumentReference df = fStore.collection("patients").document(fAuth.getUid());

        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Button();
            }

        });


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }
    public void setButton(){
        Button b = (Button) getView ().findViewById ( R.id.testing );
        b.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"click!");
            }
        } );
    }

    public void Button() {
        Button SearchBtn;
        SearchBtn = (Button) getView().findViewById(R.id.SearchIt);
        UserInput = (EditText) getView().findViewById(R.id.searchD);

        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Click");
                fStore.collection("Drugs").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d(TAG, "Oncomplete Function Started");

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG,"comparing: "+document.get("Drug").toString()+UserInput.getText().toString());

                                if(document.get("Drug").toString().equals(UserInput.getText().toString()))
                                {
                                    Log.d(TAG,document.get("Drug").toString());
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                    builder.setTitle(document.get("Drug").toString());
                                    builder.setMessage("Drug: " + document.get("Drug").toString() + "\n\n" +
                                            "\n" + "Common Uses:\n" + document.get("Common Uses").toString()+
                                            "\n" + "Side Effects: " + document.get("Side effects").toString());
                                    builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            // drug.setText(d.getDrug();
                                        }
                                    });
                                }

                            }
                        }

                    }
                });
            }
        });
    }


}

