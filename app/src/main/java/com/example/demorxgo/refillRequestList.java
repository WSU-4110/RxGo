package com.example.demorxgo;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class refillRequestList extends AppCompatActivity {
    FirebaseFirestore fStore=FirebaseFirestore.getInstance ();
    FirebaseAuth fAuth=FirebaseAuth.getInstance ();
    ArrayList<refillrequest> rs = new ArrayList<> ();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_refill_request_list );

        fillRequests ( rs );

    }

    private void fillRequests(ArrayList<refillrequest> rs)
    {
        fStore.collection ( "prescriber" ).document (fAuth.getUid ()).collection ( "Refill Requests" ).get().addOnCompleteListener ( task -> {
            if(task.isSuccessful ()) {
                for (QueryDocumentSnapshot document : task.getResult ()) {
                    Log.d(TAG,document.get ( "Patient Last" ).toString ());
                    rs.add ( new refillrequest ( document.get ("Patient First" ).toString (), document.get ( "Patient Last" ).toString (), document.get ("Drug" ).toString (), document.get ("Patient BirthDay").toString () ));
                }
            }
            adapter5 ma;
            RecyclerView requestL;
            requestL= findViewById ( R.id.requestLst );
            requestL.setLayoutManager(new LinearLayoutManager (getApplicationContext()));
            ma= new adapter5 ( rs );
            requestL.setItemAnimator(new DefaultItemAnimator ());//syntax for lines between items
            requestL.addItemDecoration(new DividerItemDecoration (refillRequestList.this,LinearLayoutManager.VERTICAL));//syntax for vertical scrolling i think
            requestL.setAdapter(ma);
        } );
    }
}