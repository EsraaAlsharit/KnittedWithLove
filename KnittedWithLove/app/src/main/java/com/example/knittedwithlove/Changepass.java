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


import static com.example.knittedwithlove.DBHelper.KEY_USERNAME;
import static com.example.knittedwithlove.DBHelper.TABLE_USERS;
import static com.example.knittedwithlove.DBHelper.KEY_PASSWORD;

public class Changepass extends AppCompatActivity {

    EditText Old, New;
    Button cancel, ChangPass;
    String oldpass;
    SQLiteDatabase db;
    DBHelper  dbHelper;


    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Intent intent = getIntent();
        final String users = intent.getStringExtra("user");



            setContentView(R.layout.changepass);

            Old = findViewById(R.id.oldpass);
            New = findViewById(R.id.newpass);
            cancel = findViewById(R.id.cancel_action);
            ChangPass = findViewById(R.id.changepass);

            dbHelper = new DBHelper(this);
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USERNAME + "=? ", new String[]{users});
            // int i=cursor.getCount();

            while (cursor.moveToNext()) {

                oldpass = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD));
            }
            cursor.close();


            ChangPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Old.getText().toString() == "" || New.getText().toString() == "") {
                        Toast.makeText(Changepass.this, "Please enter all the field", Toast.LENGTH_SHORT).show();

                    } else {
                        if (Old.getText().toString() == oldpass) {
                            if (Old.getText().toString() != New.getText().toString()) {

                                db.execSQL("UPDATE " + TABLE_USERS +
                                        " SET " + KEY_PASSWORD + "= '" + New.getText() + "' " + "WHERE " + KEY_USERNAME + " = ?;", new String[]{users});
                                Toast.makeText(Changepass.this, "Changed successfully!", Toast.LENGTH_LONG).show();
                                recreate();
                                //finish();
                            } else {
                                Toast.makeText(Changepass.this, "Your password not new!", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(Changepass.this, "Your password is wrong!", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            });


            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();


                }
            });





    }
}
