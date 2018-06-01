package com.example.sylwia.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import com.example.sylwia.myapplication.MyPurchases.PurchasedComp;
import com.example.sylwia.myapplication.MyPurchases.PurchasedCompOverview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sylwia on 5/31/2018.
 */

public class DBHandler extends SQLiteOpenHelper{
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "stockmarketDB.db";
        public static final String TABLE_PURCHASE = "purchase";
        public static final String PURCHASE_ID = "id";
        public static final String PURCHASE_SYMBOL = "symbol";
        public static final String PURCHASE_PRICE = "purchaseprice";
        public static final String PURCHASE_AMOUNT = "purchaseamount";

        //We need to pass database information along to superclass
        public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            System.out.println("create");
            System.out.println("create");
            System.out.println("create");
            System.out.println("create");
            System.out.println("create");
            System.out.println("create");
            String query = "CREATE TABLE " + TABLE_PURCHASE + "(" +
                    PURCHASE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PURCHASE_SYMBOL + " TEXT, " +
                    PURCHASE_AMOUNT + " INTEGER, " +
                    PURCHASE_PRICE + " REAL " +
                    ");";
            db.execSQL(query);
        }
        //Lesson 51
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            System.out.println("update");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PURCHASE);
            onCreate(db);
        }

        public void addPurchase(PurchasedComp purchasedComp){
            ContentValues values = new ContentValues();
            values.put(PURCHASE_SYMBOL, purchasedComp.getSymbol());
            values.put(PURCHASE_PRICE, purchasedComp.getPurchasePrice());
            values.put(PURCHASE_AMOUNT, purchasedComp.getPurchaseAmount());
            SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_PURCHASE, null, values);
            db.close();
            System.out.println("added to db");
        }

        //Delete a product from the database
        public void deleteProduct(String symbol){
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM " + TABLE_PURCHASE + " WHERE " + PURCHASE_SYMBOL + "=\"" + symbol + "\";");
        }
public List<PurchasedComp> selectAllPurchasedComp(){
            List<PurchasedComp> purchasedCompList= new ArrayList<>();
    SQLiteDatabase db = getWritableDatabase();
    String query = "SELECT * FROM " + TABLE_PURCHASE + " WHERE 1";
    Cursor recordSet = db.rawQuery(query, null);
    recordSet.moveToFirst();
    while (!recordSet.isAfterLast()) {
        if (recordSet.getString(recordSet.getColumnIndex("symbol")) != null) {
            Integer id = Integer.parseInt(recordSet.getString(recordSet.getColumnIndex(PURCHASE_ID)));
            String symbol = recordSet.getString(recordSet.getColumnIndex(PURCHASE_SYMBOL));
            Double purchasePrice = Double.parseDouble(recordSet.getString(recordSet.getColumnIndex(PURCHASE_PRICE)));
            Integer purchaseAmount = Integer.parseInt(recordSet.getString(recordSet.getColumnIndex(PURCHASE_AMOUNT)));
            PurchasedComp purchasedComp = new PurchasedComp(id, symbol, purchasePrice, purchaseAmount);
            purchasedCompList.add(purchasedComp);
        }
        recordSet.moveToNext();
    }
    db.close();
    return purchasedCompList;
}

    public List<PurchasedCompOverview> selectAllPurchasedCompOverview(){
        List<PurchasedCompOverview> purchasedCompOverviewList= new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PURCHASE + " WHERE 1";
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();
        while (!recordSet.isAfterLast()) {
            if (recordSet.getString(recordSet.getColumnIndex("symbol")) != null) {
                Integer id = Integer.parseInt(recordSet.getString(recordSet.getColumnIndex(PURCHASE_ID)));
                String symbol = recordSet.getString(recordSet.getColumnIndex(PURCHASE_SYMBOL));
                Double purchasePrice = Double.parseDouble(recordSet.getString(recordSet.getColumnIndex(PURCHASE_PRICE)));
                System.out.println(recordSet.getString(recordSet.getColumnIndex(PURCHASE_AMOUNT)));
                Integer amount = Integer.parseInt(recordSet.getString(recordSet.getColumnIndex(PURCHASE_AMOUNT)));
                PurchasedCompOverview purchasedCompOverview = new PurchasedCompOverview(id, symbol, purchasePrice, amount);
                purchasedCompOverviewList.add(purchasedCompOverview);
            }
            recordSet.moveToNext();
        }
        db.close();
        return purchasedCompOverviewList;
    }


        // this is goint in record_TextView in the Main activity.
        public String databaseToString(){
            String dbString = "";
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_PURCHASE + " WHERE 1";// why not leave out the WHERE  clause?

            //Cursor points to a location in your results
            Cursor recordSet = db.rawQuery(query, null);
            System.out.println(recordSet.toString());
            //Move to the first row in your results
            recordSet.moveToFirst();

            //Position after the last row means the end of the results
            while (!recordSet.isAfterLast()) {
                // null could happen if we used our empty constructor
                if (recordSet.getString(recordSet.getColumnIndex("symbol")) != null) {
                    dbString += recordSet.getString(recordSet.getColumnIndex("symbol"));
                    dbString += recordSet.getString(recordSet.getColumnIndex("purchaseprice"));
                    dbString += "\n";
                }
                recordSet.moveToNext();
            }
            db.close();
            return dbString;
        }

    public void updateShema(SQLiteDatabase db) {
        System.out.println("update");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PURCHASE);
        onCreate(db);
    }
}
