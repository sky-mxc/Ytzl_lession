package com.skymxc.demo.lesson_34_refreshframework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by sky-mxc
 */
public class ThirdActivity extends AppCompatActivity {
    private static final String TAG = "ThirdActivity";

    private PullToRefreshListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        listView=(PullToRefreshListView) findViewById(R.id.ptr_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,new String[]{"item","item","item","item","item","item","item","item","item","item","item","item","item","item","item","item","item","item","item","item","item","item","item","item","item"});
        listView.setAdapter(adapter);
        //刷新监听
        listView.setOnRefreshListener(onRefreshListener2);
    }

    private PullToRefreshBase.OnRefreshListener2 onRefreshListener2 = new PullToRefreshBase.OnRefreshListener2() {
        @Override
        public void onPullDownToRefresh(final PullToRefreshBase refreshView) {
            refreshView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshView.onRefreshComplete();
                }
            },1000*2);
        }

        @Override
        public void onPullUpToRefresh(final PullToRefreshBase refreshView) {
            refreshView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshView.onRefreshComplete();
                }
            },1000*2);
        }
    };
}
