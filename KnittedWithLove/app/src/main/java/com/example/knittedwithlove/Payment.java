package com.example.knittedwithlove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.knittedwithlove.DBHelper.KEY_CARDID;
import static com.example.knittedwithlove.DBHelper.KEY_CVVCARD;
import static com.example.knittedwithlove.DBHelper.KEY_DATECARD;
import static com.example.knittedwithlove.DBHelper.KEY_IDS;
import static com.example.knittedwithlove.DBHelper.KEY_NUMCARD;
import static com.example.knittedwithlove.DBHelper.KEY_USERNAME;
import static com.example.knittedwithlove.DBHelper.KEY_USERS;
import static com.example.knittedwithlove.DBHelper.TABLE_CARD;
import static com.example.knittedwithlove.DBHelper.TABLE_USERS;

public class Payment extends AppCompatActivity {


    EditText creditExpireEt, creditCVVEt, creditnumEt;
    TextView number,date;
    String numcard, datecard, cvvcard;
    String numbercard, dateex="";
    Button save, delete;
    SQLiteDatabase db, DB;
    DBHelper  dbHelper;
    int CARD;
    Cursor check;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        final String users = intent.getStringExtra("user");

        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        check = db.rawQuery("SELECT * FROM " + TABLE_USERS +" WHERE "+ KEY_USERNAME+ "=? ",new String[]{users});

        while(check.moveToNext()){

            CARD = check.getInt(check.getColumnIndex(KEY_CARDID));

        }
        check.close();

        if(CARD!=0){
            setContentView(R.layout.list_card);

            number = findViewById(R.id.numcredit);
            date = findViewById(R.id.datecredit);
            delete = findViewById(R.id.deletecredit);

            dbHelper = new DBHelper(this);
            DB = dbHelper.getReadableDatabase();

            Cursor cur = DB.rawQuery("SELECT * FROM " + TABLE_CARD +" WHERE "+ KEY_USERS+ "=? ",new String[]{users});

            while(cur.moveToNext()){

                numbercard  = cur.getString(cur.getColumnIndex(KEY_NUMCARD));
                dateex  = cur.getString(cur.getColumnIndex(KEY_DATECARD));

            }
            cur.close();

            number.setText(numbercard);
            date.setText(dateex);




            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    db.execSQL("DELETE FROM "+ TABLE_CARD +" WHERE "+ KEY_USERNAME + " = ?;", new String[]{users});

                    db.execSQL("UPDATE " + TABLE_USERS +
                            " SET " + KEY_CARDID + "= '0' " + "WHERE " + KEY_USERNAME + " = ?;", new String[]{users});
                    Toast.makeText(Payment.this, "Deleted successfully!", Toast.LENGTH_LONG).show();
                    recreate();
                    //finish();
                }
            });



        }
        else {
            setContentView(R.layout.activity_payment);


            creditExpireEt = findViewById(R.id.expire_date);
            creditCVVEt = findViewById(R.id.cvv);
            creditnumEt = findViewById(R.id.cardnum);
            save = findViewById(R.id.savecard);

            creditExpireEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    int len = s.toString().length();

                    if (len == 2)
                        creditExpireEt.append("/");
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (creditCVVEt.getText().toString() == "" || creditExpireEt.getText().toString() == "" || creditnumEt.getText().toString() == "") {
                        Toast.makeText(Payment.this, "Please enter all the field", Toast.LENGTH_SHORT).show();

                    } else {

                        numcard = creditnumEt.getText().toString();
                        datecard = creditExpireEt.getText().toString();
                        cvvcard = creditCVVEt.getText().toString();


                        ContentValues cv = new ContentValues();
                        cv.put(KEY_NUMCARD, numcard);
                        cv.put(KEY_DATECARD, datecard);
                        cv.put(KEY_CVVCARD, cvvcard);
                        cv.put(KEY_USERS, users);

                       // if (TextUtils.isEmpty(numcard) || TextUtils.isEmpty(datecard) || TextUtils.isEmpty(cvvcard)) {
                            //something empty
                         //   Toast.makeText(Payment.this, "Please enter all the field", Toast.LENGTH_SHORT).show();

                       // } else {
                            db.insert(TABLE_CARD, null, cv);

                            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CARD + " WHERE " + KEY_USERS + "=? ", new String[]{users});

                            while (cursor.moveToNext()) {
                                id = cursor.getString(cursor.getColumnIndex(KEY_IDS));
                            }
                            cursor.close();
                            db.execSQL("UPDATE " + TABLE_USERS +
                                    " SET " + KEY_CARDID + "= '" + id + "' " + "WHERE " + KEY_USERNAME + " = ?;", new String[]{users});
                            Toast.makeText(Payment.this, "Added successfully!", Toast.LENGTH_LONG).show();
                           // finish();
                        recreate();
                       // }

                    }

                }
            });


        }




    }
}
