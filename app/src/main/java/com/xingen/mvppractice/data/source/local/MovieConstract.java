package com.xingen.mvppractice.data.source.local;

import android.provider.BaseColumns;

/**
 * Created by ${新根} on 2017/5/13 0013.
 * blog: http://blog.csdn.net/hexingen
 */
public class MovieConstract implements BaseColumns {
    /**
     * 数据库的信息
     */
    public static final String SQLITE_NAME="movie.db";
    public static final int SQLITE_VERSON=1;
    /**
     * 表和字段信息
     */
    public static final  String TABLE_NAME_MOVI="movieData";
    public static final  String COLUMN_ID ="id";
    public static final String COLUMN_YEAR="year";
    public static final String COLUMN_TITLE="title";
    public static final String COLUMN_IMAGES="image";
    public static final String SQL_QUERY_MOVIE="select * from "+TABLE_NAME_MOVI;
}
