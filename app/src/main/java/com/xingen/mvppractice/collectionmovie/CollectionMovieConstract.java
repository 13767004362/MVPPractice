package com.xingen.mvppractice.collectionmovie;

import com.xingen.mvppractice.BasePresenter;
import com.xingen.mvppractice.BaseView;
import com.xingen.mvppractice.data.entity.MovieData;

import java.util.List;

/**
 * Created by ${新根} on 2017/5/14 0014.
 * blog: http://blog.csdn.net/hexingen
 */
public interface CollectionMovieConstract {
    interface View extends BaseView<Presenter> {
        void loadCollectionMovie(List<MovieData> list);
        void showToast(String s);
    }
    interface Presenter extends BasePresenter {
    }
}
