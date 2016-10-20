package com.skymxc.demo.lsson_31_aidl.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.skymxc.demo.lsson_31_aidl.aidl.IStudentManager;
import com.skymxc.demo.lsson_31_aidl.entity.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky-mxc
 */
public class StudentService extends Service {
    private static final String TAG = "StudentService";

    private List<Student> students;

    @Override
    public void onCreate() {
        students = new ArrayList<>();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new StudentManager();
    }


    class StudentManager extends IStudentManager.Stub{

        @Override
        public int getCount() throws RemoteException {
            return students==null?0:students.size();
        }

        @Override
        public boolean removeAt(int index) throws RemoteException {
            Student student = null;
            try {
                student = students.remove(index);
                Log.i(TAG, "removeAt: "+index+"==result:"+(student!=null));
                return student!=null;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        public boolean remove(Student student) throws RemoteException {

            try {
                Log.i(TAG, "remove: "+student.getName());
                return students.remove(student);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public void add(Student student) throws RemoteException {
            if (student!=null){
                if (!students.isEmpty()){
                    int id = students.get(students.size()-1).getId();
                    student.setId(++id);
                }else{
                    student.setId(1);
                }

                students.add(student);
                Log.i(TAG, "add: id="+student.getId());
            }
        }

        @Override
        public Student get(int index) throws RemoteException {
            Log.i(TAG, "get: index="+index);
            if (index<getCount()){
                students.get(index);
            }
            return null;
        }
    }
}
