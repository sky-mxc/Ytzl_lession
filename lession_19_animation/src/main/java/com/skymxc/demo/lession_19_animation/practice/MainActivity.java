package com.skymxc.demo.lession_19_animation.practice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.skymxc.demo.lession_19_animation.R;

/**
 * Created by sky-mxc
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView image;
    private LinearLayout root;
    private TranslateAnimation translateAnimation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_main);
        image = (ImageView) findViewById(R.id.image);
        root = (LinearLayout) findViewById(R.id.root);
        translateAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(this,R.anim.anim_p1_shake);
        translateAnimation.setInterpolator(new CycleInterpolator(0.5f));
        translateAnimation.setRepeatCount(2);
        translateAnimation.setRepeatMode(Animation.REVERSE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shake:
                view.startAnimation(translateAnimation);
                break;
            case R.id.translate:
                TranslateAnimation translateLeft = (TranslateAnimation) AnimationUtils.loadAnimation(this,R.anim.anim_p1_translate_l);
                translateLeft.setFillAfter(true);
                translateLeft.setAnimationListener(listenterV);
                image.startAnimation(translateLeft);
                break;
            case R.id.image:
                view.startAnimation(translateAnimation);
                break;
        }
    }

    private  Animation.AnimationListener listenterV= new Animation.AnimationListener(){

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) image.getLayoutParams();
            params.leftMargin=image.getWidth()*2;

            image.setLayoutParams(params);
            image.clearAnimation();
            TranslateAnimation translateAnimationH = (TranslateAnimation) AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_p1_tanslate_h);
            translateAnimationH.setFillAfter(true);
            image.startAnimation(translateAnimationH);
            translateAnimationH.setAnimationListener(listenterH);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
    private  Animation.AnimationListener listenterH= new Animation.AnimationListener(){

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) image.getLayoutParams();
            params.topMargin=image.getHeight()*2;

            image.setLayoutParams(params);
            image.clearAnimation();

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
}
