package com.skymxc.lesson_48_retrofit_eventbus.service;

import com.skymxc.lesson_48_retrofit_eventbus.entity.Result;
import com.skymxc.lesson_48_retrofit_eventbus.entity.Tab;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by sky-mxc
 */

public interface TabService {

    /**
     * 方法的返回值类型 必须是 call
     */

    /**
     * 加载 json字符串
     * @return
     */
    @GET(value = "index.php?app=mobile&type=mobile&controller=content&content=content&action=category")
    Call<String> loadJson();

    @GET("index.php")
    Call<String> loadJsonByQuery(@Query("app") String app,          //@Query 的值必须和参数的名字一致
                                 @Query("type") String type,
                                 @Query("controller") String controller,
                                 @Query("action") String action,
                                 @Query("content") String content);

    @GET("index.php")
    Call<String> loadJsonByQueryMap(@QueryMap Map<String,String> map);

    @GET("{name}/a.png")
    Call<String> loadJsonByPath(@Path("name")String name);

    /**
     * 以表单 post方式
     * @param name
     * @return
     */
    @FormUrlEncoded
    @POST("index.php")
    Call<String> loadJsonByField(@Field("name")String name);

    /**
     * 上传文件
     * @return
     */
    @Multipart
    @POST("upphoto")
    Call<String> upPhoto(@Part("u.uid")RequestBody uid,@Part("photo\";filename=\"2.jpg") RequestBody file);

 @Multipart
    @POST("upphoto")
    Call<String> upPhoto2(@Part("u.uid")RequestBody uid,@Part("photo") MultipartBody.Part file);



    /**
     * 加载 Tab 集合
     * @return
     */
    @GET(value = "index.php?app=mobile&type=mobile&controller=content&content=content&action=category")
    Call<Result<List<Tab>>> loadTab();
}
