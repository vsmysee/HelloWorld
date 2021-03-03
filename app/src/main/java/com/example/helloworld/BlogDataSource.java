package com.example.helloworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BlogDataSource {
    // Database fields
    private SQLiteDatabase mDatabase;
    private MySQLiteHelper mDbHelper;
    private String[] mAllColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_KEY, MySQLiteHelper.COLUMN_NAME};

    public BlogDataSource(Context context) {
        mDbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Blog createRate(String key, String name) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_KEY, key);
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        long insertId = mDatabase.insert(MySQLiteHelper.TABLE_RATE, null,
                values);
        Cursor cursor = mDatabase.query(MySQLiteHelper.TABLE_RATE,
                mAllColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Blog newComment = cursorToRate(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteRate(Blog comment) {
        long id = comment.getId();
        mDatabase.delete(MySQLiteHelper.TABLE_RATE, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Blog> getAllRates() {
        List<Blog> rates = new ArrayList<Blog>();

        Cursor cursor = mDatabase.query(MySQLiteHelper.TABLE_RATE,
                mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Blog rate = cursorToRate(cursor);
            rates.add(rate);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return rates;
    }

    private Blog cursorToRate(Cursor cursor) {
        Blog rate = new Blog();
        rate.setId(cursor.getLong(0));
        rate.setKey(cursor.getString(1));
        rate.setName(cursor.getString(2));
        return rate;
    }

    public void deleteAll() {
        mDatabase.delete(MySQLiteHelper.TABLE_RATE, null, null);
    }
}