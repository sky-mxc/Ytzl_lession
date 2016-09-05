package com.skymxc.demo.practice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sky-mxc
 */
public class InfoActivity  extends AppCompatActivity{
    private static final int REQUEST_CHOOSE = 1;    //选择请求吗
    private static final int REQUEST_CAMERA = 2;    //相机请求吗
    private static final int REQUEST_PICTURE = 3;   //图库请求吗
    private static final int REQUEST_CUT = 4;        //剪裁程序请求吗
    private static final int REQUEST_PWD = 5;        //修改密码请求码
    //头像
    private ImageView img;

    //头像数组
    private byte[] photoData;

    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        img = (ImageView) findViewById(R.id.img);
        tv = (TextView) findViewById(R.id.update_pwd);
        Intent intent = getIntent();
        if (intent.getExtras()!=null){
            photoData = intent.getByteArrayExtra("data");
            if (photoData ==null)return;
            Bitmap bmp = getBitmap(photoData);
            img.setImageBitmap(bmp);
        }
    }

    /**
     * 点击事件
     * @param v
     */
    public void click(View v){
        switch (v.getId()){
            case R.id.img:
                //开始选择是图片还是 拍照
                Intent in = new Intent(InfoActivity.this,ChooseActivity.class);
                startActivityForResult(in,REQUEST_CHOOSE);
                break;
            case R.id.back:
                //返回
                in = new Intent(InfoActivity.this,MainActivity.class);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                Bitmap bmp = ((BitmapDrawable)(img.getDrawable())).getBitmap();
                bmp.compress(Bitmap.CompressFormat.PNG,100,bos);

                photoData=bos.toByteArray();
                in.putExtra("data",photoData);
                startActivity(in);
                break;
            case R.id.update_pwd:
                //修改密码
                in = new Intent(InfoActivity.this,UpdatePwdActvity.class);
                startActivityForResult(in,REQUEST_PWD);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || resultCode != RESULT_OK){
            return;
        }

        switch (requestCode){
            case REQUEST_CHOOSE:

                Intent intent =null;
                //选择是拍照还是 图库  //默认是图库
                int result = data.getIntExtra("result",-1);
                Log.e("Tag","选择结果："+result);
                if (result>0){
                    //相机
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,REQUEST_CAMERA);

                }else{
                    //图库
                    intent= new Intent(Intent.ACTION_GET_CONTENT);
                  //  intent.setType("image/*");
                 //   intent.setData();
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                    startActivityForResult(intent,REQUEST_PICTURE);
                }

                break;
            case REQUEST_PICTURE:
                Log.e("Tag","======2="+data.getData());
                Uri uri = data.getData();
                uri= convertUri(uri);//转为文件类型的uri
                startImageZoom(uri);
                break;
            case REQUEST_CAMERA:
                //调用 相机后的结果
                Bundle extras = data.getExtras();
                if (extras != null){
                    //获得拍的照片
                    Bitmap bm = extras.getParcelable("data");
                    //将Bitmap转化为uri
                     uri = saveBitmap(bm, "temp");
                    //启动图像裁剪
                    startImageZoom(uri);
                }
                break;
            case REQUEST_CUT:
                //获取剪裁后的图片
                  extras = data.getExtras();
                //获取序列化对象Bitmap
                Bitmap bmp=  extras.getParcelable("data");

                img.setImageBitmap(bmp);
                break;
            case REQUEST_PWD:
                String d = data.getStringExtra("d");
                tv.setText(d);
                break;
        }

    }

    /**
     * 将Bitmap 存入sd卡 并转为 文件URI
     * @param bmp
     * @param dirPath
     * @return
     */
    private Uri saveBitmap (Bitmap bmp,String dirPath){
        //新建文件夹用于存放裁剪后的图片
        File tmpDir = new File(Environment.getExternalStorageDirectory()+"/"+dirPath);
        if (!tmpDir.exists()){
            tmpDir.mkdir();
        }

        try {
            //新建文件存储裁剪后的图片
            File img = new File(tmpDir.getAbsoluteFile()+"/avator.png");
            FileOutputStream fos = new FileOutputStream(img);
            //将Bitmap 压缩后谢雨输出流（参数 图片格式，图片质量 ，输出流）
            bmp.compress(Bitmap.CompressFormat.PNG,100,fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (IOException e) {
           return null;
        }
    }

    /**
     * 将content 类型的Uri转换为 文件类型的Uri
     * @param uri
     * @return
     */
    private Uri convertUri(Uri uri ){
        InputStream is ;
        try {
            //Uri 》 InputStream
            is= getContentResolver().openInputStream(uri);

            //InputStream 》 Bitmap
            Bitmap bmp = BitmapFactory.decodeStream(is);

            is.close();
            return saveBitmap(bmp,"temp");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 通过Uri 传递图像信息 然后剪裁
     * @param uri
     */
    private void startImageZoom(Uri uri){

        //构建隐式Itent 启动剪裁程序
        Intent intent = new Intent("com.android.camera.action.CROP");
        //设置数据uri和类型为图片类型
        intent.setDataAndType(uri,"image/*");
        //显示View为可剪裁的
        intent.putExtra("crop",true);
        //裁剪的宽和高比例
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //输出图片的宽高 均为 200
        intent.putExtra("outputX",200);
        intent.putExtra("outputY",200);
        //裁剪之后返回的数据是通过intent 返回  部分手机不支持
        intent.putExtra("return-data",true);
        startActivityForResult(intent,REQUEST_CUT);
    }


    private Bitmap getBitmap(byte[] data){
        return BitmapFactory.decodeByteArray(data,0,data.length);
    }
}
