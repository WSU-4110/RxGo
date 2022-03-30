package com.example.demorxgo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class adapter3 extends RecyclerView.Adapter<adapter3.MyViewHolder>{
    private ArrayList<patients> arrayList = new ArrayList<patients> ();
    private ArrayList<patients> arrayListFull;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;


    public adapter3(ArrayList<patients> arrayList) {
        this.arrayList = arrayList;
        arrayListFull = new ArrayList<> ( arrayList );
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView first, last, Bday;
        Button saveMe;

        public MyViewHolder(View itemView) {
            super ( itemView );
            first = itemView.findViewById ( R.id.firstNme );
            last = itemView.findViewById ( R.id.lastNme );
            Bday = itemView.findViewById ( R.id.birthDye );
            saveMe = itemView.findViewById ( R.id.seePtBtn );
        }
    }

    @Override
    public adapter3.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.v ( "CreateViewHolder", "in onCreateViewHolder" );
        View itemView = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.savedpatient_list_item, parent, false );
        return new adapter3.MyViewHolder ( itemView );
    }

    @Override
    public void onBindViewHolder(@NonNull adapter3.MyViewHolder holder, final int position) {
        patients p = arrayList.get ( position );

        holder.first.setText ( p.getFirstName () );
        holder.last.setText ( p.getLastName () );
        holder.Bday.setText ( p.getBirthday () );
        fStore=FirebaseFirestore.getInstance ();
        fAuth=FirebaseAuth.getInstance ();
    }

    @Override
    public int getItemCount() {
        return arrayList.size ();
    }
}

