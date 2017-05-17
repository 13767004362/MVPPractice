package com.xingen.mvppractice.ui.swiperefreshlayout;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：新根  on 2016/12/22.
 * <p>
 * 博客链接：http://blog.csdn.net/hexingen
 * 用途：支持非直接子类滚动视图
 */
public class ScrollChildSwipeRefreshLayout extends SwipeRefreshLayout {
    private View scrollUpChild;
    public ScrollChildSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置在哪个view中触发刷新。
     * @param view
     */
    public void setScrollUpChild(View view){
        this.scrollUpChild=view;
    }

    /**
     *ViewCompat..canScrollVertically（）：用于检查view是否可以在某个方向上垂直滑动
     * @return
     */
    @Override
    public boolean canChildScrollUp() {
        if(scrollUpChild!=null){
            return ViewCompat.canScrollVertically(scrollUpChild,-1);
        }
        return super.canChildScrollUp();
    }

}
