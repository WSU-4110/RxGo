package com.example.demorxgo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;


import com.example.demorxgo.databinding.FragmentPrescriptionsBinding;

import java.util.zip.Inflater;

public class Info extends Fragment {
    ListView listView;
    String[] Prescriptions = {
             "Levothyroxine", "Lisinopril", "Metformin", "Amlodipine",
            "Metoprolol", "Omeprazole", "Losartan", "Simvastatin"};
    ArrayAdapter<String> arrayAdapter;

    public Info() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_info, container, false);
        super.onCreate(savedInstanceState);
        listView = v.findViewById(R.id.listview);
       // arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Prescriptions);
        listView.setAdapter(arrayAdapter);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, Prescriptions);



        return v;

    }

//    public boolean  onCreateOptionsMenu(Menu menu){
//
//        MenuItem item = menu.findItem(R.id.action_search)
//        SearchView  = (SearchView) menuItem.getActionView();
//        searchView.setQueryHint("Type here to Search");
//
//
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText ) {
//                arrayAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
// public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        //getMenuInflater().inflate(R.menu.menu, menu);
       MenuItem menuItem = menu.findItem(R.id.button);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to Search");



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText ) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}

