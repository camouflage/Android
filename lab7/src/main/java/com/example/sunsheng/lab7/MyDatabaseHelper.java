package com.example.sunsheng.lab7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sunsheng on 12/4/15.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Contacts.db";
    private static final String TABLE_NAME = "Contacts";
    private static final int DB_VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table " + TABLE_NAME + "(_id integer primary key autoincrement, " +
                "_stuId text not null, _name text not null, _tel text);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS " + TABLE_NAME);
        onCreate(db);
    }

    public long insert(Contact entity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("_stuId", entity.getStuId());
        cv.put("_name", entity.getName());
        cv.put("_tel", entity.getTel());

        long id = db.insert(TABLE_NAME, null, cv);
        db.close();
        return id;
    }

    public int update(Contact entity) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "_stuId = ?";
        String[] whereArgs = {entity.getStuId()};

        ContentValues cv = new ContentValues();
        cv.put("_stuId", entity.getStuId());
        cv.put("_name", entity.getName());
        cv.put("_tel", entity.getTel());

        int rows = db.update(TABLE_NAME, cv, whereClause, whereArgs);
        db.close();

        return rows;
    }

    public int delete(Contact entity) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "_stuId = ?";
        String[] whereArgs = {entity.getStuId()};

        int rows = db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();

        return rows;
    }

    public Cursor query() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
}

