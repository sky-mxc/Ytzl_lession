package com.skymxc.demo.lesson_21_activeandroid_db.practice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skymxc.demo.lesson_21_activeandroid_db.R;
import com.skymxc.demo.lesson_21_activeandroid_db.practice.entity.Score;

import java.util.List;

/**
 * Created by sky-mxc
 */
public class ScoreAdapter extends BaseAdapter {
    private List<Score> scores;
    private LayoutInflater lif ;

    public ScoreAdapter (Context context,List<Score> scores){
        this.scores= scores;
        lif = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return scores==null?0:scores.size();
    }

    @Override
    public Score getItem(int i) {
        return scores.get(i);
    }

    @Override
    public long getItemId(int i) {
        return scores.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view==null){
            view= lif.inflate(R.layout.layout_practice_list_item,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        Score score = getItem(i);

        holder.tvName.setText(score.getName());
        holder.tvGrade.setText(score.getGrade());
      holder.tvHtml.setText(score.getHtml()+"");
        holder.tvJava.setText(score.getJava()+"");
       holder.tvSSH.setText(score.getSsh()+"");
        return view;
    }

    public void addAll(List<Score> scores){
        this.scores.clear();
        this.scores.addAll(scores);
        notifyDataSetChanged();
    }

    class  ViewHolder{
        TextView tvName ;
        TextView tvJava ;
        TextView tvSSH ;
        TextView tvHtml ;
        TextView tvGrade ;

        public ViewHolder(View view){
            tvName = (TextView) view.findViewById(R.id.name);
            tvJava = (TextView) view.findViewById(R.id.java);
            tvSSH = (TextView) view.findViewById(R.id.ssh);
            tvHtml = (TextView) view.findViewById(R.id.html);
            tvGrade = (TextView) view.findViewById(R.id.grade);
        }

    }
}
