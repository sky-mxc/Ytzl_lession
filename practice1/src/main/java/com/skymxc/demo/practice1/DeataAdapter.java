package com.skymxc.demo.practice1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.skymxc.demo.practice1.model.DataItem;

import java.util.List;

/**
 * Created by sky-mxc
 */
public class DeataAdapter extends BaseAdapter {
    private DataItem item;
    private List<DataItem> datas;
    private LayoutInflater lif ;
    private bindView bind;
    private View.OnClickListener onClickListener;
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    public DeataAdapter(Context context,List<DataItem> items){
        this.datas =items;
        this.lif= LayoutInflater.from(context);
    }

    public void setBind(bindView bind) {
        this.bind = bind;
    }



    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public DataItem getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder ;
        if (view==null){
            view= lif.inflate(R.layout.layout_listview_data_item,null);
            holder = new ViewHolder(view);
            view.setTag(holder);

        }else{
            holder= (ViewHolder) view.getTag();
        }
        item = getItem(i);
//        holder.cbChoose.setOnCheckedChangeListener(onCheckedChangeListener);
//        holder.cbChoose.setTag(item);
//        holder.btDel.setOnClickListener(onClickListener);
//        holder.btDel.setTag(item);
//        holder.tvTitle.setText(item.title);
//        holder.tvDesc.setText(item.desc);
        bind.bind(holder,item);
        return view;
    }
    class  ViewHolder {
        TextView tvTitle;
        TextView tvDesc;
        Button btDel;
        CheckBox cbChoose;
        public ViewHolder(View view){
            tvTitle = (TextView) view.findViewById(R.id.title);
            tvDesc = (TextView) view.findViewById(R.id.desc);
            btDel = (Button) view.findViewById(R.id.del);
            cbChoose = (CheckBox) view.findViewById(R.id.cb_choose);
        }
    }

    public interface  bindView{
        void bind(ViewHolder holder,DataItem item);
    }



}


