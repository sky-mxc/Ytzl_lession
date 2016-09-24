package com.skymxc.demo.lesson_24_tab;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabHost = (FragmentTabHost) findViewById(R.id.tab_host);
        //必须设置 用到的对象
        tabHost.setup(this,getSupportFragmentManager(),R.id.container);
        //添加标签页 至少添加一个
        TabHost.TabSpec spec0 = tabHost.newTabSpec("message");      //创建Tab
        spec0.setIndicator(createTab(R.drawable.selector_tab_msg,"消息"));    //为tab设置 指示器 可以为 view 可以为 消息
        tabHost.addTab(tabHost.newTabSpec("message").setIndicator("消息"),MsgFragment.class,null);
        tabHost.addTab(tabHost.newTabSpec("contact").setIndicator("联系人"),ContactsFragment.class,null);
//        tabHost.addTab(tabHost.newTabSpec("active").setIndicator("活动"),ContactsFragment.class,null);
        tabHost.getTabWidget().setDividerDrawable(null);    //去除间隔线
        tabHost.setCurrentTab(1);
        //设置标签改变的监听
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Toast.makeText(MainActivity.this,tabId,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public View createTab(int icon, String tab){
        View view = getLayoutInflater().inflate(R.layout.layout_tab,null);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        TextView textView = (TextView) view.findViewById(R.id.tab);
        iconView.setImageResource(icon);
        textView.setText(tab);
        return  view;
    }
}
