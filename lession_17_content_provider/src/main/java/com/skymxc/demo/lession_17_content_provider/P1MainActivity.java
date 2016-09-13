package com.skymxc.demo.lession_17_content_provider;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.skymxc.demo.lession_17_content_provider.entity.Person;

import java.util.ArrayList;
import java.util.List;

public class P1MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView lv;
    private List<Person> persons;
    private P1Adapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p1_main);
        lv = (ListView) findViewById(R.id.listview);
        persons = new ArrayList<>();
        adapter = new P1Adapter(this,persons);
        lv.setAdapter(adapter);
        //初始化
        getLoaderManager().initLoader(1,null,callback);
    }

    private LoaderManager.LoaderCallbacks<Cursor> callback = new LoaderManager.LoaderCallbacks<Cursor>() {
        /**
         * 初始化  CursorLoader
         * @param i
         * @param bundle
         * @return
         */
        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
          CursorLoader cursorLoader=  new CursorLoader(P1MainActivity.this, Uri.parse("content://"+P1Provider.authority+"/person"),new String[]{"_id","path","title","describe"},null,null,null);

            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            persons.clear();
            Log.e("Tag","=====onLoadFinished========"+cursor.getCount());
            while (cursor != null && cursor.moveToNext()){
                Person person = new Person();
                String path = cursor.getString(cursor.getColumnIndex("path"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String describe = cursor.getString(cursor.getColumnIndex("describe"));
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                person.id =id;
                person.title =title;
                person.describe = describe;
                person.path = path;
                persons.add(person);
                Log.e("Tag","====== cursor.moveToNext()====="+person.title);
            }
            cursor.close();
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.add:

                Intent intent = new Intent(P1MainActivity.this,P1AddActivity.class);
                startActivityForResult(intent,10);


                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null){
            Person person = (Person) data.getSerializableExtra("person");
            ContentValues cv = new ContentValues();
            cv.put("path",person.path);
            cv.put("title",person.title);
            cv.put("describe",person.describe);
           Uri uri=  getContentResolver().insert(Uri.parse("content://"+P1Provider.authority+"/person"),cv);
            Log.e("Tag","===onActivityResult============uri:"+uri);
            query();
        }
    }


    private void query(){
        persons.clear();
        Cursor cursor = getContentResolver().query(Uri.parse("content://"+P1Provider.authority+"/person"),new String[]{"_id","title","path","describe"},null,null,null);
        while (cursor != null && cursor.moveToNext()){
            Person person = new Person();
            String path = cursor.getString(cursor.getColumnIndex("path"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String describe = cursor.getString(cursor.getColumnIndex("describe"));
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            person.id =id;
            person.title =title;
            person.describe = describe;
            person.path = path;
            persons.add(person);
            Log.e("Tag","====== cursor.moveToNext()====="+person.title);
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}
