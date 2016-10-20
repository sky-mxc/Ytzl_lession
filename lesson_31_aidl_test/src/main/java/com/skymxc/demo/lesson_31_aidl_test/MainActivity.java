package com.skymxc.demo.lesson_31_aidl_test;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.skymxc.demo.lsson_31_aidl.aidl.IStudentManager;
import com.skymxc.demo.lsson_31_aidl.entity.Student;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";

    private IStudentManager manager;

    private Student student;
    private int i;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            manager = IStudentManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.setClassName("com.skymxc.demo.lsson_31_aidl","com.skymxc.demo.lsson_31_aidl.service.StudentService");
        bindService(intent,conn,BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

    @Override
    public void onClick(View v) {
        if (manager==null) {
            Toast.makeText(this, "服务启动失败", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            switch (v.getId()){
                case R.id.count:
                    Log.i(TAG, "onClick: getcount:"+manager.getCount());
                    break;
                case R.id.remove:
                    if (student!=null){
                        Log.i(TAG, "onClick: remove:"+student.getName());
                        manager.remove(student);
                    }
                    break;
                case R.id.remove_at:
                    Log.i(TAG, "onClick: removeAt:"+manager.removeAt(1));
                    break;
                case R.id.get:
                     student = manager.get(1);
                    Log.i(TAG, "onClick: get(1)==name:"+student.getName());
                    break;
                case R.id.add:
                    i++;
                     student = new Student();
                    student.setName("李四"+i);
                    student.setPhone("110"+i);
                    manager.add(student);
                    Log.i(TAG, "onClick: add:"+student.getName());
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
