package com.example.knittedwithlove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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

import static com.example.knittedwithlove.DBHelper.KEY_FNAME;
import static com.example.knittedwithlove.DBHelper.KEY_LNAME;
import static com.example.knittedwithlove.DBHelper.KEY_NUMCARD;
import static com.example.knittedwithlove.DBHelper.KEY_USERNAME;
import static com.example.knittedwithlove.DBHelper.TABLE_ADDRESS;
import static com.example.knittedwithlove.DBHelper.KEY_CONTRY;
import static com.example.knittedwithlove.DBHelper.KEY_CITY;
import static com.example.knittedwithlove.DBHelper.KEY_IDS;
import static com.example.knittedwithlove.DBHelper.KEY_ADDRESS;
import static com.example.knittedwithlove.DBHelper.KEY_PHONE;
import static com.example.knittedwithlove.DBHelper.KEY_USERS;
import static com.example.knittedwithlove.DBHelper.KEY_ADDRESSID;
import static com.example.knittedwithlove.DBHelper.TABLE_USERS;

public class Addresses extends AppCompatActivity {

    EditText country, city, address, phonenum;
    TextView fullname,fulladress, mobile;
    String Count, City, Addres, Phonenum;
    String fname, lname;
    Button save, delete,edit;
    SQLiteDatabase db, DB;
    DBHelper  dbHelper;
    int place;
    Cursor check;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        final String users = intent.getStringExtra("user");

       // final String users = intent.getStringExtra("user");

        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        check = db.rawQuery("SELECT * FROM " + TABLE_USERS +" WHERE "+ KEY_USERNAME+ "=? ",new String[]{users});

        while(check.moveToNext()){

            place = check.getInt(check.getColumnIndex(KEY_ADDRESSID));

        }
        check.close();


        if(place!=0){
            setContentView(R.layout.list_address);

            fullname = findViewById(R.id.fullname);
            fulladress = findViewById(R.id.fulladdress);
            mobile = findViewById(R.id.mobile);
            delete = findViewById(R.id.deletaddress);
            edit = findViewById(R.id.editaddress);

            dbHelper = new DBHelper(this);
            DB = dbHelper.getReadableDatabase();

            Cursor cur = DB.rawQuery("SELECT * FROM " + TABLE_ADDRESS +" WHERE "+ KEY_USERS+ "=? ",new String[]{users});

            while(cur.moveToNext()){

                Count  = cur.getString(cur.getColumnIndex(KEY_CONTRY));
                City  = cur.getString(cur.getColumnIndex(KEY_CITY));
                Addres  = cur.getString(cur.getColumnIndex(KEY_ADDRESS));
                Phonenum  = cur.getString(cur.getColumnIndex(KEY_PHONE));

            }
            cur.close();


            fulladress.setText(Count+"-"+City+"-"+Addres);
            mobile.setText(Phonenum);
            Cursor cur2 = DB.rawQuery("SELECT * FROM " + TABLE_USERS +" WHERE "+ KEY_USERS+ "=? ",new String[]{users});

            while(cur2.moveToNext()){

                fname  = cur2.getString(cur2.getColumnIndex(KEY_FNAME));
                lname  = cur2.getString(cur2.getColumnIndex(KEY_LNAME));

            }
            cur.close();

            fullname.setText(fname+" "+lname);



            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    db.execSQL("DELETE FROM "+ TABLE_ADDRESS +" WHERE "+ KEY_USERNAME + " = ?;", new String[]{users});

                    db.execSQL("UPDATE " + TABLE_USERS +
                            " SET " + KEY_ADDRESSID + "= '0' " + "WHERE " + KEY_USERNAME + " = ?;", new String[]{users});
                    Toast.makeText(Addresses.this, "Deleted successfully!", Toast.LENGTH_LONG).show();
                    recreate();
                    //finish();
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i= new Intent(Addresses.this, ChangeAdd.class);
                    i.putExtra("user", users);
                   // i.putExtra("what", "address");
                    startActivity(i);
                  //  finish();
                    recreate();

                }
            });

        } else {
            setContentView(R.layout.activity_addresses);

            country = findViewById(R.id.countryfild);
            city = findViewById(R.id.cityfild);
            address = findViewById(R.id.addressfild);
            phonenum = findViewById(R.id.phonefild);


            save = findViewById(R.id.saveaddress);


            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DB= dbHelper.getWritableDatabase();

                    if (country.getText().toString() == "" || city.getText().toString() == "" || address.getText().toString() == "" || phonenum.getText().toString() == "") {
                        Toast.makeText(Addresses.this, "Please enter all the field", Toast.LENGTH_SHORT).show();

                    } else {

                        Count = country.getText().toString();
                        City = city.getText().toString();
                        Addres = address.getText().toString();
                        Phonenum = phonenum.getText().toString();


                        ContentValues cv = new ContentValues();
                        cv.put( KEY_CONTRY, Count);
                        cv.put(KEY_CITY, City);
                        cv.put(KEY_ADDRESS, Addres);
                        cv.put(KEY_PHONE, Phonenum);
                        cv.put(KEY_USERS, users);

                       DB.insert(TABLE_ADDRESS, null, cv);

                            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ADDRESS + " WHERE " + KEY_USERS + "=? ", new String[]{users});

                            while (cursor.moveToNext()) {
                                id = cursor.getString(cursor.getColumnIndex(KEY_IDS));
                            }
                            cursor.close();
                            db.execSQL("UPDATE " + TABLE_USERS +
                                    " SET " + KEY_ADDRESSID + "= '" + id + "' " + "WHERE " + KEY_USERNAME + " = ?;", new String[]{users});
                            Toast.makeText(Addresses.this, "Added successfully!", Toast.LENGTH_LONG).show();
                            //finish();
                        recreate();


                    }


                }
            });
        }



    }
}

