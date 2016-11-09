package com.skymxc.lesson_44_media;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VVActivity extends AppCompatActivity {

    private VideoView vv ;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vv);
        vv= (VideoView) findViewById(R.id.vv);
        path = getIntent().getStringExtra("path");
        //设置播放进度控制器
        vv.setMediaController(new MediaController(this));
        vv.setVideoPath(path);
        vv.start();
        vv.setKeepScreenOn(true);//保存屏幕开启，需要权限
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vv.stopPlayback();
    }
}
