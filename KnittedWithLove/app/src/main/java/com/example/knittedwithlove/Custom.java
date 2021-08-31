package com.example.knittedwithlove;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Custom extends Fragment  {
    Spinner dropdown;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom,container, false);
       // dropdown.setOnItemSelectedListener(this);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        dropdown = view.findViewById(R.id.spinner);

        String[] items = new String[]{"","Accessories", "blanket", "Hat", "Scarf", "Sweater", "Doll", "Other"};

        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Log.v("item", (String) parent.getItemAtPosition(position));
                switch (position) {
                    case 1:
                        // Whatever you want to happen when the first item gets selected
                        break;
                    case 2:
                        // Whatever you want to happen when the second item gets selected
                        break;
                    case 3:
                        // Whatever you want to happen when the thrid item gets selected
                        break;
                    case 4:
                        // Whatever you want to happen when the thrid item gets selected
                        break;
                    case 5:
                        // Whatever you want to happen when the thrid item gets selected
                        break;
                    case 6:
                        // Whatever you want to happen when the thrid item gets selected
                        break;
                    case 7:
                        // Whatever you want to happen when the thrid item gets selected
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


    }



}

