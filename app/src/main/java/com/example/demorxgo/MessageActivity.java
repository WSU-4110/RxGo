package com.example.demorxgo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MessageActivity extends AppCompatActivity {

    TextView firstN;

    FirebaseFirestore fStore, fStore2;
    FirebaseAuth fAuth;

    String userid;

    ImageButton btn_send;
    EditText text_send;

    MessageAdapter messageAdapter;
    List<Chat> mchat;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        fStore = FirebaseFirestore.getInstance();
        fStore2 = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        firstN = findViewById(R.id.username2);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);

        Intent intent;


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = text_send.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(fAuth.getUid(), userid, msg);
                } else {
                    Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });
        intent = getIntent();
        userid = intent.getStringExtra("userid");

        DocumentReference reference = fStore.collection("prescriber").document(fAuth.getUid()).collection("Patients").document(userid);
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    firstN.setText(document.get("First Name").toString());
                }

                readMessages(fAuth.getUid(), userid);
            }
        });
    }

        private void sendMessage (String sender,final String receiver, String message){

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("sender", sender);
            hashMap.put("receiver", receiver);
            hashMap.put("message", message);

            reference.child("Chats").push().setValue(hashMap);
        }

        private void readMessages(final String myID, final String userID){
            mchat = new ArrayList<>();

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Chats");
            Query query = ref.orderByKey();

            query.addValueEventListener(new ValueEventListener()
            {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mchat.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Chat chat = snapshot.getValue(Chat.class);

                        assert chat != null;
                        if ( chat.getSender().equals(myID) &&  chat.getReceiver().equals(userID) ||
                                chat.getSender().equals(userID) &&  chat.getReceiver().equals(myID)) {
                            mchat.add(chat) ;
                        }


                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
                });
            }

}
