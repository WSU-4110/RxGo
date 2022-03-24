package com.example.demorxgo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import com.example.demorxgo.databinding.FragmentInfoBinding;
import com.google.firebase.auth.FirebaseAuth;


public class Info extends Fragment {
    EditText UserInput;
    FirebaseAuth fAuth;

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
        UserInput = (EditText) getView().findViewById(R.id.input7);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

}

