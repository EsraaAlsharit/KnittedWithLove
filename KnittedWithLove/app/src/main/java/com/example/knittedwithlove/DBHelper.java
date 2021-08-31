package com.example.knittedwithlove;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class DBHelper extends SQLiteAssetHelper {
    public static final String DB_NAME="DB.db";
    public static final int DB_VERSION=1;
    //users table
    public static final String TABLE_USERS="Users";
    public static final String KEY_USERNAME="Username";
    public static final String KEY_FNAME="FName";
    public static final String KEY_LNAME="Lname";
    public static final String KEY_EMAIL="Email";
    public static final String KEY_PASSWORD="Password";
    public static final String KEY_CARDID="Card";
    public static final String KEY_ADDRESSID="Address";

    //item table
    public static final String TABLE_ITEM="Item";
    public static final String KEY_ID="id";
    public static final String KEY_NAME="name";
    public static final String KEY_IMG="img";
    public static final String KEY_PRICE="price";
    public static final String KEY_TYPE="type";

    //cart table
    public static final String TABLE_CART="Cart";
    public static final String KEY_ID_ITEM="id_item";
    public static final String KEY_USERS="username";
    public static final String KEY_TOTAL="total";
    public static final String KEY_QUANTITY="quantity";

    //list table
    public static final String TABLE_LIST="List";
    //public static final String KEY_ID_ITEM="id_item";
    //public static final String KEY_USERS="username";
    public static final String KEY_USERITEM="useritem";

    //cart table
    public static final String TABLE_ORDER="Order";
    public static final String KEY_ORDERID="order_id";
    //public static final String KEY_ID_ITEM="id_item";
    //public static final String KEY_USERS="username";
    //public static final String KEY_TOTAL="total";

    //card
    public static final String TABLE_CARD="Credit";
    public static final String KEY_IDS ="id";
    //public static final String KEY_USERS="username";
    public static final String KEY_NUMCARD="number";
    public static final String KEY_CVVCARD="cvv";
    public static final String KEY_DATECARD="date";

    //card
    public static final String TABLE_ADDRESS="Address";
   // public static final String KEY_IDS ="id";
    //public static final String KEY_USERS="username";
    public static final String KEY_CONTRY="country";
    public static final String KEY_CITY="city";
    public static final String KEY_ADDRESS="address";
    public static final String KEY_PHONE="phonenumber";

    public DBHelper(Context context) {
        super(context, "DB.db", null,null, 1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }

}
