package com.example.knittedwithlove;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Home extends Fragment {

    private CardView cardtoy, cardblanket, cardhat, cardscarf, cardsweater, cardaccessories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.home,container, false);


    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



        cardtoy=view.findViewById(R.id.cardtoy);
        cardblanket=view.findViewById(R.id.cardblanket);
        cardhat=view.findViewById(R.id.cardhat);
        cardscarf=view.findViewById(R.id.cardscarf);
        cardsweater=view.findViewById(R.id.cardsweater);
        cardaccessories=view.findViewById(R.id.cardaccessories);



        cardtoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Shoping.class);
                intent.putExtra("type", "toys");
                startActivity(intent);
            }
        });

        cardblanket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Shoping.class);
                intent.putExtra("type", "blanket");
                startActivity(intent);
            }
        });

        cardhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Shoping.class);
                intent.putExtra("type", "hat");
                startActivity(intent);
            }
        });

        cardscarf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Shoping.class);
                intent.putExtra("type", "scarf");
                startActivity(intent);
            }
        });

        cardsweater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Shoping.class);
                intent.putExtra("type", "sweater");
                startActivity(intent);
            }
        });

        cardaccessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Shoping.class);
                intent.putExtra("type", "accessories");
                startActivity(intent);
            }
        });


    }


}


