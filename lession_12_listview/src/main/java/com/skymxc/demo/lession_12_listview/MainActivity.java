package com.skymxc.demo.lession_12_listview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv ;

    private TextView tvEmpty;

    private MyAdapter myAdapter;

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listview);
        tvEmpty = (TextView) findViewById(R.id.empty_view);
        gridView = (GridView) findViewById(R.id.grid_view);
        //数据适配器
      //  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,new String[]{"item0","item1","item2","item3","item4","item5","item6","item7"});
//
//        List<HashMap<String,String>> data = new ArrayList<>();
//
//        for (int i=0;i<15;i++){
//            HashMap<String,String> map = new HashMap<>();
//
//            map.put("text1","name +"+i);
//            map.put("text2","text2 +"+i);
//
//            data.add(map);
//        }
//
//        //int[] 对应的控件
//        //String[] map中的key 如果不对 就不会显示出来
//        SimpleAdapter simpleAdapter = new SimpleAdapter(this,data,android.R.layout.simple_list_item_2,new String[]{"text1","text2"},new int[]{android.R.id.text1,android.R.id.text2});
//
//        //设置视图绑定器：定义数据和视图之间的绑定关系； 如果不能自定匹配我们的数据才使用
//        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
//            /**
//             *
//             * @param view 要绑定的视图
//             * @param o 数据
//             * @param s 文本描述
//             * @return false:表示绑定未处理完成 ；true ：处理完成  如果处理完成 就不会 执行 SimpleAdapter 中的setViewBinder
//             */
//            @Override
//            public boolean setViewValue(View view, Object o, String s) {
//                if (view.getId() == android.R.id.text1){
//                    TextView tv= (TextView)view;
//                    //tv.setText(s);
//                    tv.setText(o.toString());//和上面一样的
//                    tv.setTextColor(Color.RED);
//                    return true;
//                }
//                return false;
//            }
//        });



        lv.setDivider(new ColorDrawable(Color.GRAY));
        lv.setDividerHeight(5);


        //getLayoutInflater()   获取布局加载器，在ACtivity中使用
      //  LayoutInflater.from(this)  通过静态方法获取布局加载器

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);


        //事件
        lv.setOnItemClickListener(onItemClickListener);

        //设置无数据时提示视图
   //     lv.setEmptyView(tvEmpty);


        //加载头视图
       View header = getLayoutInflater().inflate(R.layout.layout_header,null);
        TextView headerTv = (TextView) header.findViewById(R.id.header_tv);
        headerTv.setTextColor(Color.RED);
        ImageView headerImg = (ImageView) header.findViewById(R.id.image);
        headerImg.setImageResource(R.mipmap.ic_launcher);
        //设置头视图  头视图占据一项
        lv.addHeaderView(header);


        List<Student> students = new ArrayList<>();

        myAdapter = new MyAdapter(this,students);

        lv.setAdapter(myAdapter);






        gridView.setAdapter(myAdapter);
        gridView.setEmptyView(tvEmpty);

    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        /**
         *
         * @param parent ListView 本身
         * @param view  被点击项的view
         * @param position 子项的下标
         * @param l 被点击项的id  对应 getItemId()返回的数据
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

            Log.e("Tag","没有添加之前的position："+position);

            //lv.getHeaderViewsCount();  获取头视图总数量
            if (position>0){
                //当添加过头视图之后 数据项依次向下
                Student s = (Student) parent.getAdapter().getItem(position-lv.getHeaderViewsCount());

                Toast.makeText(MainActivity.this,s.getName(),Toast.LENGTH_SHORT).show();
            }


        }
    };


    public void click(View v){
        switch (v.getId()){
            case R.id.empty_view:
                for (int i=0;i<15;i++){
                    Student stu = new Student();
                    stu.setAge(12);
                    stu.setName("张三");
                    if (i%2==0)
                    stu.setPhoto(R.mipmap.ic_launcher);
                    myAdapter.addStudent(stu);
                }
                break;
        }
    }
}
