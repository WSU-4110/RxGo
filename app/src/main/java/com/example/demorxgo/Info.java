package com.example.demorxgo;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


public class Info extends Fragment {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    EditText UserInput;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        DocumentReference df = fStore.collection("patients").document(fAuth.getUid());

        df.get().addOnCompleteListener( task -> Button() );
        return inflater.inflate(R.layout.fragment_info, container, false);
    }


    public void Button() {
        Button SearchBtn;
        SearchBtn = (Button) getView().findViewById(R.id.SearchIt);
        UserInput = (EditText) getView().findViewById(R.id.searchD);

        SearchBtn.setOnClickListener( v -> {
            Log.d(TAG, "Click");
            fStore.collection("Drugs").get().addOnCompleteListener( task -> {
                Log.d(TAG, "Oncomplete Function Started");

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG,"comparing: "+document.get("Drug").toString()+UserInput.getText().toString());

                        if(document.get("Drug").toString().equals(UserInput.getText().toString()))
                        {
                            Log.d(TAG,document.get("Drug").toString());
                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                            builder.setTitle(document.get("Drug").toString());
                            builder.setMessage("Drug: " + document.get("Drug").toString() + "\n" +
                                    "\n" + "Common Uses:\n" + document.get("Common Uses").toString()+
                                    "\n" + "Side Effects: " + document.get("Side effects").toString());
                            builder.setNegativeButton("Close", (dialogInterface, i) -> dialogInterface.cancel() );
                            builder.show();
                        }
                    }
                }
            } );
        } );
    }
}

