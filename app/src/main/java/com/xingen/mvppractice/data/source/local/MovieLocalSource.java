package com.xingen.mvppractice.data.source.local;

import android.content.Context;
import android.database.Cursor;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.QueryObservable;
import com.squareup.sqlbrite.SqlBrite;
import com.xingen.mvppractice.data.entity.MovieData;
import com.xingen.mvppractice.utils.SchedulerProvider;
import com.xingen.mvppractice.utils.TransformUtils;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by ${新根} on 2017/5/16 0014.
 * blog: http://blog.csdn.net/hexingen
 */
public class MovieLocalSource implements LocalDataSource<MovieData> {
    private BriteDatabase briteDatabase;
    private static  MovieLocalSource instance;
    private Func1<Cursor,MovieData> cursorListFunc1;
    private MovieLocalSource(Context context , SchedulerProvider schedulerProvider){
        MovieDataHelper helper=new MovieDataHelper(context);
        SqlBrite sqlBrite=new SqlBrite.Builder().build();
        this.briteDatabase= sqlBrite.wrapDatabaseHelper(helper,schedulerProvider.io());
        this.cursorListFunc1=new Func1<Cursor, MovieData>() {
            @Override
            public MovieData call(Cursor cursor) {
                return TransformUtils.transformMovieData(cursor);
            }
        };
    }

    /**
     * 获取实例
     * @param context
     * @param schedulerProvider
     * @return
     */
    public static MovieLocalSource getInstance(Context context , SchedulerProvider schedulerProvider){
        if(instance==null){
            instance=new MovieLocalSource(context,schedulerProvider);
        }
        return instance;
    }

    /**
     *
     */
    public static void destroyInstance(){
        instance=null;
    }

    @Override
    public Observable< List<MovieData>> queryAll() {
      return queryAction(MovieConstract.SQL_QUERY_MOVIE,null);
    }
    @Override
    public Observable< List<MovieData>> queryAction(String select, String[] selectArg) {
        QueryObservable observable= selectArg==null?this.briteDatabase.createQuery(MovieConstract.TABLE_NAME_MOVI,select):this.briteDatabase.createQuery(MovieConstract.TABLE_NAME_MOVI,select,selectArg);
        return observable.mapToList(this.cursorListFunc1);
    }
    @Override
    public long insert(MovieData movieData) {
        return this.briteDatabase.insert(MovieConstract.TABLE_NAME_MOVI,TransformUtils.transformMovieData(movieData));
    }
    @Override
    public int bulkInsert(List<MovieData> list) {
        int size=0;
        //开启事物
        BriteDatabase.Transaction transaction= this.briteDatabase.newTransaction();
        try {
             for (MovieData movieData:list){
                 this.briteDatabase.insert(MovieConstract.TABLE_NAME_MOVI,TransformUtils.transformMovieData(movieData));
             }
            transaction.markSuccessful();
            size=list.size();
        }catch (Exception e){
            size=0;
            e.printStackTrace();
        }finally {
            transaction.end();
        }
        return size;
    }
    @Override
    public int update(MovieData movieData, String select, String[] selectArg) {
        return 0;
    }
    @Override
    public int delite(MovieData movieData, String select, String[] selectArg) {
        return 0;
    }
    @Override
    public void deliteAll() {
    }
}
