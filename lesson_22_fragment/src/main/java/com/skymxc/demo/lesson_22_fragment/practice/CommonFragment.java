package com.skymxc.demo.lesson_22_fragment.practice;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skymxc.demo.lesson_22_fragment.R;

/**
 * Created by sky-mxc
 * 中间通用的Fragment
 */

public class CommonFragment extends Fragment {

    private View v;

    private TextView tv;

    private static final String TAG ="practice.CommonFragment";



    public CommonFragment(){
        log("CommonFragment");
       setArguments(new Bundle());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        log("onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("onCreate()");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        log("onCreateView()");
        if (v==null){
            v = inflater.inflate(R.layout.layout_practice_fragment_common,null);
            tv= (TextView) v.findViewById(R.id.text);
            String text = getArguments().getString("msg");
            tv.setText(text);
        }
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        log("onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        log("onStart()");
        super.onStart();
    }



    @Override
    public void onResume() {
        log("onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        log("onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        log("onStop()");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        log("onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        log("onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        log("onDetach()");
        super.onDetach();
    }

    public void log(String text){
        Log.e(TAG,"==="+text+"==");
    }

    public void setText(){
//        tv.setText(null);
        String text = getArguments().getString("msg");
        Log.e("CommonFragment","=setText====="+text+"=====TextView:"+tv);

        if (tv!=null){
            tv.setText(text);
        }
    }


}
