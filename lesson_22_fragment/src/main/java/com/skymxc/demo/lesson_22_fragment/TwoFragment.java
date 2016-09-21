package com.skymxc.demo.lesson_22_fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sky-mxc
 */
public class TwoFragment extends Fragment {

    private static final String TAG ="TwoFragment";
    private View v;

    private TextView tv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (v==null){
            v=inflater.inflate(R.layout.fragment_two,null);
            tv = (TextView) v.findViewById(R.id.text);
        }
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG,"=======onAttach()===========");
    }

    public void setText(String str) {
        tv.setText(str);
    }
}
