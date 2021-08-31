package com.example.knittedwithlove;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class Account extends Fragment {

    String user;
    Intent i;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Main_App data = (Main_App) getActivity();
         user = data.getMyData();
        return inflater.inflate(R.layout.account,container, false);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        NavigationView listacc =view.findViewById(R.id.listinfo);
        listacc.bringToFront();
        listacc.setNavigationItemSelectedListener(accLis);
    }


    private NavigationView.OnNavigationItemSelectedListener accLis=
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                    switch(item.getItemId()){
                        case R.id.action_Addresses:
                            i= new Intent(getActivity(),Addresses.class);
                            i.putExtra("user",user);
                            startActivity(i);

                            break;
                        case R.id.action_Payment:
                             i= new Intent(getActivity(),Payment.class);
                            i.putExtra("user",user);
                            startActivity(i);
                            break;
                        case R.id.action_Profile:
                             i= new Intent(getActivity(),Profile.class);
                            i.putExtra("user",user);
                            startActivity(i);
                            break;
                        case R.id.action_Language:
                            //changeLanguage();
                            Toast.makeText(getActivity(), "UNDEVELOPED YET!", Toast.LENGTH_LONG).show();
                            //UNDEVELOPED YET
                            break;
                        case R.id.action_whatsapp:
                            Toast.makeText(getActivity(), "UNDEVELOPED YET!", Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("smsto:"+"966539352658")).setPackage("com.whatsapp"));
                            break;
                        case R.id.action_instgram:
                            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.instagram.com/o_mohamed/")));
                            break;

                        case R.id.action_twitter:
                            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://twitter.com/_um_mohamed1")));
                            break;

                        case R.id.action_facebook:
                            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.facebook.com/O_mohamed-1323429594393318/")));

                            break;

                        case R.id.action_signOut:
                            Intent intent=new Intent(getContext(),Main.class);
                            startActivity(intent);

                            break;
                        case R.id.action_aboutUs:
                            startActivity(new Intent(getActivity(),About_us.class));

                            break;

                    }
                    return true;
                }
            };



    private void changeLanguage(){
        final String[] lan ={"English", "عربي"};
        AlertDialog.Builder mBuilder =new AlertDialog.Builder(getActivity());
        mBuilder.setTitle("Choose Language:");
        mBuilder.setSingleChoiceItems(lan, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(i==0){
                    //setLocale("en");
                   // recreate();
                }
                else if(i==1){
                  //  setLocale("ar");
                    //recreate();
                }

                dialog.dismiss();
            }
        });
        AlertDialog mDialog= mBuilder.create();
        mDialog.show();
    }

    /*private void setLocale (String lang){
        Locale locale =new Locale(lang);
        Locale.setDefault(locale);
        Configuration config= new Configuration();
       // config.locale= locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext)
    }*/
}
