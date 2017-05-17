package com.xingen.mvppractice.collectionmovie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xingen.mvppractice.R;
import com.xingen.mvppractice.data.source.local.MovieLocalSource;
import com.xingen.mvppractice.ui.base.BaseApplication;
import com.xingen.mvppractice.utils.SchedulerProvider;

/**
 * Created by ${新根} on 2017/5/13 0013.
 * blog: http://blog.csdn.net/hexingen
 */
public class CollectionMovieActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collectionmovie);

        CollectionMovieFragment fragment=CollectionMovieFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.collectionmovie_content_layout,fragment,CollectionMovieFragment.TAG).commit();

        CollectionMovieConstract.Presenter presenter=new CollectionMoviePresenter(fragment,MovieLocalSource.getInstance(BaseApplication.getAppContext(), SchedulerProvider.getInstacne()));
    }
}
