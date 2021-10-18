package com.qeecan.multical.Note.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NoteSQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "notes";
    public static final String TITLE = "title";
    public static final String ID = "_id";
    public static final String CONTENT = "content";
    public static final String TAG = "tag";
    public static final String TIME = "time";

    public NoteSQLiteHelper(@Nullable Context context) {
        super(context, "note", null, 1);
    }



    /*
     * 数据库初创建时调用该方法
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME
                + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TITLE + " TEXT NOT NULL,"
                + CONTENT + " TEXT,"
                + TAG + " TEXT ,"
                + TIME + " TEXT NOT NULL)"

        );
    }


    /*
     * 数据库初版本迭代时可以回调此方法
     * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
