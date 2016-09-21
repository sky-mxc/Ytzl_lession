package com.skymxc.demo.lesson_22_fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by sky-mxc
 */

public class OneFragment extends Fragment {

    private View v;
    private static final String TAG ="OneFragment";

    private Button bt;
    private EditText edit;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG,"======onAttach()======");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e(TAG,"======onCreate()======");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (v == null){
            v = inflater.inflate(R.layout.fragment_one,null);
            bt = (Button) v.findViewById(R.id.bt);
            edit = (EditText) v.findViewById(R.id.edit);
            bt.setOnClickListener(listener);
        }
        return  v;
    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str  = edit.getText().toString();
          MainActivity activity= (MainActivity) getActivity();  //获取到宿主 ；；
            //调用宿主的方法判断屏幕 方向
            if (activity.isLand()){
               TwoFragment twoFragment= (TwoFragment) getFragmentManager().findFragmentById(R.id.fragment_two);
                twoFragment.setText(str);
            }
        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.e(TAG,"======onActivityCreated()======");
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG,"======onStart()======");
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG,"======onResume()======");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG,"======onPause()======");
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG,"======onStop()======");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG,"======onDestroyView()======");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"======onDestroy()======");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG,"======onDetach()======");
    }
}
