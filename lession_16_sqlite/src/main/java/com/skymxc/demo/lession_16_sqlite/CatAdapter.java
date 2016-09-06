package com.skymxc.demo.lession_16_sqlite;

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
public class CatAdapter extends BaseAdapter {
    private  List<Cat> cats;
    private LayoutInflater lif ;
    public CatAdapter(Context context,List<Cat> cats){
        this.cats =cats;
        lif = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return cats==null?0:cats.size();
    }

    @Override
    public Cat getItem(int i) {
        return cats.get(i);
    }

    @Override
    public long getItemId(int i) {
        return cats.get(i).getCatid();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.e("Tag","==============getView================");
        TextView tv = (TextView) lif.inflate(android.R.layout.simple_list_item_1,null);
        Cat cat = getItem(i);
        tv.setText(cat.getName());
        Log.e("Tag","Name:"+cat.getName());
        return tv;
    }

    public void clear(){
        if (cats!=null){
            cats.clear();
        }

        notifyDataSetChanged();
    }

    public void setCats(List<Cat> cats){
        this.cats.clear();
        Log.e("Tag","beforeSize:"+this.cats.size());
        this.cats.addAll(cats);
        Log.e("Tag","behindSize:"+this.cats.size());
        notifyDataSetChanged();
    }
}
