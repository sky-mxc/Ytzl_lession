package com.skymxc.demo.lession_19_animation;

import android.animation.LayoutTransition;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

public class ayoutTransitionActivity extends AppCompatActivity implements View.OnClickListener {

    private GridLayout gl;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayout_transition);
        gl = (GridLayout) findViewById(R.id.gridlayout);
        LayoutTransition transition = new LayoutTransition();
        transition.setDuration(500);
     //   transition.setAnimator(LayoutTransition.APPEARING,);
        gl.setLayoutTransition(transition);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add:

                Button button = new Button(this);
                button.setText(String.valueOf(i++));
                gl.addView(button,0);
                break;
            case R.id.remove:
                if (gl.getChildCount()>0)
                    gl.removeViewAt(0);
                break;
        }
    }



}
