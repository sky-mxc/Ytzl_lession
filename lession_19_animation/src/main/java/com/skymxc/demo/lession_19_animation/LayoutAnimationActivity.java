package com.skymxc.demo.lession_19_animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LayoutAnimationActivity extends AppCompatActivity {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_animation);
        lv = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,new String[]{"item1","item2","item3","item4"});
        lv.setAdapter(adapter);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_set);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(0.5f);  //延时时间 单位 s 秒
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);          //顺序  出厂顺序 正常
        controller.setInterpolator(new DecelerateInterpolator());           //减速差值器
        lv.setLayoutAnimation(controller);

    }
}
