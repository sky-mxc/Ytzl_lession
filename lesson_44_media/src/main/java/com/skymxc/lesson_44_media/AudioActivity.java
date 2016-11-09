package com.skymxc.lesson_44_media;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import java.io.IOException;

/**
 * Created by sky-mxc
 */
public class AudioActivity extends AppCompatActivity{
    private static final String TAG = "AudioActivity";
    private ToggleButton tbPlay;
    private SeekBar sbProgress;
    private String path;
    private MediaPlayer player;
    private boolean pause;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        tbPlay = (ToggleButton) findViewById(R.id.play);
        sbProgress = (SeekBar) findViewById(R.id.progress);
        path = getIntent().getStringExtra("path");
        sbProgress.setOnSeekBarChangeListener(onSeekBarChangeListener);
//        Log.i(TAG, "onCreate: path="+path);
        if (TextUtils.isEmpty(path)){
            finish();
            return;
        }
        initMedia();
    }



    public void onClick(View v){
        if (player !=null && player.isPlaying()) {
            player.stop();
            tbPlay.setOnCheckedChangeListener(null);
            tbPlay.setChecked(false);
            tbPlay.setOnCheckedChangeListener(onCheckedChangeListener);
        }
    }


    private void initMedia(){
        Log.i(TAG, "initMedia: ");
        try {
            if (player == null) {
                player = new MediaPlayer();
            }else{
                player.reset();
            }
            //设置要播放的资源
            player.setDataSource(path);

            //设置异步准备 完成监听
            player.setOnPreparedListener(onPreparedListener);

//            player.setOnCompletionListener();   //播放完成监听
//            player.setOnBufferingUpdateListener() ;     //缓冲更细监听

            //准备播放
//        player.prepare();   //同步准备
            player.prepareAsync();  //异步准备 完成后 会触发完成监听
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if(b) { //只有当时用户改变时才跳转
                //跳转到 某一时间
                player.seekTo(i);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            player.pause();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
                player.start();
        }
    };

    private MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            Log.i(TAG, "onPrepared: ");
            player.start();    //开始播放
            handler.post(updateProgress);
            tbPlay.setEnabled(true);
            tbPlay.setChecked(true);
            tbPlay.setOnCheckedChangeListener(onCheckedChangeListener);
            sbProgress.setMax(mediaPlayer.getDuration());
            }
    };

    private Runnable updateProgress = new Runnable() {
        @Override
        public void run() {
            //获取当前播放时间
            int current = player.getCurrentPosition();
            //总时长
            int duration = player.getDuration();
            sbProgress.setProgress(current);
            handler.postDelayed(this,1000);
        }
    };

   private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b){
                //play
               if (pause){
                pause = false;

                   player.start();
               }else{
                   initMedia();
               }
            }else{
                //pause
                   pause = true;
                player.pause();
            }
        }
    };
}
