package com.skymxc.lesson_48_retrofit_eventbus.factory;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.skymxc.lesson_48_retrofit_eventbus.entity.Result;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by sky-mxc
 */
public class MyConvertFactory extends Converter.Factory {
    private static final String TAG = "MyConvertFactory";

    /**
     * 决定相应结果转化器
     * 将相应结果 responseBody转换为任意对象
     * @param type 结果目标类型
     * @param annotations
     * @param retrofit
     * @return
     */
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == String.class){
            return stringConverter;

        }
        return super.responseBodyConverter(type,annotations,retrofit);
    }

    /**
     *  决定请求参数的转化器
     * @param type
     * @param parameterAnnotations
     * @param methodAnnotations
     * @param retrofit
     * @return
     */
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (type == String.class  ){
//            Arrays.binarySearch(methodAnnotations, Multipart.class);
            Log.e(TAG, "requestBodyConverter: "+Arrays.toString(methodAnnotations));
        }else if(type.toString().contains(Result.class.toString().substring(6))){
            return new ResultConverter<>(type);
        }

        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

        return super.stringConverter(type, annotations, retrofit);
    }

    /**
     * 创建请求参数转化器
     * 将字符串 转为 RequestBody
     * 泛型1 要进行抓换的类型
     * 泛型2 目标类型
     *
     */
    private  Converter<String ,RequestBody> stringToRequestBody = new Converter<String, RequestBody>() {
        @Override
        public RequestBody convert(String s) throws IOException {
            return RequestBody.create(MediaType.parse("multipart/form-data"),s);
        }
    };

    /**
     * 创建一个结果转化器
     * 将 responseBody 转换为String
     * 泛型1 固定为 responseBody
     * 泛型2 转为的目标类型
     */
    private Converter<ResponseBody,String> stringConverter = new Converter<ResponseBody, String>() {
        @Override
        public String convert(ResponseBody responseBody) throws IOException {
            return responseBody.string();
        }
    };


    class ResultConverter<T> implements Converter<ResponseBody,T>{

        private Type type;

        public ResultConverter(Type type){
            this.type = type;
        }

        @Override
        public T convert(ResponseBody responseBody) throws IOException {
            String json = responseBody.string();
            T t = JSON.parseObject(json,type);
            return t;
        }
    }
}
