package com.skymxc.demo.lesson_23_viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by sky-mxc
 */

public class MyAdapter extends PagerAdapter {

    private int[] images;
    private Context context;

    public MyAdapter(int[] images, Context context) {
        this.images = images;
        this.context = context;
    }

    @Override
    public int getCount() {
        return images!=null?images.length:0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    /**
     * 实例化单项
     * @param container 容器
     * @param position 当前下标
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e("MyAdapter","========instantiateItem============"+position);
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(images[position]);
        //将试图添加到ImageView当中
        container.addView(imageView);
        return imageView;
    }

    /**
     *
     * @param container 当前的viewPager
     * @param position 第几项
     * @param object  要销毁的对象 与instantiateItem 返回的object对应
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.e("MyAdapter","==========destroyItem=========="+position);
        container.removeView((View) object);
    }
}
