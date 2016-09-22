package com.skymxc.demo.lesson_23_viewpager.practice;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skymxc.demo.lesson_23_viewpager.R;
import com.squareup.picasso.Picasso;

/**
 * Created by sky-mxc
 */

public class NewsFragment extends Fragment {

    private View view ;
    private ImageView imageView;
    private TextView tvTitle;
    private Picasso picasso;

    public NewsFragment (){
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view ==null){
           view= inflater.inflate(R.layout.layout_fragment_new,null);
            imageView = (ImageView) view.findViewById(R.id.image);
            tvTitle = (TextView) view.findViewById(R.id.title);
            picasso = Picasso.with(getActivity());
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //TODO get imageUrl,title
        String url=getArguments().getString("url");
        String title = getArguments().getString("title");
        Log.e("NewsFragment","=========url:"+url);
        Log.e("NewsFragment","=========title:"+title);

        picasso.load(url)
                .placeholder(R.mipmap.a_5)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
        tvTitle.setText(title);
    }
}
