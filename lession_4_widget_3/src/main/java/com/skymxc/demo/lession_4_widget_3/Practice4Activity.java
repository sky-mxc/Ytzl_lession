package com.skymxc.demo.lession_4_widget_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Practice4Activity extends AppCompatActivity {

    private TextView text;
    private SeekBar sk0;
    private SeekBar sk1;
    private RatingBar rb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice4);
        sk0 = (SeekBar) findViewById(R.id.seek_bar);
        text = (TextView) findViewById(R.id.text);
        sk1 = (SeekBar) findViewById(R.id.seek_bar_0);
        rb = (RatingBar) findViewById(R.id.rating_bar);
        sk0.setOnSeekBarChangeListener(onSeekBarChangeListener);
        sk1.setOnSeekBarChangeListener(onSeekBarChangeListener);
        rb.setOnRatingBarChangeListener(onRatingBarChangeListener);
    }

    //RatingBar 改变监听
    private RatingBar.OnRatingBarChangeListener onRatingBarChangeListener = new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            if (fromUser){
                Toast.makeText(Practice4Activity.this,"当前等级："+rating+"，最大等级："+ratingBar.getNumStars(),Toast.LENGTH_SHORT).show();
            }
        }
    };

   private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
       /**
        *
         * @param seekBar 拖动的进度条
        * @param progress 进度
        * @param fromUser 是否是用户拖动
        */
       @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser){

                        text.setText("当前进度："+progress+",最大值："+seekBar.getMax());


            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            text.setVisibility(View.VISIBLE);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            text.setVisibility(View.INVISIBLE);
        }
    };


}
