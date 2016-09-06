package com.skymxc.demo.lession_15_data_storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CheckBox cbPush;
    private EditText etText ;
    private TextView tvText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cbPush = (CheckBox) findViewById(R.id.push);
        etText = (EditText) findViewById(R.id.edit);
        tvText = (TextView) findViewById(R.id.textview);

        SharedPreferences sp = getSharedPreferences("setting",Context.MODE_PRIVATE);
        boolean checked =sp.getBoolean("push",false);//取数据
        cbPush.setChecked(checked);
        cbPush.setOnCheckedChangeListener(changeLis);
    }

    private CompoundButton.OnCheckedChangeListener changeLis = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            /**
             * 获取的三种方式
             *  getPreferences(MODE_PRIVATE); 文件名称以 activity的名称进行创建
             *  PreferenceManager.getDefaultSharedPreferences(MainActivity.this); 访问形式 默认私有  名字 是 包名_preferences.xml
             */

           // getPreferences(MODE_PRIVATE).edit().putBoolean("push",b).commit();
           // PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putBoolean("push",b).commit();

            //参数1 文件名 参数2 对数据访问进行限制  私有 不允许被人访问
            SharedPreferences sp = getSharedPreferences("setting", Context.MODE_PRIVATE);
            /**
             * 整体存储结构跟Map类似、
             * sp.contains()；//查看是否包含某key
             *  edit.apply(); 将缓存改变 并且开一个 线程去提交
             */
            boolean isContains= sp.contains("push");    //查看是否包含 某key
            //获取编辑器对象 用于存储数据
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean("push",b);      //存储数据
            edit.commit();              //提交数据
            edit.apply();   //让原来的数据和修改过的值保持一致 并开一个线程去commit


        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.save_inner_storage:
                write();
                break;
            case R.id.read_inner_storage:
                read();
                break;
            case R.id.save_outter:
                writeOut();
                break;
            case R.id.read_outter:
                readOut();
                break;
        }
    }

    /**
     * 从SD读取
     */
    private void readOut() {
        String state = Environment.getExternalStorageState();

        //是否可度
        try {
            if (state.equals(Environment.MEDIA_MOUNTED)||state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
                //获取文件路径
                File  sdCordRoot = Environment.getExternalStorageDirectory();
                File read = new File(sdCordRoot,"sky/text.txt");

                if (read.exists()){
                    FileInputStream fis = new FileInputStream(read);

                   InputStreamReader isr = new InputStreamReader(fis,"utf-8");
                    BufferedReader reader = new BufferedReader(isr);
                    StringBuffer sb = new StringBuffer();
                    String line =null;
                    while ((line = reader.readLine())!=null){
                        sb.append(line);
                    }
                    tvText.setText(sb.toString());
                    reader.close();
                    isr.close();
                    fis.close();

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 写入到外部存储卡
     */
    private void writeOut() {
        //判断SD卡的状态
        String state = Environment.getExternalStorageState();           //获取SD卡的状态
        try {
            //Environment.MEDIA_MOUNTED 可读写
            if (state.equals(Environment.MEDIA_MOUNTED)){
                File sdcardRoot = Environment.getExternalStorageDirectory();    //返回扩展卡的文件夹
                File save = new File(sdcardRoot,"sky/text.txt");        //
                if (save.exists()){
                    //文件已存在
              //      save.delete();
                }else{
                    //不存在
                    if (!save.getParentFile().exists()){
                        //父级文件夹不存在
                        save.getParentFile().mkdirs();       //创建父级文件夹
                    }

                     boolean result=  save.createNewFile();// 创建文件
                    Log.e("Tag","==创建文件的结果："+result);
                }

    //            save.isDirectory();//判断是否是文件夹
    //            save.isFile() ; //是否是文件
    //            save.isHidden();//是否是隐藏文件
    //            save.list();//获取所有子文件 文件名数组
    //            save.listFiles();//所有子文件  文件数组
               String text = etText.getText().toString();

                //字节流
//                FileOutputStream fos = new FileOutputStream(save,cbPush.isChecked());
//                fos.write(text.getBytes("utf-8"));
//                fos.flush();
//                fos.close();


                //字符流
                BufferedWriter writer = new BufferedWriter(new FileWriter(save,cbPush.isChecked()));
                writer.write(text);
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从内部存储读取出来
     */
    private void read() {
        try {
            //拿到输入流
            FileInputStream fis = openFileInput("text.txt");
            StringBuffer sb = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis,"utf-8"));
            String line = null;
            while ((line=reader.readLine())!=null){
                sb.append(line);
            }
            tvText.setText(sb.toString());

//            int len =-1;
//            byte [] b = new byte[1024];
//            //进行字节缓存
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            while ((len = fis.read(b))!=-1){
//                bos.write(b,0,len);
//            }
//            tvText.setText(bos.toString("utf-8"));
//            bos.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将数据存到内部存储当中
     */
    private void write() {
        String text = etText.getText().toString();
        if (TextUtils.isEmpty(text))return;
        //获取到 输出流
        try {
            int mode = cbPush.isChecked()?MODE_APPEND:MODE_PRIVATE;
           FileOutputStream fos = openFileOutput("text.txt",mode);
            fos.write(text.getBytes("utf-8"));
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
