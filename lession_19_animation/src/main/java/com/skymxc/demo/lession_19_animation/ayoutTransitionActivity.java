package com.skymxc.demo.lession_19_animation;

import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

public class ayoutTransitionActivity extends AppCompatActivity implements View.OnClickListener {

    private GridLayout gl;
    private int i;
    LayoutTransition transition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayout_transition);
        gl = (GridLayout) findViewById(R.id.gridlayout);
         transition = new LayoutTransition();
        transition.setDuration(1000);
        setChangeAppearing(transition);
        setChangeAppearing(transition);
        setChangeDisAppearing(transition);

    }

    /**
     * 位置改变
     * @param transition
     */
    private void setChangeAppearing(LayoutTransition transition){
        ObjectAnimator appearing = ObjectAnimator.ofFloat(null,"rotation",180);     //旋转360°
        transition.setAnimator(LayoutTransition.CHANGE_APPEARING,appearing);        //出现动画
    }

    /**
     * 位置改变
     * @param transition
     */
    private void setChangeDisAppearing(LayoutTransition transition){
        //关键帧   parameter1 进度的百分比 ，parameter2:此百分比的值
        Keyframe keyframe = Keyframe.ofFloat(0,0).ofFloat(1,360);
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("rotation",keyframe);
        ObjectAnimator appearing = ObjectAnimator.ofPropertyValuesHolder((Object) null,holder);     //旋转360°
        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING,appearing);        //出现动画
    }

    /**

    /**
     * 消失动画
     * @param transition
     */
    private void setDisappearing(LayoutTransition transition){
        ObjectAnimator appearing = ObjectAnimator.ofFloat(null,"rotation",0);
        transition.setAnimator(LayoutTransition.DISAPPEARING,appearing);
    }

    /*
     * 旋转出现
     * @param transition
     */
    private void setAppearing(LayoutTransition transition){
        ObjectAnimator appearing = ObjectAnimator.ofFloat(null,"rotation",360);     //旋转360°
        transition.setAnimator(LayoutTransition.APPEARING,appearing);        //出现动画
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add:
                setAppearing(transition);
                gl.setLayoutTransition(transition);
                Button button = new Button(this);
                button.setText(String.valueOf(i++));
                gl.addView(button,0);

                break;
            case R.id.remove:
                setDisappearing(transition);
                gl.setLayoutTransition(transition);
                if (gl.getChildCount()>0)
                    gl.removeViewAt(0);
                break;
        }
    }



}
