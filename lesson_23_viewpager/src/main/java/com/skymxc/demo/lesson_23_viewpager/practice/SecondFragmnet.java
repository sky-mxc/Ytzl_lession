package com.skymxc.demo.lesson_23_viewpager.practice;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skymxc.demo.lesson_23_viewpager.R;

/**
 * Created by sky-mxc
 */

public class SecondFragmnet extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_second_fragment,null);
        return view;
    }
}
