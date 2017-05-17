package com.xingen.mvppractice.movielist;

import com.xingen.mvppractice.data.entity.Movie;
import com.xingen.mvppractice.data.entity.MovieData;
import com.xingen.mvppractice.data.source.local.LocalDataSource;
import com.xingen.mvppractice.data.source.remote.RemoteDataSource;
import com.xingen.mvppractice.utils.SchedulerProvider;
import com.xingen.mvppractice.utils.TransformUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ${新根} on 2017/5/16 0016.
 * blog: http://blog.csdn.net/hexingen
 */
public class MovieListPresenter implements MovieListConstract.Presenter {
    private CompositeSubscription compositeSubscription;
    private MovieListConstract.View view;
    private LocalDataSource<MovieData> dataLocalDataSource;
    private RemoteDataSource remoteDataSource;
    private SchedulerProvider schedulerProvider;

    public MovieListPresenter(MovieListConstract.View view, LocalDataSource<MovieData> dataLocalDataSource, RemoteDataSource remoteDataSource) {
        this.compositeSubscription = new CompositeSubscription();
        this.dataLocalDataSource = dataLocalDataSource;
        this.remoteDataSource = remoteDataSource;
        this.schedulerProvider = SchedulerProvider.getInstacne();
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void collectionMovie(final List<Movie> list) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                List<MovieData> movieDataList = new ArrayList<>();
                for (Movie movie : list) {
                    movieDataList.add(TransformUtils.transformMovies(movie));
                }
                int size = dataLocalDataSource.bulkInsert(movieDataList);
                subscriber.onNext(size > 0 ? true : false);
                subscriber.onCompleted();
            }
        }).subscribeOn(schedulerProvider.io()).
                observeOn(schedulerProvider.ui()).
                subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (isViewBind()) {
                            String msg="收藏失败";
                            view.showToast(msg);
                        }
                    }
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (isViewBind()) {
                            String msg=aBoolean==false?"收藏失败":"收藏成功，可在收藏页面查看";
                            view.showToast(msg);
                        }
                    }
                });
        this.compositeSubscription.add(subscription);
    }

    @Override
    public void subscribe() {
        loadRemoteTask();
    }

    /**
     * 开始加载远程的数据
     */
    private void loadRemoteTask() {
        Subscription subscription = remoteDataSource.movieList(new Subscriber<List<Movie>>() {
            @Override
            public void onCompleted() {
                if (isViewBind()) {
                    view.showToast("获取列表成功");
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isViewBind()) {
                    view.showToast("加载失败");
                }
            }

            @Override
            public void onNext(List<Movie> list) {
                view.loadMovieList(list);
            }
        });
        this.compositeSubscription.add(subscription);
    }

    @Override
    public void unsubscribe() {
        this.compositeSubscription.clear();
    }

    /**
     * 检查View是否被绑定
     *
     * @return
     */
    private boolean isViewBind() {
        return this.view == null ? false : true;
    }
}
