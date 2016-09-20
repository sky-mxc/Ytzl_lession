package com.skymxc.demo.lesson_21_activeandroid_db;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.skymxc.demo.lesson_21_activeandroid_db.entity.Student;

/**
 * Created by sky-mxc
 */
public class AddDialog extends Dialog {

    private EditText etName;
    private EditText etAge;
    private EditText etGrade;
    private EditText etClass;
    private Button btAdd;
    private Button btCancel;
    private static  AddDialog dialog;
    private OnAddOk addOk;

    public static AddDialog getInstance(Context context){
        if (dialog==null){
            dialog = new AddDialog(context);
        }
        return dialog;
    }


    private AddDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_add);
        etName = (EditText) findViewById(R.id.name);
        etAge = (EditText) findViewById(R.id.age);
        etClass = (EditText) findViewById(R.id.class_name);
        etGrade = (EditText) findViewById(R.id.grade);
        btCancel = (Button) findViewById(R.id.cancel);
        btAdd = (Button) findViewById(R.id.add);
       btAdd.setOnClickListener(onClickListener);
        btCancel.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
         switch (view.getId()){
             case R.id.add:
                 String name = etName.getText().toString();
                 String age = etAge.getText().toString();
                 String  grade = etGrade.getText().toString();
                 String className = etClass.getText().toString();
                 String reg= "^\\d{1,3}$";
                 if (age.matches(reg)) {
                     Student student = new Student(name, className, Integer.parseInt(age), grade);
                     addOk.addStudent(student);
                 }else{
                     Toast.makeText(getContext(),"年龄啊",Toast.LENGTH_SHORT).show();
                 }
                 dialog.dismiss();
                 break;
             case R.id.cancel:
                 dialog.cancel();
                 break;
         }
        }
    };

    public void setAddOk(OnAddOk addOk) {
        this.addOk = addOk;
    }

    public interface  OnAddOk{
        void addStudent(Student student);
    }
}
