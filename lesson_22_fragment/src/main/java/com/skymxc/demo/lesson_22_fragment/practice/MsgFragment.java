package com.skymxc.demo.lesson_22_fragment.practice;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.skymxc.demo.lesson_22_fragment.R;

/**
 * Created by sky-mxc
 * 底部消息对应的Fragment
 */

public class MsgFragment extends Fragment {

    private static final String TAG ="practice.MsgFragment";

    private View view;
    private CommonFragment commonFragment ;
    private RadioGroup rg;

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

        if (view==null){
            view = inflater.inflate(R.layout.layout_practice_fragment_msg,null);
            commonFragment= (CommonFragment) getChildFragmentManager().findFragmentById(R.id.fragment_common);
        }


        rg = (RadioGroup) view.findViewById(R.id.menu_top_msg);
        setText(rg.getCheckedRadioButtonId());
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e("MsgFragment","======onCheckedChanged=========");
                setText(checkedId);
            }
        });
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

    private void setText(int checkedId) {
        switch (checkedId){
            case R.id.message:
                commonFragment.getArguments().putString("msg","消息界面中的消息面板");
                break;
            case R.id.phone:
                commonFragment.getArguments().putString("msg","消息界面中的电话界面");
                break;
        }
        commonFragment.setText();
    }

    public void log(String text){
        Log.e(TAG,"==="+text+"==");
    }
}
