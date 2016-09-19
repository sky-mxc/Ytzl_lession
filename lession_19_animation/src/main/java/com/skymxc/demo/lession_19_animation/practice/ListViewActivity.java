package com.skymxc.demo.lession_19_animation.practice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.skymxc.demo.lession_19_animation.R;

public class ListViewActivity extends AppCompatActivity {
    private ListView lv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        lv = (ListView) findViewById(R.id.lv);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,new String[]{"item1","item2","item2","item2","item2","item2","item2"});
        lv.setAdapter(adapter);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_p2_list_item_move);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(1f);
        controller.setOrder(LayoutAnimationController.ORDER_RANDOM);
        controller.setInterpolator( new DecelerateInterpolator());
        lv.setLayoutAnimation(controller);
    }
}
