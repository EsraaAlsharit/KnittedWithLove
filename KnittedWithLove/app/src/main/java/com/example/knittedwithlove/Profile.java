package com.example.knittedwithlove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.knittedwithlove.DBHelper.KEY_FNAME;
import static com.example.knittedwithlove.DBHelper.KEY_LNAME;
import static com.example.knittedwithlove.DBHelper.KEY_USERNAME;
import static com.example.knittedwithlove.DBHelper.TABLE_USERS;

public class Profile extends AppCompatActivity {

    EditText FN, LN;
    Button save, ChangPass;
    String UserName, FName, LName, Em,PassWor;
    SQLiteDatabase db;
    DBHelper  dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        final String users = intent.getStringExtra("user");

        FN= findViewById(R.id.fnfilde);
        LN= findViewById(R.id.lnfilde);
        save= findViewById(R.id.save);
        ChangPass= findViewById(R.id.changepass);

        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS +" WHERE "+ KEY_USERNAME+ "=? ",new String[]{users});
       // int i=cursor.getCount();

        while(cursor.moveToNext()){

            UserName = cursor.getString(cursor.getColumnIndex(KEY_USERNAME));
            FName = cursor.getString(cursor.getColumnIndex(KEY_FNAME));
            LName = cursor.getString(cursor.getColumnIndex(KEY_LNAME));

        }
        cursor.close();

        FN.setText(FName);
        LN.setText(LName);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FName!=FN.getText().toString() || LName!=LN.getText().toString()) {
                    db.execSQL("UPDATE " + TABLE_USERS +
                            " SET " + KEY_FNAME + "= '" + FN.getText() + "' , " + KEY_LNAME + " = '" + LN.getText() + "' " + "WHERE " + KEY_USERNAME + " = ?;", new String[]{users});
                    Toast.makeText(Profile.this, "Changed successfully!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Profile.this, "no changes appear!", Toast.LENGTH_LONG).show();
                }
            }
        });

        ChangPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile.this, Changepass.class);
                intent.putExtra("user", users);
               // intent.putExtra("what", "password");
                startActivity(intent);

            }
        });
        //LN.setText(LName);






    }
}
