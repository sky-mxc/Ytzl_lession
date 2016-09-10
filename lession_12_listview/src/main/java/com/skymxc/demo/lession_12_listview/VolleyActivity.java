package com.skymxc.demo.lession_12_listview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

/**
 * Created by sky-mxc
 */
public class VolleyActivity extends AppCompatActivity {
    private ImageView img;
    private NetworkImageView netImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        img = (ImageView) findViewById(R.id.image);
        netImage = (NetworkImageView) findViewById(R.id.new_image);
        RequestQueue queue  = Volley.newRequestQueue(this);
//        StringRequest request = new StringRequest(Request.Method.POST, "",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String s) {
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//
//            }
//        });

//        ImageRequest request = new ImageRequest("http://avatar.csdn.net/7/5/4/2_mxiaochao.jpg",
//                new Response.Listener<Bitmap>() {
//                    @Override
//                    public void onResponse(Bitmap bitmap) {
//                        if (bitmap==null){
//
//                        }else{
//
//                            img.setImageBitmap(bitmap);
//                        }
//                    }
//                }, 200, 300, Bitmap.Config.ARGB_8888,
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        img.setImageResource(R.mipmap.ic_launcher);
//                    }
//                });
//        queue.add(request);

        //什么都不写代表不缓存
        ImageLoader loader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return null;
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {

            }
        });

        loader.get("http://avatar.csdn.net/7/5/4/2_mxiaochao.jpg", new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                    img.setImageBitmap(imageContainer.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                img.setImageResource(R.mipmap.ic_launcher);
            }
        },200,200);



        //自动进行 图片的缩放 根据 在xml定义的大小  如果设为 包裹 就不会自动缩放了
        netImage.setImageUrl("http://img.bss.csdn.net/201509230557472929.jpg",loader);
        //加载错误时的图片
        netImage.setErrorImageResId(R.mipmap.ic_launcher);
        //加载中的图片
        netImage.setDefaultImageResId(R.mipmap.progress_bg_2);
    }
}
