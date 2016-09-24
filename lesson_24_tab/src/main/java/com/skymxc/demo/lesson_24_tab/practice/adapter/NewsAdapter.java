package com.skymxc.demo.lesson_24_tab.practice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skymxc.demo.lesson_24_tab.R;
import com.skymxc.demo.lesson_24_tab.practice.entity.News;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sky-mxc
 */

public class NewsAdapter extends BaseAdapter {
    private List<News> newses;
    private LayoutInflater inflater;
    private Picasso picasso;

    public NewsAdapter(Context context,List<News> newses){
        this.newses = newses;
        inflater = LayoutInflater.from(context);
        picasso = Picasso.with(context);
    }

    @Override
    public int getCount() {
        return newses==null?0:newses.size();
    }

    @Override
    public News getItem(int position) {
        return newses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return newses.get(position).getContentid();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView ==null){
            convertView = inflater.inflate(R.layout.layout_practice_list_item,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        News news = getItem(position);
        holder.tvDescribe.setText(news.getDescription());
        holder.tvTitle.setText(news.getTitle());
        picasso.load(news.getThumb())
                .placeholder(R.mipmap.evi)
                .error(R.mipmap.ic_launcher)
                .into(holder.icon);
        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView tvTitle;
        TextView tvDescribe;
        public ViewHolder(View view){
            icon = (ImageView) view.findViewById(R.id.icon);
            tvTitle = (TextView) view.findViewById(R.id.title);
            tvDescribe = (TextView) view.findViewById(R.id.describe);
        }
    }
}
