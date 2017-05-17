package com.xingen.mvppractice.utils;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${新根} on 2017/5/16 0016.
 * blog: http://blog.csdn.net/hexingen
 */
public class SchedulerProvider {
    private static SchedulerProvider instance;
    private SchedulerProvider(){}
    public static  synchronized  SchedulerProvider getInstacne(){
        if(instance==null){
            instance=new SchedulerProvider();
        }
        return  instance;
    }

    /**
     * 提供ui更新的线程
     * @return
     */
    public Scheduler ui(){
        return AndroidSchedulers.mainThread();
    }

    /**
     * 提供计算的线程
     * @return
     */
    public Scheduler computation(){
        return Schedulers.computation();
    }

    /**
     * 提供io工作的线程
     * @return
     */
    public Scheduler io(){
        return  Schedulers.io();
    }
}
