package com.example.knittedwithlove;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Random;

import static com.example.knittedwithlove.DBHelper.KEY_ID;
import static com.example.knittedwithlove.DBHelper.KEY_ID_ITEM;
import static com.example.knittedwithlove.DBHelper.KEY_IMG;
import static com.example.knittedwithlove.DBHelper.KEY_NAME;
import static com.example.knittedwithlove.DBHelper.KEY_ORDERID;
import static com.example.knittedwithlove.DBHelper.KEY_PRICE;
import static com.example.knittedwithlove.DBHelper.KEY_QUANTITY;
import static com.example.knittedwithlove.DBHelper.KEY_TOTAL;
import static com.example.knittedwithlove.DBHelper.KEY_USERITEM;
import static com.example.knittedwithlove.DBHelper.KEY_USERNAME;
import static com.example.knittedwithlove.DBHelper.KEY_USERS;
import static com.example.knittedwithlove.DBHelper.TABLE_CART;
import static com.example.knittedwithlove.DBHelper.TABLE_ITEM;
import static com.example.knittedwithlove.DBHelper.TABLE_LIST;
import static com.example.knittedwithlove.DBHelper.TABLE_ORDER;

public class Cart extends Fragment {



    static String user;
    Intent intent;
    ListView listcart;
    MyAdapter adapter;
    int i;
    static int[] id;
    int IDS[];
    int qu[];
    String Name[];
    String Price[];
    Bitmap images[];

    TextView message;
    TextView SumPrice,delviry,total;
    static int sum=0 , Total;
    CardView cardtotal;
    static Button increc,decres;
     Button buyIt;

    static DBHelper dbHelper;
    SQLiteDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Main_App data = (Main_App) getActivity();
        user = data.getMyData();
        dbHelper= new DBHelper(getContext());
        return inflater.inflate(R.layout.cart,container, false);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        BottomNavigationView bottomNav =view.findViewById(R.id.top_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navLis);

         listcart= view.findViewById(R.id.listcart);


        db = dbHelper.getReadableDatabase();

        Cursor all = db.rawQuery("SELECT * FROM " + TABLE_CART + " WHERE " + KEY_USERS + "=? ", new String[]{user});


         i = all.getCount();
        if(i>0){


            cardtotal= view.findViewById(R.id.totalcard);
            cardtotal.setVisibility(View.VISIBLE);

        IDS = new int[i];
        qu = new int[i];
        int k=0;
        while (all.moveToNext()){
            IDS[k] = all.getInt(all.getColumnIndex(KEY_ID_ITEM));
            qu[k] = all.getInt(all.getColumnIndex(KEY_QUANTITY));
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

                Name[x] = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                Price[x] = cursor.getString(cursor.getColumnIndex(KEY_PRICE));
                ByteArrayInputStream inputStream = new ByteArrayInputStream(cursor.getBlob(cursor.getColumnIndex(KEY_IMG)));
                images[x] = BitmapFactory.decodeStream(inputStream);

            }

            cursor.close();

        }

        //db.close();


            //buy
            buyIt= view.findViewById(R.id.buy);


            adapter = new MyAdapter(getContext().getApplicationContext(), Name, Price, images, qu);
            listcart.setAdapter(adapter);




            //sumprice

          //  tabel cart rhirjinke
            SumPrice= view.findViewById(R.id.sumprice);

            sum=0;
            for (int x=0;x<i;x++) {
                sum+= Integer.parseInt( Price[x])*qu[x];
            }

            SumPrice.setText(""+sum);


            //total
            total= view.findViewById(R.id.total);

            Total=sum+50;
            total.setText(""+Total);


            //deliver
            //delviry= view.findViewById(R.id.deliver);

            buyIt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    final int random = new Random().nextInt(999 - 100) ;

                    for (int x=0;x<i;x++) {
                        ContentValues cv = new ContentValues();
                        cv.put(KEY_ID_ITEM,id[x] );
                        cv.put(KEY_USERS, user);
                        cv.put(KEY_ORDERID, user+random+""+Total);
                        cv.put(KEY_TOTAL, Total);

                        db.insert("'"+TABLE_ORDER+"'", null, cv);
                    }

                    db.execSQL("DELETE FROM "+ TABLE_CART +" WHERE "+ KEY_USERS + " = ?;", new String[]{user});


                    Toast.makeText(getActivity(), "order successfully!", Toast.LENGTH_LONG).show();
                    getFragmentManager().beginTransaction().detach(Cart.this).attach(Cart.this).commit();
                    //finish();
                }
            });


        }
        else
        {
            message= view.findViewById(R.id.noting);
            message.setVisibility(View.VISIBLE);
            message.setText("Your cart is empty");

        }




    }

       private BottomNavigationView.OnNavigationItemSelectedListener navLis=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch(item.getItemId()){
                        case R.id.action_order:
                           // startActivity(new Intent(getActivity(),Orders.class));
                            intent= new Intent(getActivity(),Orders.class);
                            intent.putExtra("user",user);
                            startActivity(intent);
                            getFragmentManager().beginTransaction().detach(Cart.this).attach(Cart.this).commit();
                            break;
                      /*  case R.id.action_requests:
                            startActivity(new Intent(getActivity(),Requests.class));
                            getFragmentManager().beginTransaction().detach(Cart.this).attach(Cart.this).commit();
                            break;*/
                        case R.id.action_list:
                            intent= new Intent(getActivity(),Wish_List.class);
                            intent.putExtra("user",user);
                            startActivity(intent);
                            getFragmentManager().beginTransaction().detach(Cart.this).attach(Cart.this).commit();
                            //startActivity(new Intent(getActivity(),Wish_List.class));
                            break;
                    }
                    return true;
                }
            };

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rName[];
        String rPrice[];
        public int rQuntiy[];
        Bitmap rImgs[];
        TextView quint;
        int num;


        MyAdapter (Context c, String name[], String price[], Bitmap imgs[], int quntity[]) {
            super(c, R.layout.rowcart, R.id.nameitem, name);
            this.context = c;
            this.rName = name;
            this.rPrice = price;
            this.rQuntiy = quntity;
            this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.rowcart, parent, false);
            ImageButton images = row.findViewById(R.id.image);
            // ImageView
            TextView myTitle = row.findViewById(R.id.nameitem);
            TextView myDescription = row.findViewById(R.id.priecitem);
             quint = row.findViewById(R.id.cuonter);

            ImageButton delete = row.findViewById(R.id.remove);
            increc = row.findViewById(R.id.add);
            decres = row.findViewById(R.id.sub);

            // now set our resources on views
            images.setImageBitmap(rImgs[position]);
            myTitle.setText(rName[position]);
            myDescription.setText(rPrice[position]);
            quint.setText(""+rQuntiy[position]);

            ImageView addwishlistImg = row.findViewById(R.id.addwishlist);


            images.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    intent=new Intent(getActivity(), Item.class);
                    int item= id[position];
                    intent.putExtra("ID", item);

                    startActivity(intent);

                }
            });

            addwishlistImg.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    SQLiteDatabase DB = dbHelper.getWritableDatabase();

                    int item= id[position];

                    ContentValues initialValues  =new ContentValues();
                    initialValues .put(KEY_USERS,user );
                    initialValues .put(KEY_ID_ITEM, item);
                   // initialValues .put(KEY_USERITEM, user+item);


                    long r=  DB.insert(TABLE_LIST,null, initialValues );
                   // DB.close();
                    if(r>0)
                        Toast.makeText(getActivity(), "Added successfully!", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getActivity(), "Added already!", Toast.LENGTH_LONG).show();


                }
            });

            delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    SQLiteDatabase DB = dbHelper.getWritableDatabase();

                    int item= id[position];

                    DB.execSQL("DELETE FROM "+ TABLE_CART +" WHERE "+ KEY_ID_ITEM + " = "+item );
                    getFragmentManager().beginTransaction().detach(Cart.this).attach(Cart.this).commit();


                    //DB.close();


                }
            });

            row.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    intent=new Intent(getActivity(), Item.class);
                    int item= id[position];
                    intent.putExtra("ID", item);

                    startActivity(intent);

                }
            });
            increc.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {


                        int num = rQuntiy[position];
                                //Integer.parseInt(quint.getText().toString());
                        num++;
                        rQuntiy[position]=num;
                        quint.setText(""+rQuntiy[position]);

                    db.execSQL("UPDATE " + TABLE_CART +
                            " SET " + KEY_QUANTITY + "= '" + num + "' " + "WHERE "
                            + KEY_USERNAME + " = ? AND "+KEY_ID_ITEM+ "=?", new String[]{user,""+id[position]});

                    getFragmentManager().beginTransaction().detach(Cart.this).attach(Cart.this).commit();


                }

            });

            decres.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {



                            num = rQuntiy[position];
                            num--;

                            if (num <= 0) {
                                /*rQuntiy[position]=1;
                                quint.setText(""+rQuntiy[position]);

                                db.execSQL("UPDATE " + TABLE_CART +
                                        " SET " + KEY_QUANTITY + "= '" + num + "' " + "WHERE "
                                        + KEY_USERNAME + " = ? AND "+KEY_ID_ITEM+ "=?", new String[]{user,""+id[position]});
*/
                                SQLiteDatabase DB = dbHelper.getWritableDatabase();

                                int item= id[position];

                                DB.execSQL("DELETE FROM "+ TABLE_CART +" WHERE "+ KEY_ID_ITEM + " = "+item );
                                //getFragmentManager().beginTransaction().detach(Cart.this).attach(Cart.this).commit();
                               getFragmentManager().beginTransaction().detach(Cart.this).attach(Cart.this).commit();


                            }
                            else {

                                rQuntiy[position]=num;
                                quint.setText(""+rQuntiy[position]);

                                db.execSQL("UPDATE " + TABLE_CART +
                                        " SET " + KEY_QUANTITY + "= '" + num + "' " + "WHERE "
                                        + KEY_USERNAME + " = ? AND "+KEY_ID_ITEM+ "=?", new String[]{user,""+id[position]});

                                getFragmentManager().beginTransaction().detach(Cart.this).attach(Cart.this).commit();
                            }



                }
            });






            return row;
        }
    }


}
