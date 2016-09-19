package com.skymxc.demo.lession_19_animation;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView text ;
    private AlphaAnimation alphaAnimation;
    private RotateAnimation rotateAnimation;
    private ScaleAnimation scaleAnimation;
    private TranslateAnimation translateAnimation;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text  = (TextView) findViewById(R.id.text);
        image = (ImageView) findViewById(R.id.image);
        image.setClickable(true);
        image.setOnClickListener(this);
        initAlphaAnimation();
        initRotateAnimation();
        initScaleAnimation();
        translateAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(this,R.anim.anim_translate);
        translateAnimation.setRepeatCount(2);
        translateAnimation.setRepeatMode(Animation.REVERSE);

    }

    private void initScaleAnimation() {
        scaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        // scaleAnimation.setFillAfter(true);
    }


    private void initRotateAnimation() {
        //   rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(this,R.anim.anim_rotate);

        /**
         * 代码方式
         */
        rotateAnimation = new RotateAnimation(0,360, Animation.RELATIVE_TO_PARENT,0.3f,Animation.RELATIVE_TO_PARENT,0.5f);
        rotateAnimation.setDuration(2000);
    }

    private void initAlphaAnimation() {
        alphaAnimation= (AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        alphaAnimation.setRepeatCount(3);   //重复次数
        alphaAnimation.setRepeatMode(Animation.REVERSE);    //重复模式
        alphaAnimation.setFillAfter(true);              //是否停留在最后一针
      //  alphaAnimation.setFillBefore(true);         //停留在第一针
        alphaAnimation.setInterpolator(new AccelerateInterpolator(2));           //改变运动的速度  差值器  Acc
        /**
         * 代码方式创建动画
         */
//        alphaAnimation = new AlphaAnimation(0,0.6f);
//        alphaAnimation.setDuration(2000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.text:
                ((AnimationDrawable)view.getBackground()).start();
                break;
            case R.id.anim_alpha:
                view.startAnimation(alphaAnimation);
                break;
            case R.id.anim_rotate:
                view.startAnimation(rotateAnimation);
                break;
            case R.id.anim_scale:
                view.startAnimation(scaleAnimation);
                break;
            case R.id.anim_translate:
                view.startAnimation(translateAnimation);
                break;
            case R.id.to_list:
                Intent in = new Intent(MainActivity.this,LayoutAnimationActivity.class);
                startActivity(in);
                //activity跳转动画  在startactivity或finish方法之后调用
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);

                break;
            case R.id.to_transition:
                in= new Intent(MainActivity.this, ayoutTransitionActivity.class);
                startActivity(in);
                break;
            case R.id.image:
                Drawable drawable = image.getBackground();
               //
                // AnimatedVectorDrawable v =
                break;
        }
    }
}
