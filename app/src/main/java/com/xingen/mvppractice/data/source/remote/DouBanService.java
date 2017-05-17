package com.xingen.mvppractice.data.source.remote;

import com.xingen.mvppractice.data.entity.MovieList;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by ${新根} on 2017/5/16 0016.
 * blog: http://blog.csdn.net/hexingen
 */
public interface DouBanService{

    @GET("{path}")
    Observable<MovieList> movieList(@Path("path") String path , @QueryMap Map<String,String> options);

}
