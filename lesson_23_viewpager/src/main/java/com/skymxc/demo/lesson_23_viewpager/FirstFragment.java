package com.skymxc.demo.lesson_23_viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by sky-mxc
 */

public class FirstFragment extends Fragment {

    private View v ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (v == null){
            v = new Button(getContext());
        }
        v.setBackgroundColor(getArguments().getInt("color"));
        Log.e("FirstFragment","=====onCreateView==========="+getArguments().getString("pager"));
        return v;
    }

    public static FirstFragment getFragment(int color,String pager){
        FirstFragment fragment = new FirstFragment();
        Bundle b = new Bundle();
        b.putInt("color",color);
        b.putString("pager",pager);
        fragment.setArguments(b);
        return  fragment;
    }
}
