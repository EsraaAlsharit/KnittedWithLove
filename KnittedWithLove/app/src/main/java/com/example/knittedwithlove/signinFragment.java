package com.example.knittedwithlove;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import static com.example.knittedwithlove.DBHelper.KEY_PASSWORD;

import static com.example.knittedwithlove.DBHelper.KEY_USERNAME;
import static com.example.knittedwithlove.DBHelper.TABLE_USERS;



public class signinFragment extends AppCompatActivity {

    private TextView register;
    private EditText username;
    private EditText password;
    Button signin;
    DBHelper dbHelper;
    String name, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signin);

        dbHelper=new DBHelper(this);
        register=findViewById(R.id.donthaveaccunt);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        signin=findViewById(R.id.signIn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHaveAcc();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name=username.getText().toString();
                 pass=password.getText().toString();

                if (TextUtils.isEmpty(name)||TextUtils.isEmpty(pass)){
                    //something empty
                    Toast.makeText(signinFragment.this,"Please enter all the field",Toast.LENGTH_SHORT).show();
                    //stop the function
                    return;
                }
                //if ok
                SQLiteDatabase database=dbHelper.getReadableDatabase();
                Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_USERS+" WHERE "+ KEY_USERNAME +"=? and "+ KEY_PASSWORD+"=?",new String[]{name,pass});
                if (cursor.getCount()>0){
                    signin();
                }

                else
                    Toast.makeText(signinFragment.this,"Username or password not correct",Toast.LENGTH_LONG).show();



            }

        });
    }

    public void setHaveAcc(){
        startActivity(new Intent(this, signupFragment.class));
    }

    public void signin(){
        Intent intent= new Intent(this, Main_App.class);
        intent.putExtra("user",name);
        startActivity(intent);
    }
}
