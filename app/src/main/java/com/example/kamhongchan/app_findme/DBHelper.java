package com.example.kamhongchan.app_findme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * extends SQLiteOpenHelper,override onCreate() and onUpgrade() method
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * create table here,an item includes name and phonenumber
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "create table if not exists person"
                +"(name text primary key,"
                +"phoneNumber text not null)";
        db.execSQL(create_table);
        Log.d("DBHelper","23 onCreate create_table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}