package com.skymxc.demo.lession_15_data_storage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img ;

    private RadioGroup rg;
    private File imageFile;
    private Bitmap bitmap;
    private TextView tvInfo;
    private CheckBox cbSkew;
    private CheckBox cbRotate;
    private CheckBox cbScale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        img = (ImageView) findViewById(R.id.image);
        rg = (RadioGroup) findViewById(R.id.rg);
        tvInfo = (TextView) findViewById(R.id.textview);
        cbRotate = (CheckBox) findViewById(R.id.cb_rotate);
        cbScale = (CheckBox) findViewById(R.id.cb_scale);
        cbSkew = (CheckBox) findViewById(R.id.cb_skew);
        imageFile = new File(Environment.getExternalStorageDirectory(),"Download/dog.jpg");
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_byte:
                        readByteArrayImage();
                        break;
                    case R.id.rb_file:
                        //File
                        readSDCardImage();
                        break;
                    case R.id.rb_resource:
                        readResourceImage();
                        break;
                    case R.id.rb_stream:
                        readStreamImage();
                        break;
                }
            }



        });
    }

    /**
     * 从字节数组加载
     */
    private void readByteArrayImage() {
        if(imageFile.exists()){
            try {
                FileInputStream fis = new FileInputStream(imageFile);
                int len =-1;
                byte[] b = new byte[1024];
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                while ((len=fis.read(b))!=-1){
                bos.write(b,0,len);
                }
                b= bos.toByteArray();
                bitmap= BitmapFactory.decodeByteArray(b,0,b.length);
                img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this,"文件不存在",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 从流中加载图片
     */
    private void readStreamImage() {
        try {
            if (imageFile.exists()) {
                bitmap = BitmapFactory.decodeStream(new FileInputStream(imageFile));
                img.setImageBitmap(bitmap);
            }else{
                Toast.makeText(this,"文件不存在",Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取资源图片
     */
    private void readResourceImage() {

        bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.top);
        img.setImageBitmap(bitmap);
    }

    /**
     * 从SD卡读取图片
     */
    private void readSDCardImage() {
        if (imageFile.exists()){
            Log.e("Tag","filePath："+imageFile.getPath());
           bitmap= BitmapFactory.decodeFile(imageFile.getPath());
            img.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this,"文件不存在",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.get_image_info:
                if (bitmap == null){
                    tvInfo.setText("null");
                }else{
                    StringBuffer sb = new StringBuffer();
                    sb.append("isRecycled:"+bitmap.isRecycled());
                    //是否释放了
                    if (!bitmap.isRecycled()){
                        sb.append("\n Width："+bitmap.getWidth());
                        sb.append("\n Height："+bitmap.getHeight());
                        sb.append("\n 解析后的Size:"+bitmap.getByteCount());
                        sb.append("\n File Size:"+imageFile.length());
                        try {
                            //无损压缩
                            File f = new File(Environment.getExternalStorageDirectory(),"sky/image.jpg");
                            FileOutputStream fos = new FileOutputStream(f);
                            //参数1 压缩格式 参数2压缩质量  ，参数3 压缩到的流
                            bitmap.compress(Bitmap.CompressFormat.JPEG,90,fos);
                            fos.flush();
                            fos.close();
                            sb.append("\n compress Size:"+f.length());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //释放资源
                        bitmap.recycle();
                    }

                    sb.append("\nisRecycled:"+bitmap.isRecycled());
                    tvInfo.setText(sb.toString());
                }
                break;

            case R.id.change:
                if (bitmap == null){
                    Toast.makeText(this,"bitmap is null", Toast.LENGTH_SHORT).show();
                }else if (bitmap.isRecycled()){
                    Toast.makeText(this,"位图已释放", Toast.LENGTH_SHORT).show();
                }else{
                    //矩阵
                    Matrix matrix = new Matrix();
                    if (cbScale.isChecked()){
                        //缩放 前两个：缩放比例 不能写0 ，后两个 一吧效果看不出来
                        matrix.postScale(1.5f,1.5f);        //默认以图片的左上角进行缩放
                        //中心点缩放
                    //    matrix.postScale(1.5f,1.4f,bitmap.getWidth()/2,bitmap.getHeight()/2);
                    }
                    if (cbRotate.isChecked()){
                        //旋转    参数1 旋转度  默认是左上角
                        matrix.postRotate(45);
                    }
                    if (cbSkew.isChecked()){
                        //倾斜  默认 是以左上角进行倾斜  参数：x,y
                        matrix.postSkew(0.5f,0);
                    }
                    //有一个选中了
                    if (cbSkew.isChecked()||cbRotate.isChecked()||cbScale.isChecked()){
                        //根据一个位图创建一个新的位图
                        //新建一个位图  params:源位图 ，x,y,新位图宽w，新位图高h，矩阵，是否色彩过滤      ps：x+w <=w 必须小于等于 y和高也一样的关系,不能等于0
                       Bitmap bmp= Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);

                       img.setImageBitmap(bmp);
                       // img.setImageMatrix(matrix);
                    }
                }

                break;
        }
    }
}
