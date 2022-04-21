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
    EditText UserInput;
    ArrayList<Drug> drugSearch = new ArrayList<Drug>();
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
                                drugSearch.add(new Drug (document.get("Common Uses").toString(),
                                     document.get("Side effects").toString(), document.get("Drug").toString()
                                ));
                                Log.d(TAG, "Task was Successful");

                            }
                        }

                        for (Drug d : drugSearch){
                            Log.d(TAG, "Drug Search Starting");
                            //Log.d(TAG, d.getDrug());

                            if((d.getDrug().equals(UserInput.getText().toString())))
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setTitle(d.getDrug());
                                builder.setMessage("Drug: " + d.getDrug() + "\n\n" +
                                        "\n" + "Common Uses:\n" + d.getCommonUses() +
                                        "\n" + "Side Effects: " + d.getSideEffects());
                                builder.show();
                            }
                        }
                        /*
                        for (int i = 0; i < drugSearch.size(); i++) {
                                Log.d(TAG,drugSearch.get(i).getDrug()+"1");
                                Log.d(TAG,UserInput.getText().toString()+"2");

                                if (drugSearch.get(i).getDrug().equals(UserInput.getText().toString())) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                    builder.setTitle(drugSearch.get(i).getDrug());
                                    builder.setMessage("Drug: " + drugSearch.get(i).getDrug() + "\n\n" +
                                            "\n" + "Common Uses:\n" + drugSearch.get(i).getCommonUses() +
                                            "\nSide Effects: " + drugSearch.get(i).getSideEffects());
                                    builder.show();
                                }
                                else {
                                    Log.d(TAG, "Strings not equal to each other");

                                }
                            }*/
                    }
                });
            }
        });
    }
}

