package com.skymxc.demo.lesson_26_actionbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

/**
 * Created by sky-mxc
 */

public class ToolBarActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("sky-mxc");
      //  toolbar.setLogo(R.mipmap.acj);
        //设置导航图标
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        //导航图标点击事件
        toolbar.setNavigationOnClickListener(onClickListener);


        //不作为 ActionBar使用，设置菜单
        toolbar.inflateMenu(R.menu.menu_action_bar);
        toolbar.setOnMenuItemClickListener(null);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(ToolBarActivity.this,"点击了导航",Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar,menu);
        return true;
    }
}
