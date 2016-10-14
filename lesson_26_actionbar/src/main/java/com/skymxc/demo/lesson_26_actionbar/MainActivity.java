package com.skymxc.demo.lesson_26_actionbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener ,CompoundButton.OnCheckedChangeListener{

    private CheckBox cbSetTitle;
    private CheckBox cbShowTitle;
    private CheckBox cbSetSubTitle;
    private CheckBox cbShowActionBar;
    private CheckBox cbShowLogo;
    private CheckBox cbSetIcon;
    private CheckBox cbShowHome;
    private CheckBox cbShowUp;
    private Button btShow;
    private Button btHide;

    private   ActionBar actionBar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();



    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        actionBar= getSupportActionBar();

        actionBar.setBackgroundDrawable(getDrawable(R.drawable.shape_actionbar_bg)); //ActionBar背景
        /**
         * 设置显示的项 决定性作用
         * ActionBar.DISPLAY_SHOW_HOME ： Icon
         * ActionBar.DISPLAY_HOME_AS_UP  :返回键
         * ActionBar.DISPLAY_SHOW_TITLE : title
         * ActionBar.DISPLAY_USE_LOGO   :logo
         * 更多的查看源码
         * 可以使用 | 连接
         */
        actionBar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);

        cbSetIcon = (CheckBox) findViewById(R.id.set_icon);
        cbSetSubTitle = (CheckBox) findViewById(R.id.set_sub_title);
        cbSetTitle = (CheckBox) findViewById(R.id.set_title);
        cbShowHome = (CheckBox) findViewById(R.id.show_home);
        cbShowLogo = (CheckBox) findViewById(R.id.set_logo);
        cbShowActionBar = (CheckBox) findViewById(R.id.show_actionbar);
        cbShowTitle = (CheckBox) findViewById(R.id.show_title);
        cbShowUp = (CheckBox) findViewById(R.id.show_as_up);
        btHide = (Button) findViewById(R.id.hide_action_mode);
        btShow = (Button) findViewById(R.id.show_action_mode);

        btHide.setOnClickListener(this);
        btShow.setOnClickListener(this);
        cbSetTitle.setOnCheckedChangeListener(this);
        cbSetIcon.setOnCheckedChangeListener(this);
        cbSetSubTitle.setOnCheckedChangeListener(this);
        cbShowHome.setOnCheckedChangeListener(this);
        cbShowLogo.setOnCheckedChangeListener(this);
        cbShowActionBar.setOnCheckedChangeListener(this);
        cbShowUp.setOnCheckedChangeListener(this);
        cbShowTitle.setOnCheckedChangeListener(this);
        //添加 Tab
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //must add  TabListener
        actionBar.addTab(actionBar.newTab().setText("TAB1").setTabListener(tabListener),true); //添加TAB并设置默认选中项
        actionBar.addTab(actionBar.newTab().setText("Tab2").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Tab3").setTabListener(tabListener));

        //List
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,new String[]{"list1","list2","list3"});
//        actionBar.setListNavigationCallbacks(adapter, new ActionBar.OnNavigationListener() {
//            @Override
//            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
//                return false;
//            }
//        });
    }

    /**
     * Tab 监听
     */
    private ActionBar.TabListener tabListener = new ActionBar.TabListener() {

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar,menu);
        //actionView
        MenuItem item = menu.findItem(R.id.search);
        //获取 ActionView
        //app：
       // SearchView sechViewApp= (SearchView) item.getActionView();
        //v7
        Log.e(getClass().getName(),"===========item:"+item.getTitle());
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(item);
        //设置查询文本的监听
        Log.e(getClass().getName(),"===========SearchView:"+searchView);
        searchView.setOnQueryTextListener(queryTextListener);
        searchView.setQueryHint("嗯哈，输入吧");

        //获取Shared
        MenuItem item1 = menu.findItem(R.id.share);
       // ActionProvider shareActionProvider= item.getActionProvider();
       ShareActionProvider share= (ShareActionProvider) MenuItemCompat.getActionProvider(item1);
        //设置分享的Intent内容
        Intent in = new Intent(Intent.ACTION_SEND);
        in.setType("image/*");
       // in.setType("text/plain");///文本
        in.putExtra(Intent.EXTRA_TITLE,"title");
        in.putExtra(Intent.EXTRA_SUBJECT,"subject");
        in.putExtra(Intent.EXTRA_TEXT," text ，this is dog；this is  dog ；");
        in.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/Download/dog.jpg"));        //分享文件地址
        share.setShareIntent(in);
        return true;
    }

    private SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        /**
         * 当进行查询时 触发
         * @param query 要查询的文本
         * @return
         */
        @Override
        public boolean onQueryTextSubmit(String query) {
            Toast.makeText(MainActivity.this,"==onQueryTextSubmit=="+query,Toast.LENGTH_SHORT).show();
            return true;
        }

        /**
         * 查询文本改变时触发
         * @param newText
         * @return
         */
        @Override
        public boolean onQueryTextChange(String newText) {
            Log.e("MainActivity","=onQueryTextChange="+newText);
            return true;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                Toast.makeText(this,"设置",Toast.LENGTH_SHORT).show();
                break;
            case R.id.share:
                Toast.makeText(this,"分享",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private ActionMode actionMode ;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.to:
                Intent intent = new Intent(this,SecondActivity.class);
                startActivity(intent);
                break;
            case R.id.show_action_mode:
                if (actionMode==null) {
                    actionMode = v.startActionMode(callback);
                    actionMode.setTitle("actionMode 啊");
                    actionMode.setSubtitle("subTitle啊");
                }
                break;
            case R.id.hide_action_mode:
                actionMode.finish();
                break;
        }

    }

    private ActionMode.Callback callback = new ActionMode.Callback(){

        /**
         * ActionMode 创建时
         * 不返回true 不会显示 ActionMode
         * @param mode
         * @param menu
         * @return true
         */
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            menu.add("嗯哼");
            return true;
        }

        /**
         * 准备 actionMode的时候
         * @param mode
         * @param menu
         * @return
         */
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        /**
         * 菜单项点击事件
         * 返回true标识已经处理了
         * @param mode
         * @param item
         * @return
         */
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        /**
         * 销毁时调用
         * @param mode
         */
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode=null;
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.show_as_up:
                //back 显示
             actionBar.setDisplayHomeAsUpEnabled(isChecked);
                break;
            case R.id.show_home:
                //home显示
                actionBar.setDisplayShowHomeEnabled(isChecked);
                break;
            case R.id.show_actionbar:
                //ActionBar 显示
              if (isChecked){
                  actionBar.show();
              }else{
                  actionBar.hide();
              }
                break;
            case R.id.show_title:
                //显示title
                actionBar.setDisplayShowTitleEnabled(isChecked);
                break;
            case R.id.set_icon:
                if (isChecked){
                    actionBar.setIcon(R.mipmap.acj);
                }else{
                    actionBar.setIcon(null);
                }
                break;
            case R.id.set_logo:
                if (isChecked){
                    actionBar.setLogo(R.mipmap.a8c);
                }else{
                    actionBar.setLogo(null);
                }
                break;
            case R.id.set_title:
                //设置 title
                if (isChecked){
                    actionBar.setTitle("sky-mxc");
                }else{
                    actionBar.setTitle(null);
                }
                break;
            case R.id.set_sub_title:
                //设置子title
                if (isChecked){
                    actionBar.setSubtitle("mxc");

                }else{
                    actionBar.setSubtitle(null);
                }
                break;


        }
    }
}