package com.skymxc.demo.lession_13_menus;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout root ;
    private TextView tv ;
    private PopupMenu popupMenu;
    private Button bt_lb;
    private Button bt_rb;
    private Button pw_center;
    private Button bt_tr;

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = (RelativeLayout) findViewById(R.id.root);
        tv = (TextView) findViewById(R.id.tv);
        bt_lb = (Button) findViewById(R.id.pm_lb);
        bt_rb = (Button) findViewById(R.id.pm_rb);
        pw_center = (Button) findViewById(R.id.pw_center);
        bt_tr = (Button) findViewById(R.id.pm_rt);
        tv.setClickable(true);
        //注册上下文菜单
        registerForContextMenu(tv);
        registerForContextMenu(root);
        initPopupMenu();
        initPopupWindow();
    }

    /**
     * 初始化PopopWindow
     */
    private void initPopupWindow(){
        //加载布局
       View cv= getLayoutInflater().inflate(R.layout.layout_popup,null);
        //获取到布局中的TextView 设置点击事件
        TextView textView = (TextView) cv.findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"点击 了 PopupWindow",Toast.LENGTH_SHORT).show();
            }
        });

        //测量View的宽度
        cv.measure(View.MeasureSpec.makeMeasureSpec(1<<30-1,View.MeasureSpec.AT_MOST),View.MeasureSpec.makeMeasureSpec(1<<30-1,View.MeasureSpec.AT_MOST));


        //不设置宽高 不会显示
        popupWindow = new PopupWindow();
        popupWindow.setContentView(cv);
//        popupWindow.setWidth(200);//px
//        popupWindow.setHeight(200);//px
        popupWindow.setWidth(cv.getMeasuredWidth());        //getMeasuredWidth() 测量后的、
        popupWindow.setHeight(cv.getMeasuredHeight());
        popupWindow.setFocusable(true);//获取到焦点
        //必须设置背景 不然会不显示
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
    }

    /**
     * init popupMenu
     */
    private void initPopupMenu(){
        //指定 毛边控件 button
        popupMenu = new PopupMenu(this,bt_lb);
        //添加菜单
        popupMenu.inflate(R.menu.main_menu);

        /*getMenuInflater().inflate(R.menu.main_menu,popupMenu.getMenu());
        popupMenu.getMenuInflater().inflate(R.menu.main_menu,popupMenu.getMenu());*/

        //设置菜单项点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(MainActivity.this,"====PopupMenu=="+item.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    /**
     * 创建菜单
     * @param menu activity 已有菜单
     * @return 决定菜单是否生效
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e("Tag","===========onCreateOptionsMenu===============");

        //参数2 决定了将此菜单 设置给谁
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    int id = R.id.land_animl; //记录了 Option菜单 选中的菜单 这是默认选中的item

    /**
     *  菜单项被点击
     * @param item
     * @return 表示当前选中事件是否处理完成  false：处理没有完成；true:处理完成
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("Tag","===========onOptionsItemSelected===============");
        id = item.getItemId();
        Log.e("Tag","==================onOptionsItemSelected=============获取到选中项的id=:"+id);
        switch (item.getItemId()){
            case R.id.meat_animl:
                break;
            case R.id.grass_animal:
                break;
            case R.id.rabbit:
                break;
            case R.id.sheep:
                break;
            case R.id.land_animl:
                break;
        }

        Toast.makeText(MainActivity.this,item.getTitle(),Toast.LENGTH_SHORT).show();
        return true;
    }

    /**
     * 每次打开菜单都会调用一次 创建时也会调用
     * @param menu  要打开的菜单
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        Log.e("Tag","===========onPrepareOptionsMenu===============");

        //组，itemid,顺序  添加一个 没有子菜单的菜单
      //  menu.add(1,R.id.mix_animl,menu.size(),"mix animal(杂食动物)");

        //添加一个有子菜单的 菜单
    //    menu.addSubMenu(1,R.id.mix_animl,menu.size(),"mix animal(杂食动物)").add(2,R.id.pig_animl,0,"pig Animal(猪)");
        return true;
    }

    /**
     * 菜单打开时调用
     * @param featureId
     * @param menu
     * @return
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        Log.e("Tag","===========onMenuOpened===============");

        if (menu==null) return  super.onMenuOpened(featureId,menu);
        //可以在打开时人为选中 哪一个
        MenuItem item = menu.findItem(id);//获取上次选中的item id
        Log.e("Tag","===========onMenuOpened======已经选中的id==："+id);
        Log.e("Tag","===========onMenuOpened======已经选中的item Title==："+item.getTitle());
        if (item != null){

            item.setCheckable(true);
        }
        return true;
    }

    /**
     * ContextMenu
     */

    /**
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Log.e("Tag","===========onCreateContextMenu===============");

        //加载出菜单给 上下文菜单
        getMenuInflater().inflate(R.menu.main_menu,menu);

        switch (v.getId()){
            case R.id.root:
                menu.add("root");
                break;
            case R.id.tv:
                menu.add("textview");
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.e("Tag","===========onContextItemSelected===============");
        switch (item.getItemId()){
            case R.id.mix_animl:
                break;
        }
        Toast.makeText(this,"click了长按菜单",Toast.LENGTH_SHORT).show();
        return true;
    }

    public void click(View v){
        switch (v.getId()){
            case R.id.pm_lb:
                //显示菜单
                popupMenu.show();
                break;
            case R.id.pw_center:
                Log.e("Tag","==================isShowing():"+popupWindow.isShowing());
                if (popupWindow.isShowing()){//根本不会执行的 因为 popuWindow获取到焦点之后 activity就失去了焦点
                    //取消显示
                    popupWindow.dismiss();
                }
                //PopupWindow 显示  作为 锚点的 下拉菜单显示
              //  popupWindow.showAsDropDown(pw_center);
                //x便宜 100 y偏移50
               //popupWindow.showAsDropDown(pw_center,100,50);

                //在root容器内 ，位置为 CENTER 的位置 x偏移0，y偏移0的位置显示
             //   popupWindow.showAtLocation(root, Gravity.CENTER,0,0);

                popupWindow.showAtLocation(root, Gravity.CENTER|Gravity.TOP,0,0);

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterForContextMenu(tv);
        unregisterForContextMenu(root);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Tag","======onPause====失去焦点");
    }
}
