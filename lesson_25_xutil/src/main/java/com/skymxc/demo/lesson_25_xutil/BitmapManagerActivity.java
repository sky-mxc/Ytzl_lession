package com.skymxc.demo.lesson_25_xutil;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

@ContentView(R.layout.activity_bitmap_manager)
public class BitmapManagerActivity extends AppCompatActivity {

    @ViewInject(R.id.image)
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Event(value = {R.id.load_drawable,R.id.load_file,R.id.bind})
    private  void click(View v){
        switch (v.getId()){
            case R.id.load_drawable:
                ImageOptions io = new ImageOptions.Builder()
                        .setFadeIn(true)
                        .setFailureDrawableId(R.mipmap.ic_launcher)
                        .setSize(200,200)
                        .setUseMemCache(true)
                        .build();
                x.image().loadDrawable("assets://android.jpeg", io, new Callback.CommonCallback<Drawable>() {
                    @Override
                    public void onSuccess(Drawable drawable) {
                        image.setImageDrawable(drawable);
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {

                    }

                    @Override
                    public void onCancelled(CancelledException e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                break;
            case R.id.load_file:
              //  x.image().loadFile();
                String path = Environment.getExternalStorageDirectory().getPath()+"/Download/dog.jpg";
                ImageOptions ios = new ImageOptions.Builder()
                        .build();
                x.image().loadFile(path,ios, new Callback.CacheCallback<File>() {
                    @Override
                    public boolean onCache(File file) {
                        return false;
                    }

                    @Override
                    public void onSuccess(File file) {

                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {

                    }

                    @Override
                    public void onCancelled(CancelledException e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                break;
            case R.id.bind:
              //  x.image().bind(image,"assets://android.jpeg");
                x.image().bind(image,"assets://gif.gif");
                break;
        }
    }
}
