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

import static com.example.knittedwithlove.DBHelper.KEY_ID;
import static com.example.knittedwithlove.DBHelper.KEY_ID_ITEM;
import static com.example.knittedwithlove.DBHelper.KEY_IMG;
import static com.example.knittedwithlove.DBHelper.KEY_NAME;
import static com.example.knittedwithlove.DBHelper.KEY_PRICE;
import static com.example.knittedwithlove.DBHelper.KEY_QUANTITY;
import static com.example.knittedwithlove.DBHelper.KEY_TOTAL;
import static com.example.knittedwithlove.DBHelper.KEY_TYPE;
import static com.example.knittedwithlove.DBHelper.KEY_USERS;
import static com.example.knittedwithlove.DBHelper.TABLE_CART;
import static com.example.knittedwithlove.DBHelper.TABLE_ITEM;
import static com.example.knittedwithlove.DBHelper.TABLE_LIST;

public class Wish_List extends AppCompatActivity {

    String user;
    ListView list;
    MyAdapter adapter;

    int id[], IDS[];
    int qu[];
    String Name[];
    String Price[];
    Bitmap images[];
    Intent intent;
    ImageButton  delete;
    TextView message;
    ArrayList<Integer> idsList;
    DBHelper dbHelper;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish__list);


        dbHelper= new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        list= findViewById(R.id.list);
        idsList = new ArrayList<>();

        Cursor all = db.rawQuery("SELECT * FROM " + TABLE_LIST + " WHERE " + KEY_USERS + "=? ", new String[]{user});


        int i = all.getCount();
        if(i>0){


            IDS = new int[i];
            qu = new int[i];
            int k=0;
            while (all.moveToNext()){
                IDS[k] = all.getInt(all.getColumnIndex(KEY_ID_ITEM));
               // qu[k] = all.getInt(all.getColumnIndex(KEY_QUANTITY));
                k++;
            }

            id = new int[i];

            Name = new String[i];
            Price = new String[i];
            images = new Bitmap[i];
            db = dbHelper.getReadableDatabase();

            for (int x=0;x<i;x++) {


                Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ITEM + " WHERE " + KEY_ID + "= "+IDS[x], null);

                while (cursor.moveToNext()) {

                    id[x] = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                    idsList.add(id[x]);
                    Name[x] = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                    Price[x] = cursor.getString(cursor.getColumnIndex(KEY_PRICE));
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(cursor.getBlob(cursor.getColumnIndex(KEY_IMG)));
                    images[x] = BitmapFactory.decodeStream(inputStream);

                }

                cursor.close();

            }

            db.close();


            adapter = new MyAdapter(getApplicationContext(), Name, Price, images);
            list.setAdapter(adapter);


        }
        else
        {
            message= findViewById(R.id.noting);
            message.setVisibility(View.VISIBLE);

        }


    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rName[];
        String rPrice[];
        Bitmap rImgs[];


        MyAdapter(Context c, String name[], String price[], Bitmap imgs[]) {
            super(c, R.layout.rowwishlist, R.id.nameitem, name);
            this.context = c;
            this.rName = name;
            this.rPrice = price;
            this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.rowwishlist, parent, false);
            ImageButton images = row.findViewById(R.id.image);
            // ImageView
            TextView myTitle = row.findViewById(R.id.nameitem);
            TextView myDescription = row.findViewById(R.id.priecitem);
            // now set our resources on views
            images.setImageBitmap(rImgs[position]);
            myTitle.setText(rName[position]);
            myDescription.setText(rPrice[position]);

            delete = row.findViewById(R.id.remove);

            ImageView addwishlistImg = row.findViewById(R.id.wishlist);

            images.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    intent=new Intent(Wish_List.this, Item.class);
                    //int id= idsList.get(position);
                    int item= id[position];
                    intent.putExtra("ID", item);

                    startActivity(intent);

                }
            });


            delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    SQLiteDatabase DB = dbHelper.getWritableDatabase();

                    int item= id[position];

                   DB.execSQL("DELETE FROM "+ TABLE_LIST +" WHERE "+ KEY_ID_ITEM + " = "+item );
                   //getFragmentManager().beginTransaction().detach(W.this).attach(Cart.this).commit();

                    recreate();

                    DB.close();


                }
            });

            ImageView basketImg = row.findViewById(R.id.addcart);
            basketImg.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    SQLiteDatabase DB = dbHelper.getWritableDatabase();

                    ContentValues cv=new ContentValues();
                    cv.put(KEY_USERS,user);
                    cv.put(KEY_ID_ITEM, id[position]);
                    cv.put(KEY_TOTAL,rPrice[position]);
                    cv.put(KEY_QUANTITY,1);

                    long r=DB.insert(TABLE_CART,null, cv);


                    if(r>0)
                        Toast.makeText(Wish_List.this, "Added successfully!", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(Wish_List.this, "Added already!", Toast.LENGTH_LONG).show();
                }
            });

            row.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    intent=new Intent(Wish_List.this, Item.class);
                    int item= id[position];
                    intent.putExtra("ID", item);
                    startActivity(intent);

                }
            });





            return row;
        }
    }

}
