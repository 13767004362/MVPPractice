package com.xingen.mvppractice.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ${新根} on 2017/5/13 0013.
 * blog: http://blog.csdn.net/hexingen
 */
public class MovieDataHelper extends SQLiteOpenHelper {
    public static final String CREATE_TABLE_MOVIE = "create table " +
            MovieConstract.TABLE_NAME_MOVI + "(" +
            MovieConstract._ID + " integer primary key autoincrement," +
            MovieConstract.COLUMN_ID + " text," +
            MovieConstract.COLUMN_TITLE + " text," +
            MovieConstract.COLUMN_YEAR + " text," +
            MovieConstract.COLUMN_IMAGES + " text"
            + ")";

    public MovieDataHelper(Context context) {
        super(context, MovieConstract.SQLITE_NAME, null, MovieConstract.SQLITE_VERSON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
