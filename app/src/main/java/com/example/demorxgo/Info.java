package com.example.demorxgo;
import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import com.example.demorxgo.databinding.FragmentInfoBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Info extends Fragment {
    EditText UserInput;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    private FragmentInfoBinding binding;

    public Info() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentInfoBinding.inflate(getLayoutInflater());
        fStore=FirebaseFirestore.getInstance ();
        fAuth = FirebaseAuth.getInstance ();
        DocumentReference df = fStore.collection("patients").document( fAuth.getUid () );
        //getting user info from database
        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot> () {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                setButton();
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                }
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

}

