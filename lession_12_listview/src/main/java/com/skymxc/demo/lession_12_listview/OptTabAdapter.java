package com.skymxc.demo.lession_12_listview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sky-mxc
 */
public class OptTabAdapter extends BaseAdapter {

    private List<Tab> tabs;
    private LayoutInflater lif;

    public OptTabAdapter(List<Tab> tabs, Context context) {
        this.tabs = tabs;
        this.lif = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        Log.e("Tag","===================");
        return tabs!=null? tabs.size():0;
    }

    @Override
    public Tab getItem(int i) {
        Log.e("Tag","===================");
        return tabs.get(i);
    }

    @Override
    public long getItemId(int i) {
        Log.e("Tag","===================");
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.e("Tag","===================");
        if (view ==null){
            view = lif.inflate(android.R.layout.simple_list_item_1,null);
        }
        ((TextView)view).setText(getItem(i).getCatname());
        return view;
    }


    public void addAll(List<Tab> data) {
    this.tabs.clear();
        tabs.addAll(data);
        notifyDataSetChanged();
    }
}
