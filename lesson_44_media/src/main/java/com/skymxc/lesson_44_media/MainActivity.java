package com.skymxc.lesson_44_media;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ToggleButton tb;
    private ListView mediaList;
    private List<MediaBean> medias;
    private MediaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaList= (ListView) findViewById(R.id.media_list);
        tb= (ToggleButton) findViewById(R.id.toggle_audio_and_video);
        tb.setOnCheckedChangeListener(onCheckedChangeListener);
        medias = new LinkedList<>();
        loadData(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        adapter = new MediaAdapter();
        mediaList.setAdapter(adapter);
        mediaList.setOnItemClickListener(onItemClickListener);
    }

    private void loadData(Uri uri){
        medias.clear();
        String[] projectation = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME
        };
        String where = null;
        if (!tb.isChecked()){
            where =String.format("%s=1", MediaStore.Audio.Media.IS_MUSIC);
        }
        Cursor cursor =getContentResolver().query(uri,projectation,where,null,null);
       
        Log.i(TAG, "loadData: count="+ cursor.getCount());
        while (cursor!=null && cursor.moveToNext()){

            MediaBean mediaBean = new MediaBean();
            mediaBean.setId(cursor.getInt(0));
            mediaBean.setPath(cursor.getString(1));
            mediaBean.setName(cursor.getString(2));
            medias.add(mediaBean);
        }

        if (cursor!=null){
            cursor.close();
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String path = adapter.getItem(i).getPath();
            Log.i(TAG, "onItemClick: path="+path);
            if (tb.isChecked()){
                Intent in = new Intent(MainActivity.this,VideoActivity.class);
                in.putExtra("path",path);
                startActivity(in);
            }else{
                Intent in = new Intent(MainActivity.this,AudioActivity.class);
                in.putExtra("path",path);
                startActivity(in);
            }
        }
    };



    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b){
                loadData(MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            }else{
                loadData(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
            }
            adapter.notifyDataSetChanged();
        }
    };
    class  MediaAdapter  extends BaseAdapter{

        @Override
        public int getCount() {
            return medias.size();
        }

        @Override
        public MediaBean getItem(int i) {
            return medias.get(i);
        }

        @Override
        public long getItemId(int i) {
            return medias.get(i).getId();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView tv;
            if (view == null){
                tv = (TextView) getLayoutInflater().inflate(android.R.layout.simple_list_item_1,null);
            }else{
                tv = (TextView) view;
            }
            tv.setText(medias.get(i).getName());
            return tv;
        }
    }
}
