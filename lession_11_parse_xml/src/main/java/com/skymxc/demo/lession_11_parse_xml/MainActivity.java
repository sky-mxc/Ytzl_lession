package com.skymxc.demo.lession_11_parse_xml;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View v){
        switch (v.getId()){
            case R.id.pull_parse:
                pullParseXML();
                break;
            case R.id.sax_parse:
                saxParseXML();
                break;
        }
    }

    /**
     * Sax 解析
     */
    public void saxParseXML(){
        try {
            //创建ＳＡＸ解析器
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            PersonHandler personHandler = new PersonHandler();
            saxParser.parse(getAssets().open("student.xml"),personHandler );
            for (Person pp :personHandler.getPersons()){
                Log.e("Tag",pp.getName()+"==========="+pp.getId()+"==========="+pp.getAge());
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * pull解析模式 如果 xml中有乱码 或结束读取
     */
    private  void pullParseXML(){
        List<Person>  persons = new ArrayList<>();
        try {
            //0.得到解析器工厂实例
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            //1.创建解析器 利用解析器工厂
            XmlPullParser pullParser = factory.newPullParser();
            //  Xml.newPullParser(); Android中的pull方法

            //2.设置解析内容
            pullParser.setInput(getAssets().open("student.xml"),"utf-8");
            //3开始解析：
            //3.1获取当前标签
                //获取当前事件类型  事件：开始读取文本，开始读取标签，结束标签
            int type = pullParser.getEventType();//开始解析并返回事件类型
            Person p =null;
            while (type !=XmlPullParser.END_DOCUMENT){

                //查看事件类型
                switch (type){
                    case XmlPullParser.START_DOCUMENT:
                        //读取文档
                        Log.e("Tag","=======START_DOCUMENT==============开始读取文档======type:"+type);
                        break;
                    case XmlPullParser.START_TAG:
                        //开始读取标签
                        String tag = pullParser.getName();//获取到正在读取的的标签名称
                        Log.e("Tag","========START_TAG=====读取到的标签："+tag);
                        if (tag.equals("person")){  //读取person节点就创建一个对象 name,age  不创建
                            p = new Person();

                            String id = pullParser.getAttributeValue("","id");//参数1空间，2，属性名字
                            if (TextUtils.isEmpty(id))continue;
                            Log.e("Tag","=======START_TAG====读取到的' "+tag+" ' 的属性id:"+id);
                            p.setId(Integer.parseInt(id));
                        }else if (tag.equals("name")){
                            String name= pullParser.nextText();  //下一个文本节点
                            Log.e("Tag","================name:"+name);
                            p.setName(name);
                        }else if(tag.equals("age")){
                            String age = pullParser.nextText();//获取文本
                            Log.e("Tag","================age:"+age);
                            if (TextUtils.isEmpty(age))continue;
                            p.setAge(Integer.parseInt(age));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        //结束读取标签
                        tag = pullParser.getName();//获取结束节点的名称
                        if (tag.equals("person")){
                            persons.add(p);
                        }
                        Log.e("Tag","========END_TAG=============结束读取标签："+tag);
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        //结束读取文档
                        Log.e("Tag","========END_DOCUMENT===========结束读取文档");
                        break;
                }
                //下一个事件 每次读完就继续读下一个标签
                type = pullParser.next();
            }


        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Person pp :persons){
            Log.e("Tag",pp.getName()+"==========="+pp.getId()+"==========="+pp.getAge());
        }

    }
}
