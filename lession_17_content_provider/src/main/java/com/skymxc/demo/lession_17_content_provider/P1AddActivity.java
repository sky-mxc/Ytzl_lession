package com.skymxc.demo.lession_17_content_provider;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.skymxc.demo.lession_17_content_provider.entity.Person;

public class P1AddActivity extends AppCompatActivity  implements View.OnClickListener{

    private ImageView image;
    private EditText etTitle;
    private EditText etDescribe;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p1_add);
        image = (ImageView) findViewById(R.id.image);
        etTitle = (EditText) findViewById(R.id.title);
        etDescribe = (EditText) findViewById(R.id.describe);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.ok:
                Log.e("Tag","=======onClick==========");
                String title = etTitle.getText().toString();
                String describe = etDescribe.getText().toString();
                Person person = new Person();
                person.path=path;
                person.describe = describe;
                person.title=title;
                Intent in = new Intent();
                in.putExtra("person",person);
                setResult(RESULT_OK,in);
                finish();
                break;
            case R.id.image:
                 in = new Intent(Intent.ACTION_PICK);
                in.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            Cursor cursor=
             getContentResolver().query(uri,new String[]{MediaStore.Images.Media.DATA},null,null,null);
            if (cursor!=null && cursor.moveToFirst()){
                 path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                Log.e("Tag","===path"+path);
                Bitmap bmp= BitmapFactory.decodeFile(path);
                image.setImageBitmap(bmp);
            }
        }
    }
}
