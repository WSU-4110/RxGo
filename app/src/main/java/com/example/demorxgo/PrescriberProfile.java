package com.example.demorxgo;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PrescriberProfile extends AppCompatActivity {
    //variables
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView ( R.layout.prescriber_profile );

    }//syntax

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ;//inflate layout file(b/c fragments)
        View view =  inflater.inflate(R.layout.prescriber_profile, container, false);
        Log.d(TAG, "View");

        //assign variables to database
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        //finding user in database
        DocumentReference df = fStore.collection("prescriber").document(userID);
        //getting user info from database
        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    //calling functions to set info in text fields
                    setTextF((String) document.get("First Name"));
                    setTextL((String) document.get("Last Name"));
                    setTextDOB((String) document.get("NPI"));
                    setTextNUM( (String) document.get ( "Phone Number" ) );
                    setTextE( (String) document.get ( "Email" ) );

                }
            }
        });

        return inflater.inflate(R.layout.prescriber_profile, container, false);
    }

    //functions to set data in text fields
    public void setTextF(String text) {
        TextView textView1 = (TextView) findViewById(R.id.fName1);
        textView1.setText(text);
    }

    public void setTextL(String text) {
        TextView textView = (TextView) findViewById(R.id.lName);
        textView.setText(text);
    }

    public void setTextDOB(String text) {
        TextView textView = (TextView) findViewById(R.id.NPI);
        textView.setText(text);
    }

    public void setTextNUM(String text) {
        TextView textView = (TextView) findViewById(R.id.Phone);
        textView.setText(text);
    }

    public void setTextE(String text) {
        TextView textView = (TextView) findViewById(R.id.email);
        textView.setText(text);
    }

}
