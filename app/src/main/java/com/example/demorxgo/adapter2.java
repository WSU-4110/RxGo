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

public class adapter2 extends RecyclerView.Adapter<adapter2.MyViewHolder> implements Filterable {
    private ArrayList<patients> arrayList = new ArrayList<patients> ();
    private ArrayList<patients> arrayListFull;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public adapter2(ArrayList<patients> arrayList) {
        this.arrayList = arrayList;
        arrayListFull = new ArrayList<> ( arrayList );
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView first, last, Bday;
        Button saveMe;

        public MyViewHolder(View itemView) {
            super ( itemView );
            first = itemView.findViewById ( R.id.firstNm );
            last = itemView.findViewById ( R.id.lastNm );
            Bday = itemView.findViewById ( R.id.birthDy );
            saveMe = itemView.findViewById ( R.id.savePtBtn );
        }
    }

    @Override
    public adapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.v ( "CreateViewHolder", "in onCreateViewHolder" );
        View itemView = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.patient_list_item, parent, false );
        return new adapter2.MyViewHolder ( itemView );
    }

    @Override
    public void onBindViewHolder(@NonNull adapter2.MyViewHolder holder, final int position) {
        patients p = arrayList.get ( position );

        holder.first.setText ( p.getFirstName () );
        holder.last.setText ( p.getLastName () );
        holder.Bday.setText ( p.getBirthday () );
        fStore=FirebaseFirestore.getInstance ();
        fAuth=FirebaseAuth.getInstance ();

        //saving patient in dr's patients collection
        holder.saveMe.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                DocumentReference df = fStore.collection ( "prescriber" ).document (fAuth.getUid ()).collection ( "Patients" ).document (p.getId ());
                Map<String, Object> newPt = new HashMap<> ();
                newPt.put ( "ID",p.getId () );
                newPt.put ( "First Name",p.getFirstName () );
                newPt.put ( "Last Name",p.getLastName () );
                newPt.put ( "BirthDay",p.getBirthday () );
                df.set(newPt);
                notifyDataSetChanged();
            }
        } );
    }

    @Override
    public int getItemCount() {
        return arrayList.size ();
    }

    @Override
    public Filter getFilter() {
        return filtered;
    }

    private Filter filtered = new Filter () {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<patients> filteredList = new ArrayList<> ();

            if (constraint == null || constraint.length () == 0) {
                filteredList.addAll ( arrayListFull );
            } else {
                String filterPattern = constraint.toString ().trim ();

                for (patients item : arrayListFull) {
                    if (item.getLastName ().contains ( filterPattern )) {
                        filteredList.add ( item );
                    }
                }
            }

            FilterResults results = new FilterResults ();
            results.values = filteredList;

            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayList.clear();
            arrayList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}

