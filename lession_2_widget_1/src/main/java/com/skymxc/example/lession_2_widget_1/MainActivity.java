package com.skymxc.example.lession_2_widget_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private RadioButton radioEat = null;

    private RadioGroup radioGroup = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioEat= (RadioButton) findViewById(R.id.radioEat);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupAction);

        radioEat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });

        radioGroup.setOnCheckedChangeListener(rgChangeLis );


        ImageView image = (ImageView) findViewById(R.id.image);


//        Drawable drawable = getResources().getDrawable(R.mipmap.dog);
//        Bitmap bmp= ((BitmapDrawable)drawable).getBitmap();

        //设置显示资源图片
        image.setImageResource(R.mipmap.dog);
//        image.setImageBitmap(bmp);
//        image.setImageDrawable(getResources().getDrawable(R.mipmap.dog));
//
//        //Uri.parse()  将字符串解析为一个uri 对象
      //  image.setImageURI(Uri.parse("file:///asset/top.jpg"));

        image.setScaleType(ImageView.ScaleType.FIT_CENTER);

        //Spinner
        Spinner spinner= (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this,R.layout.list_item,R.id.text,getResources().getStringArray(R.array.spinner_entries));

        spinner.setAdapter(adapter);
        //设置选中后 监听
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    //下拉框 选中一项后 触发
    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        /**
         * d当某项被选中时调用
         * @param adapterView spinner
         * @param view  选中的项
         * @param position 被选中项在数据源中的下标
         * @param l 被选中项的数据项id
         */
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            Log.e("tag","---------------position:"+position);
            Log.e("tag","---------------l:"+l);
        }

        /**
         * 在没有任何项选中时调用  基本不会使用
         * @param adapterView
         */
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };



    //radioGroup面版内 的按钮状态改变 触发
    private RadioGroup.OnCheckedChangeListener rgChangeLis = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            RadioButton radioButton= (RadioButton) findViewById(i);

            Toast.makeText(MainActivity.this,"选中："+radioButton.getText(),Toast.LENGTH_SHORT).show();


        }
    };
}
