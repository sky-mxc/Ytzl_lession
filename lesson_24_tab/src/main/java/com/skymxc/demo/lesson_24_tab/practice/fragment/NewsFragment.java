package com.skymxc.demo.lesson_24_tab.practice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.skymxc.demo.lesson_24_tab.R;
import com.squareup.picasso.Picasso;

/**
 * Created by sky-mxc
 * 滑动新闻
 */

public class NewsFragment extends Fragment {

    private View  view;
    private ImageView image;
    private Picasso picasso;
    public NewsFragment(){
        setArguments(new Bundle());
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       if (view == null){
           view = inflater.inflate(R.layout.layout_pracitve_fragment_new,null);
            image = (ImageView) view.findViewById(R.id.image);
           picasso = Picasso.with(getActivity());
       }
        String url = getArguments().getString("url");
        Log.e("NewsFragment","url:"+url);
        if (!TextUtils.isEmpty(url)){
            picasso.load(url)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.evi)
                    .into(image);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
