package com.skymxc.demo.lession_11_parse_xml;

import android.text.TextUtils;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky-mxc
 * Person 读取使用的句柄
 */
public class PersonHandler extends DefaultHandler {
    private List<Person> persons;
    private Person p ;
    private String tag;

    /**
     * 开始读取文档
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        Log.e("Tag","============startDocument=================");
    }

    /**
     * 结束读取文档
     * @throws SAXException
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        Log.e("Tag","================endDocument===================");
    }

    /**
     * 开始读取元素
     * @param uri 命名空间地址
     * @param localName 当前节点的名字 无前缀
     * @param qName 当前节点的名字 带前缀
     * @param attributes 属性
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        Log.e("Tag","===========startElement==================");
        Log.e("Tag","=查看参数：==uri=="+uri);
        Log.e("Tag","=查看参数：==localName=="+localName);
        Log.e("Tag","=查看参数：==qName=="+qName);
        Log.e("Tag","=查看参数：==attributes=="+attributes);

        tag=qName;//获取到 元素名字 因为 在characters 无法获取元素名字

        if ("persons".equals(qName)){
            persons = new ArrayList<>();
        }else if("person".equals(qName)){
            p = new Person();
            //属性操作开始
            String id= attributes.getValue("id");//根据名称 获取

            if (!TextUtils.isEmpty(id)){
                p.setId(Integer.parseInt(id));
            }

        }
    }

    /**
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        Log.e("Tag","===========endElement==================");
        if ("person".equals(qName)){
            persons.add(p);
        }
    }

    /**
     * 文本读取  换行符也会读取
     * @param ch 文本
     * @param start 起始
     * @param length 长度
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        Log.e("Tag","===========characters==================");
        Log.e("Tag","===========characters=============参数=start:"+start);
        Log.e("Tag","===========characters=============参数=length:"+length);
        Log.e("Tag","===========characters=====当前元素:"+tag);

        //将内容转换为字符串
        String text = new String(ch,start,length).trim();
        Log.e("Tag","===========characters=====读到的字符:"+text);

        //字符串不能为空 不能读取空的文本节点 ，如换行 什么的
        if (TextUtils.isEmpty(text)) return;

        if ("name".equals(tag)){
            p.setName(text);

        }else if("age".equals(tag)){
            p.setAge(Integer.parseInt(text));
        }
    }


    public List<Person> getPersons() {
        return persons;
    }
}
