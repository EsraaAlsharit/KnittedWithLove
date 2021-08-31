package com.example.knittedwithlove;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import static com.example.knittedwithlove.DBHelper.KEY_FNAME;
import static com.example.knittedwithlove.DBHelper.KEY_ID;
import static com.example.knittedwithlove.DBHelper.KEY_ID_ITEM;
import static com.example.knittedwithlove.DBHelper.KEY_IMG;
import static com.example.knittedwithlove.DBHelper.KEY_LNAME;
import static com.example.knittedwithlove.DBHelper.KEY_NAME;
import static com.example.knittedwithlove.DBHelper.KEY_ORDERID;
import static com.example.knittedwithlove.DBHelper.KEY_USERITEM;
import static com.example.knittedwithlove.DBHelper.KEY_USERS;
import static com.example.knittedwithlove.DBHelper.TABLE_ORDER;
import static com.example.knittedwithlove.DBHelper.TABLE_USERS;

public class Orders extends AppCompatActivity {

    static String user;
    ListView listorder;
    MyAdapter adapter;

    int id[], IDS[];

    String FName, LName;

    Intent intent;

    ImageButton  delete;
    TextView message;
    ArrayList<Integer> idsList;
    DBHelper dbHelper;
    SQLiteDatabase db;
    String ordercode[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        dbHelper= new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        listorder= findViewById(R.id.listorder);
        idsList = new ArrayList<>();

        Cursor all = db.rawQuery("SELECT * FROM '"+TABLE_ORDER+"' WHERE " + KEY_USERS + "=? ", new String[]{user});

      //  Cursor count = db.rawQuery("SELECT DISTINCT "+KEY_ORDERID+" FROM "+"'"+TABLE_ORDER+"'" ,null);


        //int i = count.getCount();
        int i = all.getCount();

        if(i>0){


            ordercode = new String[i];
            int k=0;
            while (all.moveToNext()){
                ordercode[k] = all.getString(all.getColumnIndex(KEY_ORDERID));
              //  IDS[k] = all.getInt(all.getColumnIndex(KEY_ID_ITEM));
                k++;
            }  /* while (count.moveToNext()){
                ordercode[k] = count.getString(count.getColumnIndex(KEY_ORDERID));
              //  IDS[k] = all.getInt(all.getColumnIndex(KEY_ID_ITEM));
                k++;
            }*/

            db = dbHelper.getReadableDatabase();

                Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USERS + "= ?",new String[]{user});

                while (cursor.moveToNext()) {

                    FName = cursor.getString(cursor.getColumnIndex(KEY_FNAME));
                    LName = cursor.getString(cursor.getColumnIndex(KEY_LNAME));

                }

                cursor.close();


            String FullName =FName+" "+LName;



            adapter = new MyAdapter(getApplicationContext(), ordercode, FullName);
            listorder.setAdapter(adapter);


        }
        else
        {
            message= findViewById(R.id.noting);
            message.setVisibility(View.VISIBLE);

        }

    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rName;
        String rCode[];



        MyAdapter(Context c,String code[], String name ) {
            super(c, R.layout.roworder, R.id.nameitem, code);
            this.context = c;
            this.rName = name;
            this.rCode = code;


        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.roworder, parent, false);


            TextView CODE = row.findViewById(R.id.code);
            TextView NAME = row.findViewById(R.id.fullnameuser);

            CODE.setText(rCode[position]);
            NAME.setText(rName);

            Button Detail = row.findViewById(R.id.detail);



            Detail.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    intent=new Intent(Orders.this, DetailView.class);

                    String code= rCode[position];
                    intent.putExtra("CODE", code);

                    startActivity(intent);

                }
            });










            return row;
        }
    }
}
