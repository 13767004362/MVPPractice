package com.xingen.mvppractice.movielist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingen.mvppractice.R;
import com.xingen.mvppractice.data.entity.Movie;
import com.xingen.mvppractice.ui.recyclerview.BaseRecyclerViewAdapter;
import com.xingen.mvppractice.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${新根} on 2017/5/14 0014.
 * blog: http://blog.csdn.net/hexingen
 */
public class MovieListAdapter extends BaseRecyclerViewAdapter<List<Movie>, MovieListAdapter.ViewHolder> {
    private List<Movie> moviesList;
    private List<Movie> moviesCollecion;
    private ImageLoader imageLoader;


    public MovieListAdapter(ImageLoader imageLoader) {
        this.moviesList = new ArrayList<>();
        this.moviesCollecion = new ArrayList<>();
        this.imageLoader = imageLoader;
    }

    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(parent.getContext(), R.layout.item_movielist, null);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.getCheckBox().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = viewHolder.getCheckBox().isChecked();
                Movie movie = moviesList.get(viewHolder.getLayoutPosition());
                if (check) {
                    moviesCollecion.add(movie);
                } else {
                    moviesCollecion.remove(movie);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieListAdapter.ViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        boolean isChecked = false;
        if (moviesCollecion.contains(movie)) {
            isChecked = true;
        }
        holder.getCheckBox().setChecked(isChecked);

        holder.getTitle().setText(movie.getTitle());
        holder.getYear().setText(movie.getYear() + "年");

        //加载图片
       this.imageLoader.loadImage(movie.getImages().getLarge(), holder.getNetworkImageView());
    }

    @Override
    public int getItemCount() {
        return this.moviesList.size();
    }

    @Override
    public void upData(List<Movie> list) {
        this.moviesList.clear();
        this.moviesList.addAll(list);
        this.notifyDataSetChanged();
    }

    public List<Movie> getMoviesCollecion() {
        return moviesCollecion;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title, year;
        private CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.item_movielist_iv);
            this.title = (TextView) itemView.findViewById(R.id.item_movielist_name_tv);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.item_movielist_cb);
            this.year = (TextView) itemView.findViewById(R.id.item_movielist_year_tv);
        }

        public ImageView getNetworkImageView() {
            return imageView;
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getYear() {
            return year;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }
    }
}
