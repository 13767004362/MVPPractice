package com.xingen.mvppractice.ui.recyclerview;

import android.support.v7.widget.RecyclerView;

/**
 * Created by ${新根} on 2017/5/13 0013.
 * blog: http://blog.csdn.net/hexingen
 *
 * 用途：
 *  抽出一个共同的添加新数据的方法
 */
public abstract class BaseRecyclerViewAdapter<T ,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    public abstract  void upData(T t);
}
