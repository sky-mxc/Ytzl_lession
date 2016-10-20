//接口描述语言
// IStudentManager.aidl
package com.skymxc.demo.lsson_31_aidl.aidl;

//导包
import com.skymxc.demo.lsson_31_aidl.entity.Student;

// Declare any non-default types here with import statements
//方法不能重载
interface IStudentManager {

    //获取全部数量
    int getCount();

    //按照下标移除
    boolean removeAt(int index);

    //student 必须导包 即使是在同一个包 java.lang可以不导包，其他包必须导包
    //导入的是实体类述文件的包
    //凡是 引用数据类型 除String 外 都 需要声明 输入(in)，输出(out) 或者 即输入又输出 (inout)
    //按照 对象进行移除
    boolean remove(in Student student);

    //添加 Student
    void add(inout Student student);

    //根据下标获取Student对象
    Student get(int index);
}
