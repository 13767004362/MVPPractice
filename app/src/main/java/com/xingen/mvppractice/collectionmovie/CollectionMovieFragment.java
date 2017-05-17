package com.xingen.mvppractice.collectionmovie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xingen.mvppractice.R;
import com.xingen.mvppractice.data.entity.MovieData;
import com.xingen.mvppractice.ui.base.BaseApplication;
import com.xingen.mvppractice.utils.ImageLoader;

import java.util.List;

/**
 * Created by ${新根} on 2017/5/14 0014.
 * blog: http://blog.csdn.net/hexingen
 */
public class CollectionMovieFragment extends Fragment implements CollectionMovieConstract.View,View.OnClickListener{
    public static final String TAG=CollectionMovieFragment.class.getSimpleName();
    public  static CollectionMovieFragment newInstance(){
        return  new CollectionMovieFragment();
    }
    private RecyclerView recyclerView;
    private View rootView;
    private CollectionMovieAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView=inflater.inflate(R.layout.fragment_collectionmovie,container,false);
        return this.rootView;
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(BaseApplication.getAppContext(),s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        this.presenter.subscribe();
    }

    private void initView() {
        this.recyclerView=(RecyclerView) this.rootView.findViewById(R.id.collectionmovie_recyclerView);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        this.adapter=new CollectionMovieAdapter(new ImageLoader(getActivity(),R.mipmap.ic_launcher));
        this.recyclerView.setAdapter(this.adapter);

        this.rootView.findViewById(R.id.collectionmovie_return_btn).setOnClickListener(this);
    }

    @Override
    public void onPause() {
        this.presenter.unsubscribe();
        super.onPause();
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    @Override
    public void loadCollectionMovie(List<MovieData> list) {
        this.adapter.upData(list);
    }
    private CollectionMovieConstract.Presenter presenter;
    @Override
    public void setPresenter(CollectionMovieConstract.Presenter presenter) {
                   this.presenter=presenter;
    }

    @Override
    public void onClick(View v) {
         getActivity().finish();
    }
}
