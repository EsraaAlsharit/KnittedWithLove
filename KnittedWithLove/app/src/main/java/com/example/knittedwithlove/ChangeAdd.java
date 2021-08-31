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

import static com.example.knittedwithlove.DBHelper.KEY_ADDRESS;
import static com.example.knittedwithlove.DBHelper.KEY_CITY;
import static com.example.knittedwithlove.DBHelper.KEY_CONTRY;
import static com.example.knittedwithlove.DBHelper.KEY_PHONE;
import static com.example.knittedwithlove.DBHelper.KEY_USERNAME;
import static com.example.knittedwithlove.DBHelper.KEY_USERS;
import static com.example.knittedwithlove.DBHelper.TABLE_ADDRESS;


public class ChangeAdd extends AppCompatActivity {


    Button cancel;

    SQLiteDatabase db;
    DBHelper  dbHelper;


    EditText country, city, address, phonenum;

    String Count, City, Addres, Phonenum;

    Button save;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Intent intent = getIntent();
        final String users = intent.getStringExtra("user");




            setContentView(R.layout.changeaddress);

            country = findViewById(R.id.countryfild);
            city = findViewById(R.id.cityfild);
            address = findViewById(R.id.addressfild);
            phonenum = findViewById(R.id.phonefild);
        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ADDRESS +" WHERE "+ KEY_USERS+ "=? ",new String[]{users});
        // int i=cursor.getCount();

        while(cursor.moveToNext()){

            Count = cursor.getString(cursor.getColumnIndex(KEY_CONTRY));
            City = cursor.getString(cursor.getColumnIndex(KEY_CITY));
            Addres= cursor.getString(cursor.getColumnIndex(KEY_ADDRESS));
            Phonenum = cursor.getString(cursor.getColumnIndex(KEY_PHONE));
        }
        cursor.close();

        country .setText(Count);
        city.setText(City);
        address.setText(Addres);
        phonenum .setText(Phonenum);


            save = findViewById(R.id.saveaddress);
        cancel = findViewById(R.id.cancel_action);


            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    db= dbHelper.getWritableDatabase();

                    if (country.getText().toString() == "" || city.getText().toString() == "" || address.getText().toString() == "" || phonenum.getText().toString() == "") {
                        Toast.makeText(ChangeAdd.this, "Please enter all the field", Toast.LENGTH_SHORT).show();

                    } else {

                        Count = country.getText().toString();
                        City = city.getText().toString();
                        Addres = address.getText().toString();
                        Phonenum = phonenum.getText().toString();

                        db.execSQL("UPDATE " + TABLE_ADDRESS +
                                " SET " + KEY_CONTRY + "= '" + Count + "' ,"+ KEY_CITY + "= '" + City + "' ,"
                                + KEY_ADDRESS + "= '" + Addres + "' , "
                                + KEY_PHONE + "= '" + Phonenum + "' "+ "WHERE " + KEY_USERS + " = ?;", new String[]{users});

                        Toast.makeText(ChangeAdd.this, "Changed successfully!", Toast.LENGTH_LONG).show();

                        finish();


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
