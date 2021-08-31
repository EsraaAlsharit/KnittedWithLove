package com.example.knittedwithlove;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main_App extends AppCompatActivity {

    public String users;
    public static String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_app);

        BottomNavigationView bottomNav =findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navLis);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Home()).commit();

        Intent intent = getIntent();
         users = intent.getStringExtra("user");

        user=users;
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navLis=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment select= null;
                    switch(item.getItemId()){
                        case R.id.action_Home:
                            select =new Home();
                            break;
                      //  case R.id.action_Customize:
                        //    select = new Custom();
                          //  break;
                        case R.id.action_user:
                            select = new Account();
                            break;
                        case R.id.action_Cart:
                            select = new Cart();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, select).commit();
                    return true;
                }


            };

    public static String getMyData() {
        return user;
    }
}
