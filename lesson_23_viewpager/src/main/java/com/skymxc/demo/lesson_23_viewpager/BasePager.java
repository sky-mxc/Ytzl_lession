package com.skymxc.demo.lesson_23_viewpager;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by sky-mxc
 */

public abstract class BasePager {

    protected View view;
    protected Context context;
    protected LayoutInflater inflater;

    public BasePager(Context context, @LayoutRes int layoutResId) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(layoutResId,null);

    }

    protected  View findViewById(@IdRes int id){
        return view.findViewById(id);
    }

    protected abstract  void initView();

    public View getView() {
        initView();
        return view;
    }
}
