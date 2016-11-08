package com.skymxc.lesson_43_fresco;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.drawee_view) SimpleDraweeView simpleDraweeView ;

    @BindArray(R.array.str_ary)
    public String[] strArray;

    @BindString(R.string.app_name)
    public String appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        GenericDraweeHierarchy gdh = new GenericDraweeHierarchyBuilder(getResources())
                .setPlaceholderImage(R.mipmap.gpi, ScalingUtils.ScaleType.CENTER)
                .setFadeDuration(1000)
                .build();
        simpleDraweeView.setHierarchy(gdh);


    }


    @OnClick(value = {R.id.load,R.id.drawee_view})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.load:
               // simpleDraweeView.setImageURI("https://sky-mxc.github.io/images/top.jpg");
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri("http://img3.yxlady.com/yl/UploadFiles_5361/20141031/20141031110337140.gif")
                        .setTapToRetryEnabled(true)     //设置 点击重新加载可用
                        .setOldController(simpleDraweeView.getController())
                        .setControllerListener(listener)        //设置加载控制监听
                        .setAutoPlayAnimations(false)        //加载完动图 后自动播放图片
                        .build();
                simpleDraweeView.setController(controller);
                break;
        }
    }

    private ControllerListener listener = new ControllerListener() {
        @Override
        public void onSubmit(String id, Object callerContext) {
            Log.i(TAG, "onSubmit: ");
        }

        @Override
        public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
            Log.i(TAG, "onFinalImageSet: ");
            if (animatable !=null){
                animatable.start();
            }
        }

        @Override
        public void onIntermediateImageSet(String id, Object imageInfo) {
            Log.i(TAG, "onIntermediateImageSet: ");
        }

        @Override
        public void onIntermediateImageFailed(String id, Throwable throwable) {
            Log.i(TAG, "onIntermediateImageFailed: ");
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
            Log.i(TAG, "onFailure: ");
        }

        @Override
        public void onRelease(String id) {
            Log.i(TAG, "onRelease: ");
        }
    };
}
