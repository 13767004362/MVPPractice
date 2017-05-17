package com.xingen.mvppractice.collectionmovie;

import com.xingen.mvppractice.data.entity.MovieData;
import com.xingen.mvppractice.data.source.local.LocalDataSource;
import com.xingen.mvppractice.utils.SchedulerProvider;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ${新根} on 2017/5/14 0014.
 * blog: http://blog.csdn.net/hexingen
 */
public class CollectionMoviePresenter implements CollectionMovieConstract.Presenter {
    private CollectionMovieConstract.View view;
    private LocalDataSource<MovieData> localDataSource;
    private SchedulerProvider schedulerProvider;
    private CompositeSubscription compositeSubscription;

    public CollectionMoviePresenter(CollectionMovieConstract.View view, LocalDataSource<MovieData> localDataSource) {
        this.schedulerProvider = SchedulerProvider.getInstacne();
        this.localDataSource = localDataSource;
        this.view = view;

        this.view.setPresenter(this);
        this.compositeSubscription = new CompositeSubscription();
    }


    @Override
    public void subscribe() {
        queryCollection();
    }

    @Override
    public void unsubscribe() {
        this.compositeSubscription.clear();
    }

    /**
     * 查询收藏的电影
     */
    public void queryCollection() {
        Subscription subscription = this.localDataSource.queryAll()
                .observeOn(this.schedulerProvider.ui()
                ).subscribe(new Subscriber<List<MovieData>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        view.showToast("查询异常");
                    }

                    @Override
                    public void onNext(List<MovieData> list) {
                        if (list != null) {
                            if (list.size() > 0) {
                                view.loadCollectionMovie(list);
                            } else {
                                view.showToast("无收藏的电影");
                            }
                        }
                    }
                });
        this.compositeSubscription.add(subscription);
    }


}
