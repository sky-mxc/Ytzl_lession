package com.skymxc.demo.lession_14_dialog;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private ProgressDialog dialog ;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.progress);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.message:
                createMessageDialog();
                break;
            case R.id.items:
                createItemsDialog();
                break;
            case R.id.single:
                createSingledialog();
                break;
            case R.id.mutil:
                createMulitDialog();
                break;
            case R.id.view:
                createViewDialog();
                break;
            case R.id.progress:
                createProgressDialog();
                break;
            case R.id.date:
                createDateDialog();
                break;
            case R.id.time:
                createTimeDialog();
                break;
            case R.id.my_dialog:
                createMyDialog();
                break;
        }
    }

    /**
     * 创建自定义dialog
     */
    private void createMyDialog() {
        MyDialog dialog = new MyDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("设置时间");
        dialog.show();
    }

    /**
     * 创建时间对话框
     */
    private void createTimeDialog() {

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        /**
         * 最后一个参数 是否 是24时制
         */

        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            /**
             * 时间设置监听
             * @param timePicker
             * @param i
             * @param i1
             */
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                Log.e("Tag","======hour:"+i+"====minute:"+i1);
            }
        },hour,minute,true);
        dialog.show();
    }

    /**
     * 创建 日期 对话框
     */
    private void createDateDialog() {
        //获取当前时间
        Calendar calendar =Calendar.getInstance();  //获取日历
        int year =calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this,
                new  DatePickerDialog.OnDateSetListener(){
                    /**
                     *
                     * @param datePicker dialog 里面的 控件
                     * @param i 年
                     * @param i1 月
                     * @param i2 这个月的第几天
                     */
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Log.e("Tag","======year:"+i+"====Month:"+i1+"====day:"+i2);

                    }
                },year,month,day);

        dialog.show();
    }

    /**
     *  创建 进度条对话框
     */
    private void createProgressDialog() {

         dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
     //   dialog.setIndeterminate(true);//指示器模式 进度值无效
        dialog.setMessage("Message: readying......");
        dialog.setCanceledOnTouchOutside(false);    //点击边缘不消失  点击返回键能消失
        dialog.show();
        //进度必须在show之后起作用
        //dialog.setProgress(50);
        //延时执行Runnable 100毫秒后
        button.postDelayed(new Runnable() {
            @Override
            public void run() {
                int p = dialog.getProgress();
                if (p <dialog.getMax()){
                    dialog.setProgress(p+1);
                    button.postDelayed(this,100);//多次延时

                }else{
                    Toast.makeText(MainActivity.this,"canle",Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }
        },100);
    }

    /**
     * 自己创建视图 显示dialog
     */
    private void createViewDialog() {
        //代码创建控件
        EditText et = new EditText(this);
        et.setId(R.id.edit);
        et.setHint("输入文件名");
        new AlertDialog.Builder(this)
                .setView(et)
                .setTitle("新建")
                .setNegativeButton("确定",onClickLit)
                .setPositiveButton("取消",onClickLit)
                .show();
    }

    /**
     * 创建多选对话框
     */
    private void createMulitDialog() {
        new AlertDialog.Builder(this)
                .setMultiChoiceItems(new String[]{"mulit0", "mulit1", "mulit2"}, new boolean[]{true, true, false}, new DialogInterface.OnMultiChoiceClickListener() {
                    /**
                     *
                     * @param dialogInterface
                     * @param i 第几项   下标
                     * @param b 是否选中
                     */
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        Log.e("Tag","=======OnMultiChoiceClickListener==========第 "+i+" 项，选中："+b);
                    }
                }).show();
    }

    /**
     * 单选 对话框
     */
    private void createSingledialog() {
        //参数0 是指定默认的选中项
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(new String[]{"single0","single1","single2"},0,onClickLit)
                .show();
    }

    /**
     * 创建列表样式dialog
     */
    private void createItemsDialog() {
        new AlertDialog.Builder(this)
                .setItems(new String[]{"item1","item2","item3","item4"},onClickLit)
                .show();
    }

    /**
     * 对话框 按钮点击事件
     */
    private DialogInterface.OnClickListener onClickLit= new DialogInterface.OnClickListener() {
        /**
         *
         * @param dialogInterface 显示的对话框
         * @param i 选中的哪一个 按钮
         */
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Toast.makeText(MainActivity.this,i+"",Toast.LENGTH_SHORT).show();
            if (dialogInterface instanceof  AlertDialog){
               EditText edit= (EditText) ((AlertDialog)dialogInterface).findViewById(R.id.edit);
                if (edit != null){
                    Toast.makeText(MainActivity.this,edit.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            }
            Log.e("Tag","====="+i+"======");
            switch (i){
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    break;
                case DialogInterface.BUTTON_POSITIVE:
                    break;
            }

        }
    };

    /**
     * 创建消息对话框
     * 最普通的对话框
     */
    private void createMessageDialog() {
        //参数1 上下文必须是activity的 ，/*参数2 主题*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.top);
        builder.setTitle("Title  这是标题");
        builder.setMessage("Message 选择性别 Choose gender");
        builder.setPositiveButton(R.string.boy,onClickLit )
            .setNegativeButton(R.string.other,onClickLit)
            .setNeutralButton(R.string.girl,onClickLit);

//        AlertDialog dialog = builder.create();       //创建Dialog
//        dialog.show();                              //显示
        AlertDialog dialog = builder.show();           //创建并且显示
        //dialog.dismiss()          //取消显示
        //    dialog.cancel();
        Log.e("Tag","=============dialog.isShowing()============"+dialog.isShowing()+"");      //是否已经显示


    }
}
