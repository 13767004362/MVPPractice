package com.xingen.mvppractice.movielist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.xingen.mvppractice.R;
import com.xingen.mvppractice.collectionmovie.CollectionMovieActivity;
import com.xingen.mvppractice.data.source.local.MovieLocalSource;
import com.xingen.mvppractice.data.source.remote.RemoteDataSource;
import com.xingen.mvppractice.ui.base.BaseApplication;
import com.xingen.mvppractice.utils.SchedulerProvider;

public class MovieListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener{

    private  MovieListConstract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movielist);
        initView();
        MovieListFragment fragment=null;
        if(savedInstanceState!=null){
            fragment=(MovieListFragment) getSupportFragmentManager().findFragmentByTag(MovieListFragment.TAG);
        }else{
            fragment=MovieListFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.movielist_content_layout,fragment,MovieListFragment.TAG).commit();
        }
        this.presenter=new MovieListPresenter(fragment,MovieLocalSource.getInstance(BaseApplication.getAppContext(), SchedulerProvider.getInstacne()), RemoteDataSource.getInstance());

    }

    /**
     * 初始化控件
     */
    private void initView() {
        NavigationView navigationView=(NavigationView) this.findViewById(R.id.movielist_navigationview);
        FloatingActionButton floationActionButton=(FloatingActionButton) this.findViewById(R.id.movielist_floationActionBtn);

        floationActionButton.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.activity_movielist_drawer_collect://转调收藏电影的界面.
                Intent intent=new Intent(this, CollectionMovieActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_movielist_drawer_movielist:
                break;
        }
        //关闭抽屉菜单
        DrawerLayout drawerLayout=(DrawerLayout) this.findViewById(R.id.movielist_drawer);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        Snackbar.make(v,"MVP案例",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout=(DrawerLayout) this.findViewById(R.id.movielist_drawer);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){//按Back键，关闭抽屉菜单。
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}
