package com.skymxc.demo.lession_4_widget_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.SeekBar;

public class Practice0Activity extends AppCompatActivity {

    //进度条
    private ProgressBar pb = null;

    //可拖动进度条
    private SeekBar sb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice0);

        pb = (ProgressBar) findViewById(R.id.progress);
        sb = (SeekBar) findViewById(R.id.seekbar);

        //拖动监听
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pb.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
