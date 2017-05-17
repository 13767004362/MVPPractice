package com.xingen.mvppractice.data.source.remote;


import com.xingen.mvppractice.data.entity.Movie;
import com.xingen.mvppractice.data.entity.MovieList;
import com.xingen.mvppractice.utils.OkHttpProvider;
import com.xingen.mvppractice.utils.SchedulerProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by ${新根} on 2017/5/16 0016.
 * blog: http://blog.csdn.net/hexingen
 */
public class RemoteDataSource {
    private final String BASE_URL = "https://api.douban.com/v2/movie/";
    private final Retrofit retrofit;
    private static RemoteDataSource instance;
    private DouBanService douBanService;
    private       SchedulerProvider provider;
    private RemoteDataSource() {
        this.provider= SchedulerProvider.getInstacne();
        OkHttpClient okHttpClient = OkHttpProvider.createOkHttpClient();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        this.douBanService = this.retrofit.create(DouBanService.class);
    }

    /**
     * 单例类对象
     *
     * @return
     */
    public static synchronized RemoteDataSource getInstance() {
        if (instance == null) {
            instance = new RemoteDataSource();
        }
        return instance;
    }

    public Subscription movieList(Subscriber<List<Movie>> subscriber) {

        String url = "search";
        Map<String,String> map=new HashMap<>();
        map.put("q","张艺谋");
        Observable<MovieList> observable = this.douBanService.movieList(url, map);
        //floatMap操作符转换
       Observable<List<Movie>> observable1= observable.flatMap(new Func1<MovieList, Observable<List<Movie>>>() {
           @Override
           public Observable<List<Movie>> call(MovieList movieList) {

               return  Observable.just(movieList.getSubjects());
           }
       });
       return observable1.subscribeOn(provider.io()).unsubscribeOn(provider.io()).observeOn(provider.ui()).subscribe(subscriber);
    }

}
