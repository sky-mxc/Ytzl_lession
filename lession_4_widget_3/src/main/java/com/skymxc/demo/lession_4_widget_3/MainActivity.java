package com.skymxc.demo.lession_4_widget_3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //进度条
    private ProgressBar pb = null;

    //可拖动进度条
    private SeekBar sb = null;

    //显示进度值
    private TextView tv = null;

    //星星进度条
    private RatingBar rb  =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb= (ProgressBar) findViewById(R.id.progress);
        sb = (SeekBar) findViewById(R.id.seekbar);
        tv = (TextView) findViewById(R.id.text);
        rb= (RatingBar) findViewById(R.id.rating);

        pb.setMax(10);          //设置最大值
        pb.setProgress(1);      //设置进度值
        pb.setSecondaryProgress(3); //第二进度值
        pb.setIndeterminate(false);      //设置进度条为不明确进度值  没有具体的值 会一直循环

        //设置进度改变监听
        sb.setOnSeekBarChangeListener(changeListener );

        rb.setNumStars(8);       //几颗星
        rb.setRating(3.1F);       //设置等级
        rb.setIsIndicator(false);//设置是不是指示器
        rb.setStepSize(1);      //设置 步进 就是拖一下进多少
        rb.setOnRatingBarChangeListener(ratingBarChangeListener);  //设置进度改变监听
    }

    //星级进度值改变监听
    private RatingBar.OnRatingBarChangeListener ratingBarChangeListener = new RatingBar.OnRatingBarChangeListener() {

        /**
         *
         * @param ratingBar 进度条
         * @param rating 进度值
         * @param fromUser 是否来自用户操作
         */
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            Log.i("Tag","用户触摸："+fromUser+";进度值："+rating);
        }
    };

    //可拖动进度条的 进度改变监听事件
    private  SeekBar.OnSeekBarChangeListener changeListener = new SeekBar.OnSeekBarChangeListener() {

        /**
         * 进度改变
         * @param seekBar 改变的进度条
         * @param progress 改变后的进度值
         * @param fromUser 当前这次触发 是不是来自用户的操作
         */
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.i("Tag"," 改变进度值："+progress);
            tv.setText(progress*10+"%");
        }

        /**
         *你的手触摸到进度条的时候触发 在进度改变之前触发
         * @param seekBar 触发的 进度条
         */
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            Log.i("Tag"," 触摸进度条");

            tv.setVisibility(View.VISIBLE);
        }

        /**
         * 停止拖动后的调用
         * @param seekBar 触摸的进度条
         */
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Log.i("Tag"," 不触摸进度条了");
            tv.setVisibility(View.GONE);
        }
    };

    /**
     * 点击事件
     * @param v 按钮
     */
    public void click(View v){
        switch (v.getId()){
            case R.id.add:
                //增加
                pb.setProgress(pb.getProgress()+1);
                pb.setSecondaryProgress(pb.getProgress()+1);

                Log.e("Tag","SecondaryProgress:"+pb.getSecondaryProgress());
                break;
            case R.id.sub:
                //减少
                pb.setProgress(pb.getProgress()-1);
                pb.setSecondaryProgress(pb.getProgress()+1);
                Log.e("Tag","SecondaryProgress:"+pb.getSecondaryProgress());
                break;
        }
    }
}
