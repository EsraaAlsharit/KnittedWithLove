package com.example.knittedwithlove;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import static com.example.knittedwithlove.DBHelper.KEY_ADDRESSID;
import static com.example.knittedwithlove.DBHelper.KEY_CARDID;
import static com.example.knittedwithlove.DBHelper.KEY_EMAIL;
import static com.example.knittedwithlove.DBHelper.KEY_FNAME;
import static com.example.knittedwithlove.DBHelper.KEY_LNAME;
import static com.example.knittedwithlove.DBHelper.KEY_PASSWORD;

import static com.example.knittedwithlove.DBHelper.KEY_USERNAME;
import static com.example.knittedwithlove.DBHelper.TABLE_USERS;


public class signupFragment extends AppCompatActivity {

    EditText Ln,Fn,pass, em, Un;
    TextView haveAcc;
    Button signup;
    String Fname, Lname,Email,Pass, Uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signup);
        DBHelper dbHelper= new DBHelper(getBaseContext());

        SQLiteDatabase database=dbHelper.getReadableDatabase();
        Fn = findViewById(R.id.fname);
        Un = findViewById(R.id.username);
        Ln = findViewById(R.id.lname);
        pass = findViewById(R.id.password);
        em = findViewById(R.id.email);
        haveAcc = findViewById(R.id.haveaccunt);
        signup = findViewById(R.id.signup);

        haveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHaveAcc();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper= new DBHelper(getBaseContext());

                SQLiteDatabase database=dbHelper.getWritableDatabase();

                Fname=Fn.getText().toString();
                Lname=Ln.getText().toString();
                Email=em.getText().toString();
                Pass=pass.getText().toString();
                Uname=Un.getText().toString();

                ContentValues cv=new ContentValues();
                cv.put(KEY_FNAME,Fname );
                cv.put(KEY_USERNAME,Uname );
                cv.put(KEY_LNAME, Lname);
                cv.put(KEY_EMAIL, Email);
                cv.put(KEY_PASSWORD, Pass);
                cv.put(KEY_CARDID, 1);
                cv.put(KEY_ADDRESSID, 1);


                long insert=database.insert(TABLE_USERS,null, cv);

                if (TextUtils.isEmpty(Fname)||TextUtils.isEmpty(Lname)||TextUtils.isEmpty(Pass)||TextUtils.isEmpty(Email)||TextUtils.isEmpty(Uname)){
                    //something empty
                    Toast.makeText(signupFragment.this,"Please enter all the field",Toast.LENGTH_SHORT).show();

                }
                else {

                if (insert>0){
                    Dialog();
                }
                else {
                    Toast.makeText(signupFragment.this, "Username Already Exist!", Toast.LENGTH_LONG).show();
                }

                }
            }
        });

    }

    private void Dialog() {
        Toast.makeText(signupFragment.this, "Registered successfully!", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(signupFragment.this,Main_App.class);
                        intent.putExtra("user",Uname);
                        startActivity(intent);
    }

    public void setHaveAcc(){
        startActivity(new Intent(this, signinFragment.class));
    }
}