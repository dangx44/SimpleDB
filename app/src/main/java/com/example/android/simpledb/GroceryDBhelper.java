package com.example.android.simpledb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.simpledb.GroceryContract.*;

public class GroceryDBhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "groceryList.db";
    public static final int DATABASE_VERSION =1 ;

    public GroceryDBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_SQL_TABLE = "CREATE TABLE " +
                GroceryEntry.TABLE_NAME + " (" +
                GroceryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GroceryEntry.COL_NAME + " TEXT NOT NULL, " +
                GroceryEntry.COL_AMOUNT + " INTEGER NOT NULL, " +
                GroceryEntry.COL_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(CREATE_SQL_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GroceryEntry.TABLE_NAME);
        onCreate(db);
    }
}
