package com.skymxc.demo.lesson_32_desgin_widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.skymxc.demo.lesson_32_desgin_widget.entity.Item;

import java.util.List;

/**
 * Created by sky-mxc
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "MyAdapter";

    private List<Item> items;
    private LayoutInflater lif;
    private Context mContext;
    private View.OnClickListener onClickListener;


    public MyAdapter(Context context, List<Item> items) {
        this.mContext = context;
        this.items = items;
        lif = LayoutInflater.from(mContext);
    }

    /**
     *根据类型创建ViewHolder
     * @param parent RecycleView
     * @param viewType 视图类型
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder ;
        if (viewType == 1){
            //头
            View v = lif.inflate(R.layout.layout_title,null);
            //24版本之前 不能再全局创建 必须每次 new  25版本可以
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            v.setLayoutParams(lp);
            holder = new TitleHolder(v);
        }else{
            holder=new ItemHolder(lif.inflate(R.layout.layout_item,null));
        }
        return holder;
    }



    /**
     * 绑定数据视图
     * @param holder 当前加载的holder
     * @param position 当前holder的下标
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = items.get(position);

        if (holder instanceof TitleHolder){
            TitleHolder th  = (TitleHolder) holder;
            th.tv.setText(item.getText1());
            th.tv2.setText(item.getText2());
        }else if (holder instanceof  ItemHolder){
            ItemHolder ih = (ItemHolder) holder;
            ih.text1.setText(item.getText1());
            ih.text2.setText(item.getText2());
            //获取资源的id ：mContext.getResources().getIdentifier()
            ih.icon.setImageResource(mContext.getResources().getIdentifier(
                    item.getIcon(),     //资源名 ，不带后缀
                    "mipmap",           //资源所在的文件类型名（文件夹）
                    mContext.getPackageName()  //包名
            ));

            if (item.getType() ==2){    //带有进度值
                ih.pb.setVisibility(View.VISIBLE);
                ih.pb.setProgress(item.getProgress());
            }else{
                ih.pb.setVisibility(View.GONE);
            }

        }


    }

    @Override
    public int getItemCount() {
        return items!=null?items.size():0;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        TextView text1;
        TextView text2;
        ImageView icon;
        ProgressBar pb;
        public ItemHolder(View itemView) {
            super(itemView);//必须有
            text1 = (TextView) itemView.findViewById(R.id.text1);
            text2 = (TextView) itemView.findViewById(R.id.text2);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            pb = (ProgressBar) itemView.findViewById(R.id.progress);
            //设置点击事件
            this.itemView.setOnClickListener(onClickListener);
        }

    }

    class TitleHolder extends RecyclerView.ViewHolder{
        TextView tv;
        TextView tv2;

        public TitleHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.text1);
            tv2 = (TextView) itemView.findViewById(R.id.text2);
        }
    }
}
