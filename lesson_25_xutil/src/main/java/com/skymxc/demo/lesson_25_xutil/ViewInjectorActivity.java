package com.skymxc.demo.lesson_25_xutil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_viewinjector)
public class ViewInjectorActivity extends AppCompatActivity {

    @ViewInject(R.id.text)
    private TextView tv0;
    @ViewInject(R.id.text2)
    private TextView tv1;
    @ViewInject(R.id.bt)
    private Button bt;
    @ViewInject(R.id.cb)
    private CheckBox cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化 告诉 ViewInjector应该进行操作的对象 和对象的布局视图
        x.view().inject(this);


    }


   //value  :id 单个：value = R.id.bt  ；多个：{R.id.bt,R.id.cb} 同时进行事件绑定
    //type  事件类型
    @Event(value = {R.id.bt,R.id.text},type =View.OnClickListener.class )
    private void onClick(View v) {
        switch (v.getId()){
            case R.id.bt:
                tv1.setText("click Button");
                break;
//            case R.id.cb:
//                tv1.setText("click Checkbox");
//                break;
            case R.id.text:
                tv1.setText("第一个被单击了");
                break;
        }
    }

    /**
     * Checkbox 的 状态改变 事件
     * @param buttonView 点击的Button
     * @param isChecked 是否选中
     */
    @Event(value = R.id.cb,type = CompoundButton.OnCheckedChangeListener.class)
    private void onChecedChanged(CompoundButton buttonView, boolean isChecked) {
        tv1.setText("Checbox 选中了吗："+isChecked);
    }


}
