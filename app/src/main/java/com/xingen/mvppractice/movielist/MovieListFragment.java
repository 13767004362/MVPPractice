package com.xingen.mvppractice.movielist;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xingen.mvppractice.R;
import com.xingen.mvppractice.data.entity.Movie;
import com.xingen.mvppractice.ui.base.BaseApplication;
import com.xingen.mvppractice.ui.recyclerview.BaseItemDecoration;
import com.xingen.mvppractice.ui.swiperefreshlayout.ScrollChildSwipeRefreshLayout;
import com.xingen.mvppractice.utils.ImageLoader;

import java.util.List;

/**
 * Created by ${新根} on 2017/5/13 0013.
 * blog: http://blog.csdn.net/hexingen
 */
public class MovieListFragment extends Fragment implements MovieListConstract.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private View rootView;
    private RecyclerView recyclerView;
    private MovieListAdapter adapter;
    private ScrollChildSwipeRefreshLayout swipeRefreshLayout;
    public static final String TAG = MovieListFragment.class.getSimpleName();
    public static MovieListFragment newInstance() {
        return new MovieListFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_movielist, container, false);
        return this.rootView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        this.presenter.subscribe();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.presenter.unsubscribe();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        this.recyclerView = (RecyclerView) this.rootView.findViewById(R.id.movielist_recyclerView);
        this.adapter = new MovieListAdapter(new ImageLoader(getActivity(),R.mipmap.ic_launcher));
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.addItemDecoration(new BaseItemDecoration(getActivity()));

        this.rootView.findViewById(R.id.movielist_collection_btn).setOnClickListener(this);

        swipeRefreshLayout = (ScrollChildSwipeRefreshLayout) rootView.findViewById(R.id.movielist_refreshLayout);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#263238"), Color.parseColor("#ffffff"), Color.parseColor("#455A64"));
        swipeRefreshLayout.setScrollUpChild(recyclerView);
        swipeRefreshLayout.setOnRefreshListener(this);

        //自动加载下拉提示框
        setLoadingIndicator(true);
        //以上代码不响应onRefresh()，需要手动响应onReFresh()。
        this.onRefresh();
    }

    /**
     * 控制SwipeRefreshLayout的显示与隐藏
     *
     * @param active
     */
    public void setLoadingIndicator(final boolean active) {

        if (swipeRefreshLayout == null) {
            return;
        }
        /**
         *     通过swipeRefreshLayout.post来调用swipeRefreshLayout.setRefreshing（）来实现，一进入页面就自动下拉提示窗。
         */
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                //确保布局加载完成后，调用
                swipeRefreshLayout.setRefreshing(active);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private MovieListConstract.Presenter presenter;

    @Override
    public void setPresenter(MovieListConstract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(BaseApplication.getAppContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMovieList(List<Movie> list) {
        this.adapter.upData(list);
        this.setLoadingIndicator(false);
    }

    @Override
    public void onClick(View v) {
        if (this.adapter.getMoviesCollecion().size() == 0) {
            showToast("请勾选中电影");
        } else {
            this.presenter.collectionMovie(this.adapter.getMoviesCollecion());
        }
    }
    @Override
    public void onRefresh() {
            swipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setLoadingIndicator(false);
                }
            }, 1000 * 2);

    }
}
