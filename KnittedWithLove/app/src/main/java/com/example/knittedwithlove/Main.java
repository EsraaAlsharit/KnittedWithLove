package com.example.knittedwithlove;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button signin=findViewById(R.id.Sign_In);
        Button signup=findViewById(R.id.Sign_Up);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTosignin();

            }

        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTosignup();

            }

        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    public void goTosignin(){
        Intent intent= new Intent(this, signinFragment.class);
        startActivity(intent);
    }

    public void goTosignup(){
        Intent intent= new Intent(this, signupFragment.class);
        startActivity(intent);
    }




}
