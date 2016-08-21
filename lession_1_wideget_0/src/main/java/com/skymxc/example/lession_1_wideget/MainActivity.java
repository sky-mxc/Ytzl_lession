package com.skymxc.example.lession_1_wideget;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    //文本显示
    private TextView tv = null;

    //按钮
    private Button button = null;

    //文本输入框
    private EditText edit = null;

    //复选框
    private CheckBox checkBoxClimb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv );
        button = (Button) findViewById(R.id.buttonBt);
        edit = (EditText) findViewById(R.id.edit);
        checkBoxClimb = (CheckBox) findViewById(R.id.checkboxClimb);

        //文字内容
        tv.setText(" update content by JavaCode");
        //文字尺寸 不指定单位的话 默认单位是像素
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        //设置空间的重心 可以同过 | 连接
        tv.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);

        //可绘制资源
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        //可通过 drawable 设置图片范围大小
        drawable.setBounds(0,0,50,50);
        //设置图片 参数顺序： left,top,right,bottom
        tv.setCompoundDrawables(null,null,drawable,drawable);
        //图片和文字的距离
        tv.setCompoundDrawablePadding(0);

        //Button
        button.setText("click me ");
        //设置点击事件
        button.setOnClickListener(buttonClickListener);

        //EditText
        edit.setHint(" input NickName Please");

        //设置最大输入长度  通过设置过滤器 来限制   设置 长度过滤器LengthFilter
        edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
        //设置输入类型 必须先设置 一个 class类型
        edit.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);


        //复选框
        //设置监听  在 改变状态后触发
        checkBoxClimb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             *
             * @param buttonView 被点击的button
             * @param isChecked 按钮的状态
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                String str = isChecked?"已选中"+buttonView.getText():"未选中"+buttonView.getText();
                /*switch (buttonView.getId()){
                    case R.id.checkboxClimb:
                        str= isChecked?"已选中":"未选中";
                        Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
                        break;
                }*/
                Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();


            }
        });

    }

    /**
     * 通过 实现匿名内部类的方式 监听 点击 事件
     */
    private View.OnClickListener  buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //获取到 文本输入框的值
            String editStr =  edit.getText().toString();

            Toast.makeText(MainActivity.this,"oh yes ,my god ："+editStr,Toast.LENGTH_SHORT).show();

            edit.setText("点击过后");

            Editable editableStr = edit.getText();
            editableStr.append("光标在后");
            editableStr.delete(0,3);
        }
    };
}
