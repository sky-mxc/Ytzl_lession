package com.skymxc.demo.lession_12_practice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sky-mxc
 */
public class MyAdapter extends BaseAdapter {

    public MyAdapter(List<Programme> programmes, Context context) {
        this.programmes = programmes;
        this.lif = LayoutInflater.from(context);
    }

    private List<Programme> programmes;

    private LayoutInflater lif;
    @Override
    public int getCount() {
        return programmes==null?0:programmes.size();
    }

    @Override
    public Programme getItem(int i) {
        return programmes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getId();
    }

    /**
     *
     * @param i
     * @param view
     * @param viewGroup 要设置给的View
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = lif.inflate(R.layout.layout_programme_item,null);

        TextView tvPhoto = (TextView) v.findViewById(R.id.photo);
        TextView tvTitle = (TextView) v.findViewById(R.id.title);
        TextView tvSummary = (TextView) v.findViewById(R.id.summary);
        Programme programme=  getItem(i);
        tvTitle.setText(programme.getTitle());
        tvPhoto.setText(programme.getThumbnail());
        tvSummary.setText(programme.getForm());

        return v;
    }
}
