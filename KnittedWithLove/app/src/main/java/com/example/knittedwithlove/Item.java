package com.example.knittedwithlove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import static com.example.knittedwithlove.DBHelper.KEY_ADDRESSID;
import static com.example.knittedwithlove.DBHelper.KEY_ID;
import static com.example.knittedwithlove.DBHelper.KEY_IDS;
import static com.example.knittedwithlove.DBHelper.KEY_ID_ITEM;
import static com.example.knittedwithlove.DBHelper.KEY_IMG;
import static com.example.knittedwithlove.DBHelper.KEY_NAME;
import static com.example.knittedwithlove.DBHelper.KEY_PRICE;
import static com.example.knittedwithlove.DBHelper.KEY_QUANTITY;
import static com.example.knittedwithlove.DBHelper.KEY_TOTAL;
import static com.example.knittedwithlove.DBHelper.KEY_TYPE;
import static com.example.knittedwithlove.DBHelper.KEY_USERITEM;
import static com.example.knittedwithlove.DBHelper.KEY_USERS;
import static com.example.knittedwithlove.DBHelper.TABLE_CART;
import static com.example.knittedwithlove.DBHelper.TABLE_ITEM;
import static com.example.knittedwithlove.DBHelper.TABLE_LIST;
import static com.example.knittedwithlove.DBHelper.TABLE_USERS;

public class Item extends AppCompatActivity {

    ImageView img;
    ImageButton wish;
    Button cart;

    int id, quntity ,P,Price;
    String Name, users , IDS;

    Bitmap images;

    TextView name, priec;

    SQLiteDatabase dbre, DB,db;
    DBHelper  dbHeer;
    Cursor cursor;
    String []num ={"1","2","3","4","5","6","7","8","9","10"};

    Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Main_App data = new Main_App();
        users = data.getMyData();

        Intent intent = getIntent();
        final int ID = intent.getIntExtra("ID",0);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, num);
        dropdown= findViewById(R.id.spinner);
        dropdown.setAdapter(adapter);

        img = findViewById(R.id.fullimge);

        wish = findViewById(R.id.addwishlist);
        cart = findViewById(R.id.addcart);

        name = findViewById(R.id.nameitem);
        priec = findViewById(R.id.priecitem);


         dbHeer = new DBHelper(this);
         dbre = dbHeer.getReadableDatabase();
         cursor = dbre.rawQuery("SELECT * FROM " +TABLE_ITEM+" WHERE "+KEY_IDS+"="+ID, null);

        while(cursor.moveToNext()){

            Name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            Price = cursor.getInt(cursor.getColumnIndex(KEY_PRICE));
           ByteArrayInputStream inputStream = new ByteArrayInputStream(cursor.getBlob(cursor.getColumnIndex(KEY_IMG)));
          images = BitmapFactory.decodeStream(inputStream);

        }
        cursor.close();
        //Toast.makeText(this, "Add to basket " + ID , Toast.LENGTH_SHORT).show();

        DB = dbHeer.getWritableDatabase();

        img.setImageBitmap(images);
        name.setText(Name);
        priec.setText(""+Price);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = dbHeer.getReadableDatabase();
                Cursor cur = db.rawQuery("SELECT * FROM " +TABLE_CART+ " WHERE "+KEY_USERS+"=? AND "+KEY_ID_ITEM+"="+ID+" " , new String[]{users});

                while(cur.moveToNext()){

                    quntity = cur.getInt(cur.getColumnIndex(KEY_QUANTITY));
                    P = cur.getInt(cur.getColumnIndex(KEY_TOTAL));

                }
                cur.close();

                if(quntity!=0) {
                    int n =quntity+Integer.parseInt(dropdown.getSelectedItem().toString());

                    db.execSQL("UPDATE " + TABLE_CART +
                            " SET " + KEY_QUANTITY + "= '" + n + "' , "+
                            KEY_TOTAL + "=? "+ "WHERE "+KEY_ID_ITEM+ "="+ID+" AND "+KEY_USERS+"=?", new String[]{""+(Price*P),users});

                   // cv.put(KEY_TOTAL, ""+);
                    //cv.put(KEY_QUANTITY, ""+n);

                   // DB.insert(TABLE_CART, null, cv);
                }
                else {
                    ContentValues cv = new ContentValues();
                    cv.put(KEY_USERS, users);
                    cv.put(KEY_ID_ITEM, ID);
                    cv.put(KEY_TOTAL, Price);
                    cv.put(KEY_QUANTITY, dropdown.getSelectedItem().toString());

                    DB.insert(TABLE_CART, null, cv);
                }
                    Toast.makeText(Item.this, "Added successfully!", Toast.LENGTH_LONG).show();

            }
        });


        wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues cv=new ContentValues();
                cv.put(KEY_USERS,users );
                cv.put(KEY_ID_ITEM, ID);
              //  cv .put(KEY_USERITEM, users+ID);

                long r=DB.insert(TABLE_LIST,null, cv);

                if(r>0)
                    Toast.makeText(Item.this, "Added successfully!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Item.this, "Added already!", Toast.LENGTH_LONG).show();


            }
        });

    }
}
