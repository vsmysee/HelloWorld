package com.example.helloworld;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_RATE = "blog";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_KEY = "blog";
    public static final String COLUMN_NAME = "name";

    private static final String DATABASE_NAME = "units.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_RATE + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_KEY
            + " CHAR(100) not null," + COLUMN_NAME + " CHAR(100) not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATE);
        onCreate(db);
    }

}