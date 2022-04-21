package com.example.demorxgo;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demorxgo.databinding.FragmentUsersBinding;
import com.facebook.FacebookSdk;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.database.ValueEventListener;
import com.example.demorxgo.UserAdapter;
import com.example.demorxgo.User;
import com.example.demorxgo.R;


import java.util.ArrayList;
import java.util.List;


public class UserFragment extends Fragment {

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ArrayList<patients> mUsers = new ArrayList<>();
    private FragmentUsersBinding binding;
    EditText search_users;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_users, container, false);

        readUsers();

        return view;
    }

    private void readUsers() {

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        fStore.collection ( "prescriber" ).document (fAuth.getUid ()).collection ( "Patients" ).get().addOnCompleteListener ( new OnCompleteListener<QuerySnapshot> () {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        mUsers.add(new patients(document.get("First Name").toString(), document.get("Last Name").toString(), document.get("BirthDay").toString(), document.getId()));
                    }
                }

                recyclerView = getView().findViewById(R.id.recycler_view);//assign variable

                userAdapter = new UserAdapter(getContext(), mUsers);
                recyclerView.setAdapter(userAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

            }

        });
    }

}