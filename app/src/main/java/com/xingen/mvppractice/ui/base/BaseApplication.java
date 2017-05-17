package com.xingen.mvppractice.ui.base;

import android.app.Application;

/**
 * Created by ${新根} on 2017/5/13 0013.
 * blog: http://blog.csdn.net/hexingen
 */
public class BaseApplication extends Application {
    private static BaseApplication appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext=this;
    }

    public static BaseApplication getAppContext() {
        return appContext;
    }
}
