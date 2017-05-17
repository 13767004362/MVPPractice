package com.xingen.mvppractice.movielist;

import com.xingen.mvppractice.BasePresenter;
import com.xingen.mvppractice.BaseView;
import com.xingen.mvppractice.data.entity.Movie;

import java.util.List;

/**
 * Created by ${新根} on 2017/5/13 0013.
 * blog: http://blog.csdn.net/hexingen
 */
public interface MovieListConstract {

    interface  Presenter extends BasePresenter {
        /**
         *  收藏的数据
         */
       void collectionMovie(List<Movie> list);
    }
    interface  View extends BaseView<Presenter> {
        /**
         *  加载从数据源中获取的数据
         */
          void loadMovieList(List<Movie> list);
          void showToast(String s);
    }
}
