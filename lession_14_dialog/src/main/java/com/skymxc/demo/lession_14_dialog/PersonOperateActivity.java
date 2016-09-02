package com.skymxc.demo.lession_14_dialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Calendar;

public class PersonOperateActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * operate 操作标识
     * 0 增加 default
     * 4 查看
     * 2 修改
     * 3 删除
     */

    private  int operate =1;
    private TextView tvTtile ;
    private ImageView img_action;
    private Person person;
    private TextView tvName;
    private TextView tvDate;
    private TextView tvTime;

    private static final int ACTION_UPDATE=2;
    private static final int ACTION_SEE=4;
    private static final int ACTION_DEL=3;
    private static final int ACTION_ADD=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_operate);
        tvTtile = (TextView) findViewById(R.id.title);
        img_action = (ImageView) findViewById(R.id.action_image);
        tvName = (TextView) findViewById(R.id.name);
        tvDate = (TextView) findViewById(R.id.birth_date);
        tvTime = (TextView) findViewById(R.id.birth_time);
        Intent intent = getIntent();
        if (intent!=null&&intent.getExtras()!=null){
            //获取操作标识
            operate = intent.getIntExtra("operate",ACTION_ADD);
            Log.e("Tag","==========operate====:"+operate);
            if (operate!=0){
                //查，删，改操作
               person = (Person) intent.getSerializableExtra("person");
                tvName.setText(person.getName());
                tvDate.setText(person.getBirthDate());
                tvTime.setText(person.getBirthTime());
            }
            changeOperate();
        }
    }

    /**
     * 更改操作
     *
     */
    private void changeOperate() {
        switch (operate){
            case ACTION_ADD:
                tvTtile.setText("添加");
                img_action.setImageResource(R.mipmap.ok);
                break;
            case ACTION_SEE:
                tvTtile.setText("查看");
                img_action.setImageResource(R.mipmap.delete_white);
                break;
            case ACTION_UPDATE:
                tvTtile.setText("修改");
                img_action.setImageResource(R.mipmap.ok);
                break;
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.name:
                if(operate ==ACTION_SEE){
                    operate=2;
                    changeOperate();
                }
                editName();
                break;
            case R.id.birth_date:
                if(operate ==ACTION_SEE){
                    operate=2;
                    changeOperate();
                }
                editDate();
                break;
            case R.id.birth_time:
                if(operate ==ACTION_SEE){
                    operate=2;
                    changeOperate();
                }
                editTime();
                break;
            case R.id.action_image:
                String name = tvName.getText().toString();
                String date = tvDate.getText().toString();
                String time = tvTime.getText().toString();
                if (operate!= ACTION_SEE){
                    if (name.trim().equals("未填写")||date.trim().equals("请选择")||time.trim().equals("请选择")){
                        Toast.makeText(this,"请确认信息完整",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (person == null) person = new Person();
                person.setName(name);
                person.setBirthDate(date);
                person.setBirthTime(time);
                Intent in = new Intent();
                in.putExtra("person", (Serializable) person);
                in.putExtra("operate",(operate==ACTION_SEE?ACTION_DEL:operate));//保存操作标识
                setResult(RESULT_OK,in);
                finish();
                break;
        }
    }


    /**
     * 编辑 出生时间
     */
    private void editTime() {
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.HOUR;
        int minute = calendar.MINUTE;
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String time = hour+":"+minute;
                tvTime.setText(time);
            }
        },hour,minute,true);
        dialog.show();
    }

    /**
     * 编辑出生日期
     */
    private void editDate() {

        //获取当前日历
        Calendar calendar = Calendar.getInstance();
        int year = calendar.YEAR;
        int month = calendar.MONTH;
        int day = calendar.DAY_OF_MONTH;
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = year+"/"+month+"/"+day;
                tvDate.setText(date);
            }
        },year,month,day);
        dialog.show();

    }

    /**
     * 编辑名字
     */
    private void editName() {
        EditText edit = new EditText(this);
        edit.setId(android.R.id.edit);
        edit.setHint("输入姓名");
        String name = tvName.getText().toString();
        if (!name.equals("未填写")){
            edit.setText(name);
        }

        new AlertDialog.Builder(this).setTitle("编辑姓名").setView(edit)
                .setPositiveButton("OK",dialogOnclickLit)
                .setNeutralButton("cancel",dialogOnclickLit)
                .show();
    }

    private DialogInterface.OnClickListener dialogOnclickLit = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i){
                case DialogInterface.BUTTON_POSITIVE:
                    Log.e("Tag","================OK");
                    if (dialogInterface instanceof AlertDialog){
                        AlertDialog dialog = (AlertDialog) dialogInterface;
                        EditText et = (EditText) dialog.findViewById(android.R.id.edit);
                        String name = et.getText().toString().trim();
                        if (name == null|| name.length()<=0) return;
                        tvName.setText(name);
                    }
                    break;
            }
        }
    };
}
