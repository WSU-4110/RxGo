package com.example.demorxgo;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.MyViewHolder> {
    private ArrayList<prescription> arrayList = new ArrayList<>();

    public adapter(ArrayList<prescription> arrayList) {
        this.arrayList = arrayList;
        Log.d(TAG, arrayList.toString());
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView drug, strength, refill;

        public MyViewHolder(View itemView){
            super(itemView);
            Log.v("ViewHolder", "in View Holder");
            drug = itemView.findViewById(R.id.drugName);
            strength = itemView.findViewById(R.id.drugStrength);
            refill = itemView.findViewById(R.id.refillN);
            Log.d(TAG, "Viewholer construct");
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int   viewType) {
        Log.v("CreateViewHolder", "in onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Log.v("BindViewHolder", "in onBindViewHolder");
        prescription d = arrayList.get(position);
        holder.drug.setText(d.getDrug());
        holder.strength.setText(d.getStrength());
        holder.refill.setText(d.getId());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
