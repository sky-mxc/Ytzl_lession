package com.skymxc.demo.lesson_20_animator;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String  TAG ="MainActivity";

    private Button btValuesAnimator;
    private ValueAnimator valueAnimator;
    private Button btObjectAnimator;
    private ObjectAnimator objectAnimator;
    private Button btSet;
    private CheckBox cbTegether;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //============AnimatorSet
    //    initAnimatorSet();

        //===========ObjectAnimator
        initObjectAnimator();

        //===========valueAnimator
        initValueAnimator();


    }

    private void initView() {
        btValuesAnimator = (Button) findViewById(R.id.value_animator);
        btObjectAnimator = (Button) findViewById(R.id.object_animator);
        btSet =  (Button)findViewById(R.id.set_animator);
        cbTegether = (CheckBox) findViewById(R.id.tegether);
    }

    private void initAnimatorSet() {
        AnimatorSet set = new AnimatorSet();
        set.setDuration(6000);

//        PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat("translationX",0,300);
//        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY",0,200);
//        ObjectAnimator x = ObjectAnimator.ofPropertyValuesHolder(btSet,translationX);
//        ObjectAnimator y = ObjectAnimator.ofPropertyValuesHolder(btSet,translationY);

        ObjectAnimator x = ObjectAnimator.ofFloat(btSet,"translationX",0,300);
        ObjectAnimator y = ObjectAnimator.ofFloat(btSet,"translationY",0,200);
        if (cbTegether.isChecked()){
            set.playTogether(x,y);         //同时播放
        }else{
            set.playSequentially(x,y);     //序列播放
        }
    set.start();
    }

    private void initObjectAnimator() {
       // objectAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.animator_object);
        /**
         * 设置颜色
         */
//        objectAnimator = ObjectAnimator.ofInt(btObjectAnimator,"textColor", Color.YELLOW,Color.RED);
//        objectAnimator.setDuration(6000);

        /**
         * 移动
         */
        PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat("translationX",0,300);
        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY",0,200);

        objectAnimator = ObjectAnimator.ofPropertyValuesHolder(btObjectAnimator,translationX,translationY);
        objectAnimator.setDuration(6000);
    }

    private void initValueAnimator() {
        // valueAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(this,R.animator.animator_values);
        // valueAnimator = ValueAnimator.ofFloat(0,1);
        // valueAnimator = ValueAnimator.ofInt(Color.RED,Color.BLUE);

        PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofFloat("alpha",0.3f,0.7f);
        PropertyValuesHolder widthHolder = PropertyValuesHolder.ofInt("width",0,300);

        valueAnimator = ValueAnimator.ofPropertyValuesHolder(alphaHolder,widthHolder);
        valueAnimator.setDuration(10000);
        //   valueAnimator.setInterpolator( new CycleInterpolator(1));
        valueAnimator.addUpdateListener(updateLis);
    }

    private ValueAnimator.AnimatorUpdateListener updateLis = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {


          // int value = (int) valueAnimator.getAnimatedValue();
//            btValuesAnimator.setTranslationX(value);

         //   float value = (float) valueAnimator.getAnimatedValue();
         //   btValuesAnimator.setAlpha(value);
        //    btValuesAnimator.setTextColor(value);


            float value= (float) valueAnimator.getAnimatedValue("alpha");
            int width= (int) valueAnimator.getAnimatedValue("width");

            Log.e(TAG,"========value========"+value);
            Log.e(TAG,"========width========"+width);

            btValuesAnimator.setAlpha(value);
            btValuesAnimator.setRight(width);//
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.value_animator:
                valueAnimator.start();
                break;
            case R.id.object_animator:
                objectAnimator.setTarget(btObjectAnimator);
                objectAnimator.start();
                break;
            case R.id.set_animator:

                initAnimatorSet();

                break;
        }
    }
}
