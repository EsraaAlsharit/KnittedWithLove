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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import static com.example.knittedwithlove.DBHelper.KEY_ID_ITEM;
import static com.example.knittedwithlove.DBHelper.KEY_IMG;
import static com.example.knittedwithlove.DBHelper.KEY_NAME;
import static com.example.knittedwithlove.DBHelper.KEY_ID;
import static com.example.knittedwithlove.DBHelper.KEY_ORDERID;
import static com.example.knittedwithlove.DBHelper.KEY_PRICE;
import static com.example.knittedwithlove.DBHelper.KEY_QUANTITY;
import static com.example.knittedwithlove.DBHelper.KEY_TOTAL;
import static com.example.knittedwithlove.DBHelper.KEY_TYPE;
import static com.example.knittedwithlove.DBHelper.KEY_USERITEM;
import static com.example.knittedwithlove.DBHelper.KEY_USERS;
import static com.example.knittedwithlove.DBHelper.TABLE_CART;
import static com.example.knittedwithlove.DBHelper.TABLE_ITEM;
import static com.example.knittedwithlove.DBHelper.TABLE_LIST;
import static com.example.knittedwithlove.DBHelper.TABLE_ORDER;

public class DetailView extends AppCompatActivity {


    int id[];
    String Name[];
    String Price[];
    MyAdapter adapter;
    Bitmap images[];
    Intent intent;
    String users= "";
    ListView listView;
    ImageButton wish, cart;
    ImageButton img;
    ArrayList<Integer> idsList;
    DBHelper dbHelper;
    SQLiteDatabase db;
    int quntity,P;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoping);
        Main_App data = new Main_App();
        users = data.getMyData();

        Intent intent = getIntent();
        String code = intent.getStringExtra("CODE");

        listView = findViewById(R.id.shop);


        idsList = new ArrayList<>();

        //dbHelper = new DBHelper(this);
        dbHelper = new DBHelper(getBaseContext());
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + "'" + TABLE_ORDER + "' " + " WHERE " + KEY_ORDERID + "=? ", new String[]{code});
        int i = cursor.getCount();

        id = new int[i];
        Name = new String[i];
        Price = new String[i];
        images = new Bitmap[i];

        int x = 0;
        if(i>0) {
            while (cursor.moveToNext()) {

                id[x] = cursor.getInt(cursor.getColumnIndex(KEY_ID_ITEM));
          /*  idsList.add(id[x]);
            Name[x] = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            Price[x] = cursor.getString(cursor.getColumnIndex(KEY_PRICE));
            ByteArrayInputStream inputStream = new ByteArrayInputStream(cursor.getBlob(cursor.getColumnIndex(KEY_IMG)));
            images[x] = BitmapFactory.decodeStream(inputStream);
*/
                x++;
            }

            cursor.close();

            Name = new String[i];
            Price = new String[i];
            images = new Bitmap[i];

            for(int z=0;z<i;z++) {
                db = dbHelper.getReadableDatabase();

                Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_ITEM + " WHERE " + KEY_ID + "=? ", new String[]{"" + id[z]});
              //  int k = 0;
                while (cur.moveToNext()) {

                    Name[z] = cur.getString(cur.getColumnIndex(KEY_NAME));
                    Price[z] = cur.getString(cur.getColumnIndex(KEY_PRICE));
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(cur.getBlob(cur.getColumnIndex(KEY_IMG)));
                    images[z] = BitmapFactory.decodeStream(inputStream);

                //    k++;
                }

            }

            adapter = new MyAdapter(getApplicationContext(), Name, Price, images);
            listView.setAdapter(adapter);
        }



    }


    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rName[];
        String rPrice[];
        Bitmap rImgs[];


        MyAdapter(Context c, String name[], String price[], Bitmap imgs[]) {
            super(c, R.layout.rowitems, R.id.nameitem, name);
            this.context = c;
            this.rName = name;
            this.rPrice = price;
            this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.rowitems, parent, false);
            ImageButton images = row.findViewById(R.id.image);
            // ImageView
            TextView myTitle = row.findViewById(R.id.nameitem);
            TextView myDescription = row.findViewById(R.id.priecitem);
            // now set our resources on views
            images.setImageBitmap(rImgs[position]);
            myTitle.setText(rName[position]);
            myDescription.setText(rPrice[position]);



            ImageView addwishlistImg = row.findViewById(R.id.wishlist);

            images.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    intent=new Intent(DetailView.this, Item.class);
                    //int id= idsList.get(position);
                    int item= id[position];
                    intent.putExtra("ID", item);

                    startActivity(intent);

                }
            });

            ImageView basketImg = row.findViewById(R.id.addcart);
            basketImg.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {


                    db = dbHelper.getReadableDatabase();
                    Cursor cur = db.rawQuery("SELECT * FROM " +TABLE_CART+ " WHERE "+KEY_USERS+"=? AND "+KEY_ID_ITEM+"="+id[position] , new String[]{users});

                    while(cur.moveToNext()){

                        quntity = cur.getInt(cur.getColumnIndex(KEY_QUANTITY));
                        P = cur.getInt(cur.getColumnIndex(KEY_TOTAL));

                    }

                    cur.close();
                    int pri= Integer.parseInt(rPrice[position]);

                    if(quntity!=0) {

                        int n =quntity+1;

                        db.execSQL("UPDATE " +TABLE_CART+
                                " SET " + KEY_QUANTITY + "= '" + n + "' , "+
                                KEY_TOTAL + "="+(pri*P)+ " WHERE "+KEY_ID_ITEM+ "="+id[position]+" AND "+KEY_USERS+"=?", new String[]{users});

                    }
                    else {

                        ContentValues cv = new ContentValues();
                        cv.put(KEY_USERS, users);
                        cv.put(KEY_ID_ITEM, id[position]);
                        cv.put(KEY_TOTAL,rPrice[position]);
                        cv.put(KEY_QUANTITY,1);

                        //  cv.put(KEY_QUANTITY, dropdown.getSelectedItem().toString());
                        db.insert(TABLE_CART,null, cv);

                    }
//                    SQLiteDatabase DB = dbHelper.getWritableDatabase();

                  /*  ContentValues cv=new ContentValues();
                    cv.put(KEY_USERS,users );
                    cv.put(KEY_ID_ITEM, id[position]);
                    cv.put(KEY_TOTAL,rPrice[position]);
                    cv.put(KEY_QUANTITY,1);*/

                    //


                    //if(r>0)
                    Toast.makeText(DetailView.this, "Added successfully!", Toast.LENGTH_LONG).show();
                    // else
                    //   Toast.makeText(Shoping.this, "Added already!", Toast.LENGTH_LONG).show();
                }
            });

            addwishlistImg.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    SQLiteDatabase DB = dbHelper.getWritableDatabase();

                    int item= id[position];

                    ContentValues initialValues  =new ContentValues();
                    initialValues .put(KEY_USERS,users );
                    initialValues .put(KEY_ID_ITEM, item);
                    //initialValues .put(KEY_USERITEM, users+item);



                    long r=  DB.insert(TABLE_LIST,null, initialValues );

                    if(r>0)
                        Toast.makeText(DetailView.this, "Added successfully!", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(DetailView.this, "Added already!", Toast.LENGTH_LONG).show();

                    // Toast.makeText(parent.getContext(), "Add to wish list " +position , Toast.LENGTH_SHORT).show();
                }
            });

            row.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    intent=new Intent(DetailView.this, Item.class);
                    int item= id[position];
                    intent.putExtra("ID", item);

                    startActivity(intent);
                    // Toast.makeText(parent.getContext(), "Whole View " + position , Toast.LENGTH_SHORT).show();
                }
            });
            return row;
        }
    }
}
