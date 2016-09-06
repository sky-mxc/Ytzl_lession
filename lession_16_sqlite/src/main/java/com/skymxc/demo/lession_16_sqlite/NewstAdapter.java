package com.skymxc.demo.lession_16_sqlite;

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
public class NewstAdapter extends BaseAdapter {
    private List<News> list ;
    private LayoutInflater lif ;

    public NewstAdapter(Context context ,List<News> list) {
        this.list= list;
        lif = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list ==null?0:list.size();
    }

    @Override
    public News getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) list.get(i).getContentid();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //TODO 加载视图 绑定视图和数据
        View v = lif.inflate(R.layout.layout_news_item,null);
        TextView tvTitle = (TextView) v.findViewById(R.id.title);
        TextView tvIcon = (TextView) v.findViewById(R.id.icon);
        TextView tvContent = (TextView) v.findViewById(R.id.content);
        News news = getItem(i);
        tvTitle.setText(news.getTitle());
        tvIcon .setText(news.getThumb());
        tvContent.setText(news.getDescription());
        return v;
    }

    public void clear(){
        //TODO 清楚数据
        list.clear();
        notifyDataSetChanged();

    }

    /**
     * 跟换数据源
     * @param list
     */
   public void setList(List<News> list){
      this.list.clear();
       this.list.addAll(list);
       notifyDataSetChanged();
   }

}
